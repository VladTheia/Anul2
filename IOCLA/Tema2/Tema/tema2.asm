extern puts
extern printf
extern strlen

%define BAD_ARG_EXIT_CODE -1

section .data
filename: db "./input0.dat", 0
inputlen: dd 2263

fmtstr:            db "Key: %d",0xa, 0
usage:             db "Usage: %s <task-no> (task-no can be 1,2,3,4,5,6)", 10, 0
error_no_file:     db "Error: No input file %s", 10, 0
error_cannot_read: db "Error: Cannot read input file %s", 10, 0
msg:             db "Intra aici", 0

section .text
global main

;TASK1
xor_strings:
        push ebp
        mov ebp, esp
        
        mov ecx, [ebp + 8] ;encrypted string 
        mov edx, [ebp + 12] ;key string
        xor eax, eax
        
xor_again:
        cmp byte [ecx + eax], 0x00 ;it means we reached the end ot the string
        je decrypted1
        mov bl, byte [edx + eax]
        xor [ecx + eax], bl ; xor the string and the key
        inc eax
        jmp xor_again        
        
decrypted1:     
        leave
	ret

;TASK2
rolling_xor:
        push ebp
        mov ebp, esp

        mov ecx, [ebp + 8]

        	push ecx ; save string
        push ecx
        call strlen ; strlen in eax
        add esp, 4
        pop ecx ; restore string
        dec eax
        
        xor ebx, ebx ;used for storing chars

rolling_xor_again:
        cmp eax, 0 ; we read the string from end to start, so we stop when we get to the first char
        je decrypted2
        mov bl, byte [ecx + eax - 1]
        xor [ecx + eax], bl ; xor the last element with the one before it
        dec eax
        jmp rolling_xor_again

decrypted2:
        leave
        ret        

;TASK3
convert_string:
        push ebp
        mov ebp, esp
        
        mov ecx, [ebp + 8] ; save string in ecx
        xor ebx, ebx ; 1st char
        xor edx, edx ; 2nd char
        xor edi, edi ; converted string counter
        xor esi, esi ; unconverted string counter
        
again: ; loop for converting two bytes into one
        cmp byte [ecx + esi], 0x00
        je converted
        mov bl, byte [ecx + esi]
        mov dl, byte [ecx + esi + 1]
        cmp bl, 97 ; from ASCII code to hex value
        jl is_number
        sub bl, 87
        jmp second_char
is_number:
        sub bl, 48
        jmp second_char

second_char:
        cmp dl, 97
        jl is_number2
        sub dl, 87
        jmp create
is_number2:
        sub dl, 48
        jmp create

create: ; the strings are converted because we need them in binary to be able to xor
        shl bl, 4
        add bl, dl
        mov byte [ecx + edi], bl
        inc edi
        add esi, 2
        jmp again
                
converted:
        mov byte [ecx + edi], 0x00
        leave
        ret

xor_hex_strings:
        push ebp
        mov ebp, esp
        
        mov ecx, [ebp + 8] ;encrypted string 
        mov edx, [ebp + 12] ;key string
        xor eax, eax
        
        push edx ;save string
        push ecx
        call convert_string
        add esp, 4
        pop edx ; restore string
        
        push ecx ;save string
        mov ecx, edx
        push ecx
        call convert_string
        add esp, 4
        mov edx, ecx ;put key string in edx
        pop ecx ;restore string

        xor eax, eax
        xor ebx, ebx
        
hex_xor_again: ;now that the strings are converted, we xor them 
        cmp byte [ecx + eax], 0x00
        je decrypted3
        mov bl, byte [edx + eax]
        xor [ecx + eax], bl
        inc eax
        jmp hex_xor_again

decrypted3:
        leave
	ret
;TASK4
index: 
        push ebp
        mov ebp, esp
        
        mov ebx, [ebp + 8] ; char to be converted to index value
        
        cmp bl, '='
        je padding
        cmp bl, 'A'
        jge alpha
        sub bl, 24
        jmp indexed
alpha:
        sub bl, 'A'
        jmp indexed
padding:
        mov bl, 0
indexed:
        leave
        ret

base32decode:
        push ebp
        mov ebp, esp
        
        mov ecx, [esp + 8] ; encrypted string
	xor esi, esi ; cases counter
        xor edi, edi ; string counter
        xor ebx, ebx ; used to contain the chars of the encoded string
        xor eax, eax ; use for making the decoded chars
        
decode:
; case1
        mov bl, byte [ecx + edi]
        push ebx
        call index
        add esp, 4
  
        mov al, bl
        shl al, 3
        
        mov bl, byte [ecx + edi + 1]
        push ebx
        call index
        add esp, 4
        
        shr bl, 2
        add al, bl
        
        mov byte [ecx + esi], al
        xor eax, eax
        xor ebx, ebx
        inc edi
        inc esi

; case2
        mov bl, byte [ecx + edi]
        push ebx
        call index
        add esp, 4
        
        mov al, bl
        shl al, 6
        
        mov bl, byte [ecx + edi + 1]
        push ebx
        call index
        add esp, 4
        
        shl bl, 1
        add al, bl
        
        mov bl, byte [ecx + edi + 2]
        push ebx
        call index
        add esp, 4
        
        shr bl, 4
        add al, bl
        
        mov byte [ecx + esi], al
        xor eax, eax
        xor ebx, ebx
        add edi, 2
        inc esi
        
; case3
        mov bl, byte [ecx + edi]
        push ebx
        call index
        add esp, 4
        
        mov al, bl
        shl al, 4
        
        mov bl, byte [ecx + edi + 1]
        push ebx
        call index
        add esp, 4
        
        shr bl, 1
        add al, bl
        
        mov byte [ecx + esi], al
        xor eax, eax
        xor ebx, ebx
        inc edi
        inc esi
                 
; case4
        mov bl, byte [ecx + edi]
        push ebx
        call index
        add esp, 4
        
        mov al, bl
        shl al, 7
        
        mov bl, byte [ecx + edi + 1]
        push ebx
        call index
        add esp, 4
        
        shl bl, 2
        add al, bl
        
        mov bl, byte [ecx + edi + 2]
        push ebx
        call index
        add esp, 4        
        
        shr bl, 3
        add al, bl
        
        mov byte [ecx + esi], al
        xor eax, eax
        xor ebx, ebx
        add edi, 2
        inc esi
        
; case5
        mov bl, byte [ecx + edi]
        push ebx
        call index
        add esp, 4
  
        mov al, bl
        shl al, 5
        
        mov bl, byte [ecx + edi + 1]
        push ebx
        call index
        add esp, 4
        
        add al, bl
        
        mov byte [ecx + esi], al
        xor eax, eax
        xor ebx, ebx
        add edi, 2
        inc esi
        
        mov bl, byte [ecx + edi]
        cmp bl, 0x00
        jne decode
        mov byte [ecx + esi], 0x00        
        
decrypted4:
        leave
	ret


;TASK5
find_key:
        xor eax, eax
        xor ebx, ebx
        xor edx, edx
        xor esi, esi

next_try: ; if 5 consecutive chars from the string have the same key, than that's the correct key
        mov bl, byte [ecx + esi]
        xor bl, 'f'
        inc esi
        mov dl, byte [ecx + esi]
        xor dl, 'o'
        cmp bl, dl
        jne next_try
        inc esi
        mov dl, byte [ecx + esi]
        xor dl, 'r'
        cmp bl, dl
        jne next_try
        inc esi
        mov dl, byte [ecx + esi]
        xor dl, 'c'
        cmp bl, dl
        jne next_try
        inc esi
        mov dl, byte [ecx + esi]
        xor dl, 'e'
        cmp bl, dl
        jne next_try
        jmp found

bruteforce_singlebyte_xor:
	push ebp
        mov ebp, esp
        
        mov ecx, [ebp + 8]
        
        push ecx ; vezi daca merge sters
        jmp find_key
found:
        pop ecx ;vezi daca merge sters
        
        xor esi, esi ;counter
bf_xor:
        cmp byte [ecx + esi], 0x00
        je decrypted5
        xor [ecx + esi], bl ;xor every byte with the key
        inc esi
        jmp bf_xor
        
decrypted5:
        mov eax, ebx ; save the key in eax for the return
        leave
        ret
        
;TASK6
out_of_bound:
        xor eax, eax
        mov al, 97
        sub al, byte [ecx + esi] ; this is how much we have left to subtract after reaching 'a'
        mov byte [ecx + esi], 123 ; we start again from z
        sub byte [ecx + esi], al
        jmp continue

letter:
        mov bl, byte [edx + edi]
        sub bl, 97
        sub byte [ecx + esi], bl
        cmp byte [ecx + esi], 97 ; by subtracting, we can get to a value lower than 'a'
        jl out_of_bound
        jmp continue
        
sign:
        inc esi
        jmp again_vigenere
        
decode_vigenere:
        push ebp
        mov ebp, esp
        
        mov ecx, [ebp + 8]
        mov edx, [ebp + 12]
        xor eax, eax
        xor edi, edi ; key counter
        xor esi, esi ; string counter
        xor ebx, ebx
        
again_vigenere:       
        mov bl, byte [ecx + esi]
        cmp bl, 0x00
        je decrypted6
        cmp bl, 97 
        jge letter ; it it's a letter, modify it
        jmp sign ; it it's a sign, skip it
        
continue:
        inc esi
        inc edi
        cmp byte [edx + edi], 0x00 ; see if we reached the end of the key
        je restart ; make edi point again towards the start of the key
        jmp again_vigenere
        
restart:
        xor edi, edi
        jmp again_vigenere 
        
decrypted6:
        leave
        ret

main:
    mov ebp, esp; for correct debugging
	push ebp
	mov ebp, esp
	sub esp, 2300

	; test argc
	mov eax, [ebp + 8]
	cmp eax, 2
	jne exit_bad_arg

	; get task no
	mov ebx, [ebp + 12]
	mov eax, [ebx + 4]
	xor ebx, ebx
	mov bl, [eax]
	sub ebx, '0'
	push ebx

	; verify if task no is in range
	cmp ebx, 1
	jb exit_bad_arg
	cmp ebx, 6
	ja exit_bad_arg

	; create the filename
	lea ecx, [filename + 7]
	add bl, '0'
	mov byte [ecx], bl

	; fd = open("./input{i}.dat", O_RDONLY):
	mov eax, 5
	mov ebx, filename
	xor ecx, ecx
	xor edx, edx
	int 0x80
	cmp eax, 0
	jl exit_no_input

	; read(fd, ebp - 2300, inputlen):
	mov ebx, eax
	mov eax, 3
	lea ecx, [ebp-2300]
	mov edx, [inputlen]
	int 0x80
	cmp eax, 0
	jl exit_cannot_read

	; close(fd):
	mov eax, 6
	int 0x80

	; all input{i}.dat contents are now in ecx (address on stack)
	pop eax
	cmp eax, 1
	je task1
	cmp eax, 2
	je task2
	cmp eax, 3
	je task3
	cmp eax, 4
	je task4
	cmp eax, 5
	je task5
	cmp eax, 6
	je task6
	jmp task_done

task1:
	; TASK 1: Simple XOR between two byte streams
	; TODO TASK 1: find the address for the string and the key
        push ecx
        push ecx
        call strlen  ; save the length of the encoded string in eax
        add esp, 4
        pop ecx

        mov edx, ecx 
        add edx, eax
        inc edx ; save the key in edx
    
        push edx
        push ecx
        call xor_strings
        add esp, 8
	; TODO TASK 1: call the xor_strings function

	push ecx
	call puts                   ;print resulting string
	add esp, 4

	jmp task_done

task2:
	; TASK 2: Rolling XOR
        
	; TODO TASK 2: call the rolling_xor function
        push ecx
        call rolling_xor
        add esp, 4

	push ecx
	call puts
	add esp, 4

	jmp task_done

task3:
	; TASK 3: XORing strings represented as hex strings

	; TODO TASK 1: find the addresses of both strings
	; TODO TASK 1: call the xor_hex_strings function
        ; TODO TASK 1: find the address for the string and the key
        push ecx
        push ecx
        call strlen
        add esp, 4
        pop ecx

        mov edx, ecx 
        add edx, eax
        inc edx ; save the key in edx
    
        push edx
        push ecx
        call xor_hex_strings
        add esp, 8
        
	push ecx                     ;print resulting string
	call puts
	add esp, 4

	jmp task_done

task4:
	; TASK 4: decoding a base32-encoded string
        
	; TODO TASK 4: call the base32decode function
        push ecx
        call base32decode
        add esp, 4
	
	push ecx
	call puts                    ;print resulting string
	add esp, 4
	
	jmp task_done
        
task5:
	; TASK 5: Find the single-byte key used in a XOR encoding

        ; TODO TASK 5: call the bruteforce_singlebyte_xor function
       
        push ecx
        call bruteforce_singlebyte_xor
        add esp, 4
        
        push eax ; save the key
	push ecx                    ;print resulting string
	call puts
	pop ecx
        pop eax ; restore the key
        
	push eax                    ;eax = key value
	push fmtstr
	call printf                 ;print key value
	add esp, 8

	jmp task_done

task6:
	; TASK 6: decode Vignere cipher

	; TODO TASK 6: find the addresses for the input string and key
	; TODO TASK 6: call the decode_vigenere function
        push ecx
        push ecx
        call strlen
        add esp, 4
        pop ecx

        mov edx, ecx 
        add edx, eax 
        inc edx
        
	push edx                   ;edx = address of key
	push ecx                   ;ecx = address of input string 
	call decode_vigenere
	add esp, 8

	push ecx
	call puts
	add esp, 4

task_done:
	xor eax, eax
	jmp exit

exit_bad_arg:
	mov ebx, [ebp + 12]
	mov ecx , [ebx]
	push ecx
	push usage
	call printf
	add esp, 8
	jmp exit

exit_no_input:
	push filename
	push error_no_file
	call printf
	add esp, 8
	jmp exit

exit_cannot_read:
	push filename
	push error_cannot_read
	call printf
	add esp, 8
	jmp exit

exit:
	mov esp, ebp
	pop ebp
	ret

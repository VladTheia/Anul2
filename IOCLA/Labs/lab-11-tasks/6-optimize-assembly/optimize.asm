global runAssemblyCode

extern printf

section .data
    str: db "%d",10,13

section .text
runAssemblyCode:
    push ebp
    mov ebp, esp

    ; N
    ;sub esp, 4
    mov ecx, [ebp+12] ; ecx = N
    ;mov dword[esp], eax ; N = ebp - 4
    mov edx, [ebp+8] ; edx = a
    
    xor eax, eax
again:
     add eax, [edx + 4*ecx - 4]
     dec ecx
     cmp ecx, 0
     jne again
    
    ; i
    ;sub esp, 4 ; i = ebp - 8
    ; sum
    ;sub esp, 4 ; sum = ebp - 12
    ;mov dword[esp], 0
    ; int* a
    ;sub esp, 4 ; a = ebp - 16
    ;mov eax, [ebp+8] ; 
    ;mov [esp], eax

    ;mov eax, [ebp-4] ; eax = N
    ;mov dword[ebp-8], 0 ; i = 0
    ;jmp L1

L2:
    ;mov [ebp-4], eax ; sum = N
    ;mov eax, [ebp-8] ; eax = i
    ;mov edx, [ebp-16]; edx = a
    ;lea eax, [edx + 4*eax] ; [eax] = a[i]
    ;mov edx, [eax] ; edx = [eax]
    ;add [ebp-12], edx ; sum += edx
    ;add dword[ebp-8], 1 ; i++
    ;mov eax, [ebp-4] ; eax = N

L1:
    ; if i < N
    ;cmp [ebp-8], eax
    ;jl L2

    ;mov eax, [ebp-12]
    mov esp, ebp
    pop ebp
    ret

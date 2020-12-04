%include "io.inc"

%define MAX_INPUT_SIZE 4096

section .bss
	 expr: resb MAX_INPUT_SIZE

section .text
global CMAIN
CMAIN:
	 mov ebp, esp
        mov ecx, MAX_INPUT_SIZE
	 GET_STRING expr, MAX_INPUT_SIZE
        xor eax, eax ;numar prelucrat
        xor ebx, ebx ;parcurgere caracter
        xor ecx, ecx ;contor parcurgere sir
        xor edx, edx ;decizie nr negativ
        
parse_string: ; parcurgerea sirului
        xor ebx, ebx
        xor edx, edx
        mov bl, byte [expr + ecx]
        cmp ebx, '0'
        jge build_nr
        cmp ebx, '-'
        je minus_case
        cmp ebx, '+'
        je execute_add
        cmp ebx, '*'
        je execute_mul
        cmp ebx, '/'
        je execute_div

minus_case:
        inc ecx
        xor ebx, ebx
        mov bl, byte [expr + ecx]
        cmp ebx, '0'
        jl execute_sub ; minusul este defapt operand
        xor edx, edx
        mov edx, 1
        jmp build_nr ; minusul defineste un numar negativ, deci il construim

build_nr:
        xor eax, eax
        sub ebx, 48
        mov eax, ebx
check: ; verificam daca trebuie sa crestem ordinul de marime
        inc ecx
        xor ebx, ebx
        mov bl, byte [expr + ecx]
        cmp ebx, '0'
        jge inc_order
        inc ecx
        jmp push_nr ; am gasit spatiu, deci dam push pe stiva

inc_order:
        push edx ; folosim stiva pentru a salva semnul numarului (negativ/pozitiv)
        sub ebx, 48
        xor edx, edx
        mov edx, 10
        imul eax, edx
        xor edx, edx
        pop edx ; recuperam semnul
        add eax, ebx
        jmp check  

push_neg_nr:
        neg eax
        push eax
        xor edx, edx
        jmp parse_string
push_nr:
        cmp edx, 1
        je push_neg_nr
        push eax
        jmp parse_string
        
execute_add:
        xor eax, eax
        xor edx, edx
        pop edx
        pop eax
        add eax, edx
        push eax
        inc ecx
        xor ebx, ebx
        mov bl, byte [expr + ecx]
        cmp ebx, 0
        je exit_string
        inc ecx ; ne aflam la un spatiu, deci crestem ecx
        jmp parse_string

execute_sub:
        xor eax, eax
        xor edx, edx
        pop edx
        pop eax
        sub eax, edx
        push eax
        inc ecx
        xor ebx, ebx
        mov bl, byte [expr + ecx]
        cmp ebx, 0
        je exit_string
        ;inc ecx
        jmp parse_string
        
execute_mul:
        xor eax, eax
        xor ebx, ebx
        xor edx, edx
        pop ebx
        pop eax
        imul ebx
        push eax
        inc ecx
        xor ebx, ebx
        mov bl, byte [expr + ecx]
        cmp ebx, 0
        je exit_string
        inc ecx
        jmp parse_string

execute_div:
        xor eax, eax
        xor ebx, ebx
        xor edx, edx
        pop ebx
        pop eax
        cmp eax, 0
        jge not_signed; daca numarul e pozitiv nu trebuie sa schimbam edx
        mov edx, -1
not_signed:
        idiv ebx
        push eax
        inc ecx
        xor ebx, ebx
        mov bl, byte [expr + ecx]
        cmp ebx, 0
        je exit_string
        inc ecx
        jmp parse_string
        
exit_string:
      	 xor eax, eax
		pop eax
        PRINT_DEC 4, eax
        ;mov esp, ebp
		ret
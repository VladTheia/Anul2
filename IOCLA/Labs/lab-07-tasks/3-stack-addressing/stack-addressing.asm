%include "io.inc"

%define NUM 5

section .text
global CMAIN
CMAIN:
    mov ebp, esp

    ; TODO 1: replace every push by an equivalent sequence of commands
    mov ecx, NUM
push_nums:
    ;push ecx
    sub esp, 4
    mov [esp], ecx
    loop push_nums

    ;push 0
    mov eax, 0
    sub esp, 4
    mov [esp], eax
    ;push "mere"
    sub esp, 4
    mov dword [esp], "mere"
    ;push "are "
    sub esp, 4
    mov dword [esp], "are "
    ;push "Ana "
    sub esp, 4
    mov dword [esp], "Ana "

    ; TODO 2: print the stack in "address: value" format in the range of [ESP:EBP]
    mov ecx, ebp
again:
    PRINT_STRING "0x"
    PRINT_HEX 4, ecx
    PRINT_STRING ": 0x"
    PRINT_HEX 4, [ecx]
    NEWLINE
    sub ecx, 4
    cmp ecx, esp
    jg again
    ; TODO 3: print the string
    PRINT_STRING [esp]
    NEWLINE
    ; restore the previous value of the EBP (Base Pointer)
    mov esp, ebp

    ; exit without errors
    xor eax, eax
    ret

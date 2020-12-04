%include "io.inc"

section .text
global CMAIN
CMAIN:
    push ebp
    mov ebp, esp

    mov eax, 211    ; to be broken down into powers of 2
    mov ebx, 1      ; stores the current power

lable:
    test eax, ebx
    jnz lable2
    shl ebx, 1
    jmp lable

lable2:
    PRINT_DEC 4, ebx
    NEWLINE
    shl ebx, 1
    jmp lable
    
    ; TODO - print the powers of 2 that generate number stored in EAX

    leave
    ret

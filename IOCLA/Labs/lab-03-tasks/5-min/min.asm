%include "io.inc"

section .text
global CMAIN
CMAIN:
    ;cele doua numere se gasesc in eax si ebx
    mov eax, 1
    mov ebx, -4 
    cmp eax,ebx
    jl lable
    ; TODO: aflati minimul
    xchg eax, ebx
lable:
    PRINT_DEC 4, eax ; afiseaza minimul
    NEWLINE

    ret
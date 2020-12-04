%include "io.inc"

section .text
global CMAIN
CMAIN:
    mov eax, 10      ; vrem sa aflam al N-lea numar; N = 7
    ; TODO: calculati al N-lea numar fibonacci (f(0) = 0, f(1) = 1)
    mov ebx, 0
    mov ecx, 1
lable1:
    ;mov edx, ebx
    add ebx, ecx
    ;mov ecx, edx
    sub eax, 1
    cmp eax, 0
    jne lable2
    ;cmp eax,0
    ;je lable
lable2:
    ;mov edx, ebx
    add ecx, ebx
    ;mov ecx, edx
    sub eax, 1
    cmp eax, 0
    jne lable1
    
lable:
    PRINT_DEC 4, ebx ;
    NEWLINE
    
    ret
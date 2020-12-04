%include "io.inc"

section .data
    myString: db "Hello, World!",0
    String2: db "Goodbye, World",0

section .text
global CMAIN
CMAIN:
    mov ebp, esp; for correct debugging
    mov ecx, 6                 ; N = valoarea registrului ecx
    mov eax, 2
    mov ebx, 1
    cmp eax, ebx
    jg print                   ; TODO1: eax > ebx?
    ret
print:
    PRINT_STRING myString
    NEWLINE
    sub ecx, 1
    cmp ecx, 0
    jne print
    PRINT_STRING String2
                               ; TODO2.2: afisati "Hello, World!" de N ori
                               ; TODO2.1: afisati "Goodbye, World!"

    ret

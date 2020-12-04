%include "io.inc"

section .text
global CMAIN
CMAIN:
    ;cele doua multimi se gasesc in eax si ebx
    mov eax, 139 ;{7,3,1,0}
    mov ebx, 169;{7,5,3,0}
    PRINT_DEC 4, eax ; afiseaza prima multime
    NEWLINE
    PRINT_DEC 4, ebx ; afiseaza cea de-a doua multime
    NEWLINE

    ; TODO1: reuniunea a doua multimi
    or eax, ebx
    PRINT_DEC 4, eax
    NEWLINE 
    mov eax,139
    ; TODO2: adaugarea unui element in multime
    mov edx, 1
    shl edx, 9
    or eax, edx
    PRINT_DEC 4, eax
    NEWLINE
    mov eax, 139
    ; TODO3: intersectia a doua multimi
    and eax, ebx
    PRINT_DEC 4, eax
    NEWLINE
    mov eax, 139
    ; TODO4: complementul unei multimi
    not eax
    PRINT_HEX 4, eax
    NEWLINE
    mov eax, 139
    ; TODO5: eliminarea unui element
    mov edx, 1
    shl edx, 3
    xor eax, edx
    PRINT_DEC 4, eax
    NEWLINE
    mov eax, 139
    mov ebx, 169
    ; TODO6: diferenta de multimi EAX-EBX
    mov edx, eax
    and eax, ebx
    xor edx, eax
    PRINT_DEC 4, edx
    NEWLINE
    
    ret

%include "io.inc"

section .text
global CMAIN
CMAIN:
    mov ebp, esp
    PRINT_STRING "gdc("
    ; input values (eax, edx): the 2 numbers to compute the gcd for
    mov eax, 49
    mov edx, 28
    PRINT_UDEC 4, EAX
    PRINT_CHAR ","
    PRINT_UDEC 4, EDX
    PRINT_STRING ")="
    push eax
    push edx

gcd:
    neg     eax
    je      L3

L1:
    neg     eax
    push eax
    push edx
    pop eax
    pop edx

L2:
    sub     eax,edx
    jg      L2
    jne     L1

L3:
    add     eax,edx
    jne     print
    inc     eax

print:

    ; TODO 1: solve the 'The program crashed!' error

    ; TODO 2: print the result in the form of: "gdc(eax, edx)=7"

    PRINT_UDEC 4, eax  ; output value in eax
    mov esp, ebp
    xor eax, eax
    ret

%include "io.inc"

%define ARRAY_SIZE    5

section .data
    byte_array db  1, -2, 3, -3, 5
    print_format db "Array sum is ", 0


section .text
global CMAIN
CMAIN:
    ;write your code here
    xor eax, eax
    ret
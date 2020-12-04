%include "io.inc"

%define ARRAY_SIZE 13
%define DECIMAL_PLACES 5

section .data
    num_array dw 76, 12, 65, 19, 781, 671, 431, 761, 782, 12, 91, 25, 9
    array_sum_prefix db "Sum of numbers: ",0
    array_mean_prefix db "Numbers mean: ",0
    decimal_point db ".",0

section .text
global CMAIN
CMAIN:
    xor eax, eax
    mov ecx, ARRAY_SIZE

    ; TODO1 - compute the sum of the vector numbers - store it in ax
again:
    add word ax, [num_array + 2 * ecx - 2]
    loop again
    PRINT_STRING array_sum_prefix
    PRINT_UDEC 2, ax
    NEWLINE
    xor edx, edx
    mov ecx, ARRAY_SIZE
    ; TODO2 - compute the quotient of the mean
    div cx
    PRINT_STRING array_mean_prefix
    PRINT_UDEC 2, ax
    PRINT_STRING decimal_point

    mov ecx, DECIMAL_PLACES
    mov eax, edx
    mov ebx, 10
compute_decimal_place:
    mul ebx
    ; TODO3 - compute the current decimal place - store it in ax

    dec ecx
    cmp ecx, 0
    jg compute_decimal_place
    mov ebx, ARRAY_SIZE
    div ebx
    PRINT_DEC 4, eax
    NEWLINE

    xor eax, eax
    ret

global computeLength
global computeLength2

section .text
computeLength:
    push ebp
    mov ebp, esp

    xor eax, eax ; length
    ;TODO: Implement byte count using a software loop
    mov ecx, [ebp + 8] ; string

loop1:
    inc eax
    inc ecx
    cmp byte [ecx], 0x00
    jne loop1
    
    mov esp, ebp
    pop ebp
    ret

computeLength2:
    push ebp
    mov ebp, esp

    push ebx
    push edi
    ;TODO: Implement byte count using a hardware loop
    mov ebx, [ebp + 8] ; string
    mov ecx, 0xffffffff
    
    cld
    mov al, 0
    mov edi, ebx
    repne scasb

    sub edi, ebx  ; aflăm diferența dintre apariția char-ului 'a' și începutul șirului
    dec edi
    
    mov eax, edi
    
    pop edi
    pop ebx
    
    mov esp, ebp
    pop ebp
    ret

section .text

global get_max

get_max:
	push rbp
	mov rbp, rsp

	; RDI is array pointer
	; RSI is array length

	mov rbx, rdi
	mov rcx, rsi
	xor eax, eax

compare:
	cmp eax, [rbx+rcx*8-8]
	jge check_end
	mov eax, [rbx+rcx*8-8]
check_end:
	loopnz compare

	leave
	ret

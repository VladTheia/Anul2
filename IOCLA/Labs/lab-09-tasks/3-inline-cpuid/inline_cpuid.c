#include <stdio.h>

int main(void)
{
	char cpuid_str[13];

	__asm__ (
	"xor eax, eax\n\t"	
	"cpuid\n\t"
	"mov eax, %0\n\t"
	"mov ebx, [eax]\n\t"
	"mov edx, [eax + 4]\n\t"
	"mov ecx, [eax + 8]\n\t"
	"mov eax, %0\n\t"
	/* TODO: Make cpuid call and copy string in cpuid_str.
	 * eax needs to be 0
	 * After cpuid call string is placed in (ebx, edx, ecx).
	 */
	:
	: "r" (cpuid_str)
	: "eax", "ebx", "ecx", "edx" );

	cpuid_str[12] = '\0';

	printf("CPUID string: %s\n", cpuid_str);

	return 0;
}

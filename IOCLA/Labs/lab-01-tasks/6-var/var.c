#include <stdio.h>

int main(void)
{
    short a = 20000;
    short b = 14000;

    short c = a + b;
    unsigned short d = 3 * a + b;
    short e = a << 1;

    // TODO - print variables c, d, e
    printf("c = %hd\nd = %hu\ne = %hd\n", c, d, e);
    return 0;
}

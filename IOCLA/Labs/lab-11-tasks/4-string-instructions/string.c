#include<stdio.h>
#include<time.h>
#include<stdlib.h>

#define N 1000000

extern int computeLength(char* str);
extern int computeLength2(char* str);

void main()
{
    char a[N];
    FILE *f = fopen("file.txt", "rb");
    fread(a, N, 1, f);
    fclose(f);
    a[N] = 0;
    clock_t t = clock();
    int len = computeLength(a);
    t = clock() - t;
    printf("len = %d\n", len);
    printf("Time = %f\n", ((float)t)/CLOCKS_PER_SEC);

    clock_t t2 = clock();
    int len2 = computeLength2(a);
    t2 = clock() - t2;
    printf("len = %d\n", len2);
    printf("Time = %f\n", ((float)t2)/CLOCKS_PER_SEC);
}

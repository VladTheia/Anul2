#include<stdio.h>
#include<time.h>

extern int runAssemblyCode(int* a, int N);

#define N 1000000

void main()
{
    int a[N];
    int i;
    for (i = 0; i < N; i++)
        a[i] = 1;
    clock_t t = clock();
    int result = runAssemblyCode(a, N);
    t = clock() - t;
    printf("Time: %f\n", ((float)t)/CLOCKS_PER_SEC);
    printf("result: %d\n", result);
}

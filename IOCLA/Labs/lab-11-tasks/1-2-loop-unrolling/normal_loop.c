#include<stdio.h>
#include<time.h>

//#define N 1000000
#define N 10

void main()
{
    int list[N], sum = 0;
    int i;
    clock_t t = clock();
    for (i = 0; i < N; i++)
        sum += list[i];
    t = clock() - t;
    printf("Time: %f\n", ((float)t)/CLOCKS_PER_SEC);
    printf("sum = %d\n", sum);
}

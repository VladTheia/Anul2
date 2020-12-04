#include<stdio.h>
#include<time.h>

#define N 1000000

void main()
{

    int list[N], sum = 0.0, sum1 = 0.0, sum2 = 0.0, sum3 = 0.0, sum4 = 0.0;
    int i;
    clock_t t = clock();
    for (i = 0; i < N; i += 4)
    {
        sum1 += list[i];
        sum2 += list[i+1];
        sum3 += list[i+2];
        sum4 += list[i+3];
    }
    sum = sum1 + sum2 + sum3 + sum4;
    printf("Time: %f\n", ((float)t)/CLOCKS_PER_SEC);
}

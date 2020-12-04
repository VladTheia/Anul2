#include<stdio.h>
#include<time.h>

#define N 1000000
int a;

void doSomething(int i)
{
    a = i;
}

void doSomethingElse(int i)
{
    a = i + 2;
}

void doYetSomethingElse(int i)
{
    a = i*3 + 2;
}

void main()
{
    int i;
    int var;
    printf("Insert an integer:\n");
    scanf("%d", &var);

    clock_t t1 = clock();
    for (i = 0; i < N; i++)
    {
        if (var % 2 == 0)
            doSomething(i);
        else if (var % 3 == 0)
            doSomethingElse(i);
        else
            doYetSomethingElse(i);
    }

    t1 = clock() - t1;
    printf("[Non-optimized] Computed: %d in: %f seconds\n", a, ((float)t1)/CLOCKS_PER_SEC);

    a = 0;
    clock_t t2 = clock();

    // TODO: optimize the above code here
	if (var % 2 == 0) {
	    for (i = 0; i < N; i += 4) {
     	        doSomething(i);
		doSomething(i + 2);
		doSomething(i + 3);
		doSomething(i + 4); 
	}
	} else if (var % 3 == 0) {
	    for (i = 0; i < N; i += 4) {
     	        doSomethingElse(i);
		doSomethingElse(i + 2);
		doSomethingElse(i + 3);
		doSomethingElse(i + 4); 
	}		
	} else {
		for (i = 0; i < N; i += 4) {
     	        doYetSomethingElse(i);
		doYetSomethingElse(i + 2);
		doYetSomethingElse(i + 3);
		doYetSomethingElse(i + 4); 
	}
	}
		   

    t2 = clock() - t2;
    printf("[Optimized] Computed: %d in: %f seconds\n", a, ((float)t2)/CLOCKS_PER_SEC);
}

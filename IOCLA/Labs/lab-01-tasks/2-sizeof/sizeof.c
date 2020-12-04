#include <stdio.h>
int main()
{
    char charT;
    short shortT;
    int integerT;	
    unsigned int unsignedIntegerT;
    long longT;
    long long longlongT;
    void * voidT;

    printf("%zu\n%zu\n%zu\n%zu\n%zu\n%zu\n%zu\n", sizeof(charT), sizeof(shortT), sizeof(integerT), sizeof(unsignedIntegerT),
            sizeof(longT), sizeof(longlongT), sizeof(voidT));

    return 0;
}

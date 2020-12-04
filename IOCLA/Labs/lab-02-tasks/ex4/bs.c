#include <stdio.h>

int main() {
    int c = 0, first, last, middle, n, search, array[10];

    printf("Enter number of elements\n");
    scanf("%d", &n);

init:
    scanf("%d", &array[c]);
    c++;
    if (c < n)
        goto init;

    printf("Enter value to find\n");
    scanf("%d", &search);

    first = 0;
    last = n - 1;
    middle = (first + last)/2;

start:
    if (array[middle] < search) {
        first = middle + 1;
        goto next;
    }
    if (array[middle] == search) {
        printf("%d found at location %d.\n", search, middle + 1);
        goto out;
    }
    last = middle - 1;
next:

    middle = (first + last) / 2;
    if (first <= last)
        goto start;
out:  

    if (first > last)
        printf("Not found!\n");

    return 0;
}
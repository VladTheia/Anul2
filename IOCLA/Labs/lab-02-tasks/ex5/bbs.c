#include <stdio.h>

int main() {
    int array[10], n, c = 0, d, swap;

    printf("The number of elements is ");
    scanf("%d", &n);

    printf("Enter %d elements\n", n);

init:
    scanf("%d", &array[c]);
    c++;
    if (c < n)
        goto init;

    c = 0;
    d = 0;

cLable:
dLable:
    if (array[d] > array[d + 1]) {
        swap = array[d];
        array[d] = array[d + 1];
        array[d + 1] = swap;
    }
    d++;
    if (d < n - c - 1)
        goto dLable;
    c++;
    d = 0;
    if (c < n - 1)
        goto cLable;

    printf("Sorted array is\n");
    c = 0;
print:
    printf("%d ", array[c]);
    c++;
    if (c < n)
        goto print;
    printf("\n");

    return 0;
}
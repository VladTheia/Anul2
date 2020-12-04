#include <stdio.h>

int main() {
    int array[10], maximum, size, c = 0, location = 1;
    printf("Enter the number of elements\n");
    scanf("%d", &size);
    printf("Enter %d elements\n", size);

init:
    scanf("%d", &array[c]);
    c++;
    if(c < size)
        goto init;
    maximum = array[0];
    c = 1;
lable:
    if(array[c] > maximum) {
        maximum = array[c];
        location = c + 1;
    }
    c++;
    if(c < size)
        goto lable;

    printf("Maximum element is %d at location %d.\n", maximum, location);

} 
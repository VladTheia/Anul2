#include <stdio.h>

int main() {
    char str1[10];
    char str2[10];

    scanf("%s", str1);
    scanf("%s", str2);

    char *p = str1;
    int size = sizeof(str2);

lable:
if (strncmp(p, str2, size) == 0) {
    printf("Gasit\n");
    return 0;
}
p++;
if (p = strchr(p, *str2) != 0)
    goto lable;

printf("Nu a fost gasit\n");
return 0;
}
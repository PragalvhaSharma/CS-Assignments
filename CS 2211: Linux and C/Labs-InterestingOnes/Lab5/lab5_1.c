
#include <stdio.h>

int main() {
    int number;

    printf("Enter a three-digit number: ");
    scanf("%d", &number);

    int ones = number % 10;
    int tens = (number / 10) % 10;
    int hundreds = number / 100;

    int reversedNumber = ones * 100 + tens * 10 + hundreds;

    printf("The reversed number is %d\n", reversedNumber);

    return 0;

}
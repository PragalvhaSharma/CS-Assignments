#include <stdio.h>

int main() {
    int number;

    // Prompt the user to enter a three-digit number
    printf("Enter a three-digit number: ");
    scanf("%d", &number);

    // Extract the ones, tens, and hundreds digits
    int ones = number % 10;          // Ones place digit
    int tens = (number / 10) % 10;   // Tens place digit
    int hundreds = number / 100;     // Hundreds place digit

    // Reassemble the digits in reverse order
    int reversedNumber = ones * 100 + tens * 10 + hundreds;

    // Display the reversed number
    printf("The reversed number is %d\n", reversedNumber);

    return 0;
}

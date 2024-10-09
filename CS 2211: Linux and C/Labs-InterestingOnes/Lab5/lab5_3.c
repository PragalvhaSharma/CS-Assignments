#include <stdio.h>

int main() {
    int num1, denom1, num2, denom2;

    // Prompt the user to enter two fractions separated by a plus sign (e.g., 1/2+3/4)
    printf("Enter two fractions separated by a plus sign: ");
    // Read the numerators and denominators of the two fractions from the user's input
    scanf("%d/%d+%d/%d", &num1, &denom1, &num2, &denom2);

    // Calculate the numerator of the resulting fraction
    int resultNum = num1 * denom2 + num2 * denom1;

    // Calculate the denominator of the resulting fraction
    int resultDenom = denom1 * denom2;

    // Display the sum of the two fractions
    printf("The sum is %d/%d\n", resultNum, resultDenom);

    return 0;
}

#include <stdio.h>

// Ones place conversion
void convert_to_ones(int number) {
    switch (number) {
        case 1: 
            printf("one"); break;
        case 2: 
            printf("two"); break;
        case 3: 
            printf("three"); break;
        case 4: 
            printf("four"); break;
        case 5:     
            printf("five"); break;
        case 6: 
            printf("six"); break;
        case 7: 
            printf("seven"); break;
        case 8: 
            printf("eight"); break;
        case 9: 
            printf("nine"); break;
        default: 
            break;
    }
}

// Tens and special cases conversion
void convert_to_tens(int num) {
    if (num >= 10 && num <= 19) {
        // Special case for numbers between 10 and 19
        switch (num) {
            case 10: 
                printf("ten"); break;
            case 11: 
                printf("eleven"); break;
            case 12: 
                printf("twelve"); break;
            case 13: 
                printf("thirteen"); break;
            case 14: 
                printf("fourteen"); break;
            case 15: 
                printf("fifteen"); break;
            case 16: 
                printf("sixteen"); break;
            case 17: 
                printf("seventeen"); break;
            case 18: 
                printf("eighteen"); break;
            case 19: 
                printf("nineteen"); break;
        }
    } else {
        // Handle multiples of ten (20, 30, etc.)
        switch (num / 10) {
            case 2: 
                printf("twenty"); break;
            case 3: 
                printf("thirty"); break;
            case 4: 
                printf("forty"); break;
            case 5: 
                printf("fifty"); break;
            case 6: 
                printf("sixty"); break;
            case 7: 
                printf("seventy"); break;
            case 8: 
                printf("eighty"); break;
            case 9: 
                printf("ninety"); break;
            default: 
                break;
        }

        // Print the ones place if needed (e.g., twenty-five)
        if (num % 10 != 0) {
            printf("-");
            convert_to_ones(num % 10);
        }
    }
}

// Hundreds place conversion
void convert_to_100s(int num) {
    if (num >= 100) {
        convert_to_ones(num / 100);  // Print the hundreds part
        printf(" hundred");
        if (num % 100 != 0) {
            printf(" and ");
            convert_to_tens(num % 100);  // Handle the remaining part (tens and ones)
        }
    } else {
        convert_to_tens(num);  // Directly call tens for numbers below 100
    }
}

int main() {
    int userInput;

    do {
        printf("Please enter a value (1-999, 0 to quit): ");
        scanf("%d", &userInput);

        if (userInput > 0 && userInput <= 999) {
            printf("You entered the number ");
            if (userInput < 10) {
                // Directly handle ones place (single digits)
                convert_to_ones(userInput);
            } else {
                // Handle tens and hundreds
                convert_to_100s(userInput);
            }
            printf("\n");  // Print newline after the conversion
        } else if (userInput != 0) {
            printf("Invalid input. Please enter a value between 1 and 999.\n");
        }

    } while (userInput != 0);  // Loop until the user enters 0 to quit

    printf("You entered the number 0, quitting...\n");
    return 0;
}

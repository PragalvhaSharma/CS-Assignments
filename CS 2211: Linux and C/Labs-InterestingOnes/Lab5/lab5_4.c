#include <stdio.h>
#include <stdlib.h>

// Function prototypes
void multiplication(int m, int n, int numbers[m][n]); 
void addition(int m, int n, int numbers[m][n]);
void subtraction(int m, int n, int numbers[m][n]);
void display(int m, int n, int numbers[m][n]);

int main() {
    int n, choice;

    // Prompt the user for the number of columns
    printf("Enter the number of columns (n): ");
    scanf("%d", &n);

    // Create a 3xN array
    int numbers[3][n];

    // Get user input for the first row
    printf("Enter %d numbers for the first row:\n", n);
    for (int i = 0; i < n; i++) {
        scanf("%d", &numbers[0][i]);
    }

    // Use rand() to generate random numbers for the second row
    for (int i = 0; i < n; i++) {
        numbers[1][i] = rand() % 201; // Generates numbers between 0 and 200
    }

    // Initialize the third row (result row) to zero
    for (int i = 0; i < n; i++) {
        numbers[2][i] = 0;
    }

    // Prompt user to choose an operation (without displaying the initial result row)
    do {
        printf("\nChoose an operation from the following:\n");
        printf("[(0) Addition, (1) Subtraction, (2) Multiplication, (4) Quit]: ");
        scanf("%d", &choice);

        switch (choice) {
            case 0:
                addition(3, n, numbers);
                display(3, n, numbers);
                break;
            case 1:
                subtraction(3, n, numbers);
                display(3, n, numbers);
                break;
            case 2:
                multiplication(3, n, numbers);
                display(3, n, numbers);
                break;
            case 4:
                printf("Quitting program.\n");
                break;
            default:
                printf("Invalid choice. Please try again.\n");
        }
    } while (choice != 4);

    return 0;
}

// Function for element-wise addition
void addition(int m, int n, int numbers[m][n]) {
    for (int i = 0; i < n; i++) {
        numbers[2][i] = numbers[0][i] + numbers[1][i];
    }
}

// Function for element-wise subtraction
void subtraction(int m, int n, int numbers[m][n]) {
    for (int i = 0; i < n; i++) {
        numbers[2][i] = numbers[0][i] - numbers[1][i];
    }
}

// Function for element-wise multiplication
void multiplication(int m, int n, int numbers[m][n]) {
    for (int i = 0; i < n; i++) {
        numbers[2][i] = numbers[0][i] * numbers[1][i];
    }
}

// Function to display the 2D array
void display(int m, int n, int numbers[m][n]) {
    for (int i = 0; i < n; i++) {
        printf("%d\t", numbers[0][i]);
    }
    printf("\n");

    for (int i = 0; i < n; i++) {
        printf("%d\t", numbers[1][i]);
    }
    printf("\n");

    for (int i = 0; i < n; i++) {
        printf("%d\t", numbers[2][i]);
    }
    printf("\n");
}

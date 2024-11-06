#include <stdio.h>   
#include <stdlib.h>  

int main(int argc, char *argv[]) {
    int sum = 0; // Variable to store the cumulative sum of the arguments

    // Check if at least one number is provided as a command-line argument
    if (argc < 2) {
        // Print an error message if no numbers are provided
        fprintf(stderr, "Usage: %s number1 [number2 ... numberN]\n", argv[0]);
        return 1; // Return with a non-zero status to indicate an error
    }

    // Iterate over each command-line argument (excluding the program name at argv[0])
    for (int i = 1; i < argc; i++) {
        int num = atoi(argv[i]); // Convert argument string to integer
        sum += num;              // Add the integer value to the cumulative sum
    }

    // Print the result of the sum
    printf("Sum: %d\n", sum);
    return 0; // Return with zero status to indicate successful execution
}

#include <stdio.h>

int main(int argc, char *argv[]) {
    // Check if at least one argument is provided
    if (argc < 2) {
        // Display usage message if no arguments are provided
        fprintf(stderr, "Usage: %s word1 [word2 ... wordN]\n", argv[0]);
        return 1; // Return 1 to indicate an error
    }

    printf("Reversed message: ");

    // Loop over the arguments in reverse order, starting from the last argument
    for (int i = argc - 1; i >= 1; i--) {
        // Print each word in reverse order
        printf("%s", argv[i]);

        // Add a space between words, but avoid trailing space after the last word
        if (i > 1) {
            printf(" ");
        }
    }

    // Print a newline after the reversed message
    printf("\n");
    return 0; // Return 0 to indicate successful execution
}

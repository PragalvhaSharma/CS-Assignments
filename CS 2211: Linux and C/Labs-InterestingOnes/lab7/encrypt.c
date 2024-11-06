#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>

char encrypt_char(char c, int shift) {
    char c_new;

    // Shift uppercase letters
    if (isupper(c)) {
        c_new = 'A' + (c - 'A' + shift) % 26;
        if (c_new < 'A') c_new += 26;
    }
    // Shift lowercase letters
    else if (islower(c)) {
        c_new = 'a' + (c - 'a' + shift) % 26;
        if (c_new < 'a') c_new += 26;
    }
    // Keep non-letter characters as is
    else {
        c_new = c;
    }
    return c_new;
}

int main(int argc, char *argv[]) {
    // Check that there are at least two arguments (message and shift value)
    if (argc < 3) {
        fprintf(stderr, "Usage: %s message shift\n", argv[0]);
        return 1;
    }

    // Parse the shift value from the last argument
    int shift = atoi(argv[argc - 1]);

    printf("Encrypted message: ");

    // Iterate over each word in the message
    for (int i = 1; i < argc - 1; i++) {
        char *word = argv[i];

        // Iterate over each character in the word
        for (int j = 0; word[j] != '\0'; j++) {
            char c = word[j];
            char c_new = encrypt_char(c, shift);
            printf("%c", c_new);
        }
        // Add space between words except after the last word
        if (i < argc - 2) {
            printf(" ");
        }
    }
    printf("\n");
    return 0;
}

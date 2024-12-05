/*
Name of the program: interact.c
Purpose: 
This program handles user interaction and file I/O for the calendar reminder app.
It includes functions for reading user input, handling signals, and saving/loading reminders.

Author:
Student Name: Pragalvha Sharma
Student Number: 251354461
Student Username: pshar223
*/

#include "interact.h"
#include <signal.h>

// Signal handler function
static void signalHandler(int signum) {
    if (signum == SIGINT) {
        printf("\nProgram interrupted...\n");
        printf("Writing to file...\n");
        cleanup();
        exit(0);  // Exit with success status for Ctrl+C
    } else {
        printf("\nReceived signal %d\n", signum);
        cleanup();
        exit(signum);
    }
}

// Set up signal handlers
void setupSignalHandlers(void) {
    signal(SIGINT, signalHandler);
    signal(SIGTERM, signalHandler);
    signal(SIGSEGV, signalHandler);
}

// Clean up resources and save data
void cleanup(void) {
    saveRemindersToFile();
    cleanupCalendar();  // Add this to ensure memory is freed
}

// Flush input buffer
void flushBuffer(void) {
    int c;
    while ((c = getchar()) != '\n' && c != EOF);
}

// Read reminder from user
int readReminder(char* str, int n) {
    int day;
    char temp_str[MAX_INPUT_LEN];

    if (fgets(temp_str, n, stdin) == NULL) {
        return -1;
    }

    temp_str[strcspn(temp_str, "\n")] = '\0';

    if (sscanf(temp_str, "%d %[^\n]", &day, str) < 1) {
        printf("Invalid input. Please enter in format: 'day reminder'\n");
        return -1;
    }

    if (day == 0) {
        return 0;
    }

    if (day < 0 || day > month.month_days) {
        printf("Invalid day: The day must be >= 1 and <=%d days\n", month.month_days);
        return -1;
    }

    if (strlen(str) == 0) {
        printf("No reminder text entered.\n");
        return -1;
    }

    return day;
}

// Save reminders to file
void saveRemindersToFile(void) {
    FILE* file = fopen(FILENAME, "w");
    if (file == NULL) {
        fprintf(stderr, "Error opening file for writing\n");
        return;
    }

    for (int i = 0; i < month.month_days; i++) {
        struct Node* current = month.reminders[i];
        while (current != NULL) {
            fprintf(file, "%d %s\n", i + 1, current->reminder);
            current = current->next;
        }
    }

    fclose(file);
}

// Load reminders from file
void loadRemindersFromFile(void) {
    FILE* file = fopen(FILENAME, "r");
    if (file == NULL) {
        // File doesn't exist yet, not an error
        return;
    }

    char line[MAX_INPUT_LEN];
    int day;
    char reminder[MAX_REMINDER_LEN];

    while (fgets(line, sizeof(line), file) != NULL) {
        line[strcspn(line, "\n")] = '\0';
        if (sscanf(line, "%d %[^\n]", &day, reminder) == 2) {
            if (day > 0 && day <= month.month_days) {
                insertToCalendar(day, reminder);
            }
        }
    }

    fclose(file);
} 
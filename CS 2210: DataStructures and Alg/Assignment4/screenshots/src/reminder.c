/*
Name of the program: reminder.c
Purpose: 
This program lets you add reminders to a calendar and view them. It includes functions to enter, save, and display reminders.

Author:
Student Name: Pragalvha Sharma
Student Number: 251354461
Student Username: pshar223
*/

#include "reminder.h"

// Initialize the month structure with current month's data
void initializeMonth(void) {
    // Force November settings
    month.month_idx = 10;  // 10 for November (0-based index)
    month.start_day = 5;   // Friday is 5 (0-based index: Sun=0, Mon=1, ..., Sat=6)

    month.month_days = 30; // November has 30 days

    // Initialize the reminders array
    for (int i = 0; i < 31; i++) {
        month.reminders[i] = NULL;
    }
}

// Insert a reminder into the calendar
void insertToCalendar(int day, const char* value) {
    if (day < 1 || day > month.month_days) {
        printf("Invalid day: %d\n", day);
        return;
    }

    month.reminders[day - 1] = addNode(month.reminders[day - 1], value);
}

// Clean up the calendar by freeing all reminder lists
void cleanupCalendar(void) {
    for (int i = 0; i < month.month_days; i++) {
        if (month.reminders[i] != NULL) {
            freeList(month.reminders[i]);
            month.reminders[i] = NULL;
        }
    }
}

// Print the calendar and reminders
void printCalendar(void) {
    const char* month_names[] = {
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    };
    const char* weekday_names[] = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

    // Print weekday headers
    for (int i = 0; i < 7; i++) {
        printf("%-6s", weekday_names[i]);
    }
    printf("\n");

    // Print initial spaces
    int weekday = 0;
    for (int i = 0; i < month.start_day; i++) {
        printf("      ");
        weekday++;
    }

    // Print days
    for (int day = 1; day <= month.month_days; day++) {
        char day_str[7]; // 6 chars + null terminator
        int has_reminder = (countNodes(month.reminders[day - 1]) > 0);

        if (has_reminder) {
            // Days with reminders (in parentheses)
            // For single-digit days
            if (day < 10) {
                snprintf(day_str, sizeof(day_str), "(%d)  ", day); // e.g., "(5)  "
            } else {
                snprintf(day_str, sizeof(day_str), "(%d) ", day);  // e.g., "(15) "
            }
        } else {
            // Days without reminders
            // For single-digit days
            if (day < 10) {
                snprintf(day_str, sizeof(day_str), " %d    ", day); // e.g., "5     "
            } else {
                snprintf(day_str, sizeof(day_str), " %d   ", day);  // e.g., "15    "
            }
        }

        // Print the day string
        printf("%-6s", day_str);

        weekday++;

        if (weekday == 7) {
            printf("\n");
            weekday = 0;
        }
    }

    // Add final newline if needed
    if (weekday != 0) {
        printf("\n");
    }

    // Print reminders in order
    printf("%s reminders:\n", month_names[month.month_idx]);
    for (int day = 1; day <= month.month_days; day++) {
        if (month.reminders[day - 1] != NULL) {
            const char* day_name = weekday_names[(month.start_day + (day - 1)) % 7];
            struct Node* current = month.reminders[day - 1];
            int count = 1;
            
            // Print first reminder with the day header - adding space for single digits
            printf("%s %2d:: (%d) %s", day_name, day, count++, current->reminder);
            current = current->next;
            
            // Print subsequent reminders with proper alignment
            while (current != NULL) {
                printf("\n         (%d) %s", count++, current->reminder);
                current = current->next;
            }
            printf("\n");
        }
    }
    
    // Add underline after reminders
    printf("_______________\n");
}

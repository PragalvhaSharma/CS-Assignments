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

// Function to insert a reminder into the calendar
void insert_to_calendar(int day, const char* value) {
    // Check if the day is valid
    if (day < 1 || day > November.month_days) {
        printf("Invalid day: %d\n", day);
        return;
    }

    // Insert the reminder into the calendar
    November.reminders[day - 1] = true; // Arrays are 0-indexed
    strncpy(November.reminder_str[day - 1], value, MAX_STR_LEN);
}

// Function to read a reminder from the user
int read_reminder(char *str, int n) {
    int day;
    char temp_str[MAX_STR_LEN];

    // Read the line from the user
    fgets(temp_str, n, stdin);

    // Remove the trailing newline character
    temp_str[strcspn(temp_str, "\n")] = '\0';

    // Parse the day and reminder
    // Expected format: "day reminder"
    if (sscanf(temp_str, "%d %[^\n]", &day, str) < 1) {
        printf("Invalid input. Please enter in format 'day reminder'\n");
        return -1; // Indicate error
    }

    // Handle 0 to quit
    if (day == 0) {
        return 0;
    }

    // If no reminder text is provided
    if (strlen(str) == 0) {
        printf("No reminder entered.\n");
        return -1;
    }

    return day;
}

// Function to print the calendar and the reminders
void print_calendar() {
    int day, weekday;
    const int DAY_FIELD_WIDTH = 6; // Field width for each day

    // Weekday names
    char *weekday_names[] = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

    // Print weekday headers
    for (int i = 0; i < 7; i++) {
        printf("%-*s", DAY_FIELD_WIDTH, weekday_names[i]);
    }
    printf("\n");

    weekday = November.start_day; // Start from the starting day of the month

    // Print initial spaces for the first week
    for (int i = 0; i < weekday; i++) {
        printf("%*s", DAY_FIELD_WIDTH, ""); // Print empty spaces
    }

    // Print the days of the month
    for (day = 1; day <= November.month_days; day++) {
        char day_str[DAY_FIELD_WIDTH + 1]; // +1 for null terminator

        if (November.reminders[day - 1]) {
            // Day with a reminder, format with parentheses
            snprintf(day_str, sizeof(day_str), "(%2d)", day); // e.g., "(13)"
        } else {
            // Regular day, add spaces to match width
            snprintf(day_str, sizeof(day_str), " %2d ", day); // e.g., " 13 "
        }

        printf("%-*s", DAY_FIELD_WIDTH, day_str);

        // Move to the next line after Saturday
        if ((weekday + 1) % 7 == 0) {
            printf("\n");
        }

        // Increment weekday
        weekday = (weekday + 1) % 7;
    }

    // Add a newline if the last week isn't complete
    if (weekday != 0) {
        printf("\n");
    }

    // Print the list of reminders
    printf("\nReminders:\n");
    for (day = 1; day <= November.month_days; day++) {
        if (November.reminders[day - 1]) {
            printf("Day %d: %s\n", day, November.reminder_str[day - 1]);
        }
    }
    // Print a separator line
    printf("___________________________\n");
}

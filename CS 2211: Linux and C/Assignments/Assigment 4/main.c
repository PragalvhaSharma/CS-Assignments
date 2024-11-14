/*
Name of the program: main.c
Purpose: 
This program is the main starting point for the calendar reminder app. It sets up the calendar, interacts with the user, 
and uses functions to add reminders and show the calendar.

Author:
Student Name: Pragalvha Sharma
Student Number: 251354461
Student Username: pshar223
*/
#include "reminder.h"

// Declare the global variable November
struct Month November;

int main() {
    // Initialize November
    November.month_days = 31;  // Adjusted to 31 days to have November end on Sunday
    November.start_day = 5;    // November starts on Friday (day 5), where 0=Sunday

    // Initialize the reminders array and reminder strings
    for (int i = 0; i < 31; i++) {
        November.reminders[i] = false;
        strcpy(November.reminder_str[i], "");
    }

    char reminder[MAX_STR_LEN]; // Buffer to store the reminder string
    int day;                    // Variable to store the day number

    // Loop to read reminders from the user
    while (1) {
        printf("Enter day and reminder (0 to quit): ");
        day = read_reminder(reminder, MAX_STR_LEN);

        // Check if the user wants to exit
        if (day == 0) {
            break;
        }

        // If read_reminder returns -1, there was an error; skip this iteration
        if (day == -1) {
            continue;
        }

        // Insert the reminder into the calendar
        insert_to_calendar(day, reminder);

        // Print the updated calendar and reminders
        print_calendar();
    }

    return 0;
}

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
#include "interact.h"

// Declare the global variable month
struct Month month;

int main() {
    // Set up signal handlers
    setupSignalHandlers();

    // Initialize the month structure
    initializeMonth();

    // Load existing reminders
    loadRemindersFromFile();

    char reminder[MAX_REMINDER_LEN];
    int day;

    // Main loop to read reminders from the user
    while (1) {
        printf("\nEnter day and reminder (0 to quit): ");
        day = readReminder(reminder, MAX_REMINDER_LEN);

        if (day == 0) {
            break;
        }

        if (day == -1) {
            continue;
        }

        insertToCalendar(day, reminder);
        printCalendar();
    }

    // Clean up before exit
    cleanup();

    return 0;
}

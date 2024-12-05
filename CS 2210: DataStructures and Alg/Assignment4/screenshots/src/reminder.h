/*
Name of the program: reminder.h
Purpose: 
Header file that defines the Month structure and declares core calendar functions
for managing reminders and displaying the calendar.

Author:
Student Name: Pragalvha Sharma
Student Number: 251354461
Student Username: pshar223
*/

#ifndef REMINDER_H
#define REMINDER_H

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <time.h>
#include "linked_list.h"

struct Month {
    int month_idx;
    int month_days;
    int start_day;
    struct Node* reminders[31];  // Array of linked lists for multiple reminders
};

extern struct Month month;

// Function declarations
void initializeMonth(void);
void insertToCalendar(int day, const char* value);
void printCalendar(void);
void cleanupCalendar(void);

#endif
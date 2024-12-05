/*
Name of the program: interact.h
Purpose: 
Header file for interact.c that declares functions and constants for user interaction
and file I/O operations in the calendar reminder app.

Author:
Student Name: Pragalvha Sharma
Student Number: 251354461
Student Username: pshar223
*/

#ifndef INTERACT_H
#define INTERACT_H

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "reminder.h"

#define FILENAME "reminders.txt"
#define MAX_INPUT_LEN 200

// Function declarations
void flushBuffer(void);
int readReminder(char* str, int n);
void saveRemindersToFile(void);
void loadRemindersFromFile(void);
void setupSignalHandlers(void);
void cleanup(void);

#endif 
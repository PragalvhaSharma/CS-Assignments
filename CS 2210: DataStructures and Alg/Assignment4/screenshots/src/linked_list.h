/*
Name of the program: linked_list.h
Purpose: 
Header file for linked_list.c that defines the Node structure and declares
functions for managing the linked list of reminders.

Author:
Student Name: Pragalvha Sharma
Student Number: 251354461
Student Username: pshar223
*/

#ifndef LINKED_LIST_H
#define LINKED_LIST_H

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX_REMINDER_LEN 100

// Node structure for the linked list
struct Node {
    char reminder[MAX_REMINDER_LEN];
    struct Node* next;
};

// Function declarations
struct Node* createNode(const char* reminder);
struct Node* addNode(struct Node* head, const char* reminder);
void deleteNode(struct Node** head, const char* reminder);
void freeList(struct Node* head);
void printList(struct Node* head);
int countNodes(struct Node* head);

#endif 
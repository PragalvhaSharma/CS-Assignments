/*
Name of the program: linked_list.c
Purpose: 
This program implements a linked list data structure for storing multiple reminders per day.
It provides functions to create, add, delete, and manage reminder nodes.

Author:
Student Name: Pragalvha Sharma
Student Number: 251354461
Student Username: pshar223
*/

#include "linked_list.h"

// Create a new node
struct Node* createNode(const char* reminder) {
    struct Node* newNode = (struct Node*)malloc(sizeof(struct Node));
    if (newNode == NULL) {
        fprintf(stderr, "Memory allocation failed!\n");
        return NULL;
    }
    strncpy(newNode->reminder, reminder, MAX_REMINDER_LEN - 1);
    newNode->reminder[MAX_REMINDER_LEN - 1] = '\0';
    newNode->next = NULL;
    return newNode;
}

// Add a new node to the list
struct Node* addNode(struct Node* head, const char* reminder) {
    struct Node* newNode = createNode(reminder);
    if (newNode == NULL) return head;

    if (head == NULL) {
        return newNode;
    }

    struct Node* current = head;
    while (current->next != NULL) {
        current = current->next;
    }
    current->next = newNode;
    return head;
}

// Delete a node with specific reminder
void deleteNode(struct Node** head, const char* reminder) {
    if (*head == NULL) return;

    struct Node* current = *head;
    struct Node* prev = NULL;

    while (current != NULL && strcmp(current->reminder, reminder) != 0) {
        prev = current;
        current = current->next;
    }

    if (current == NULL) return;

    if (prev == NULL) {
        *head = current->next;
    } else {
        prev->next = current->next;
    }
    free(current);
}

// Free the entire list
void freeList(struct Node* head) {
    struct Node* current = head;
    while (current != NULL) {
        struct Node* temp = current;
        current = current->next;
        free(temp);
    }
}

// Print all reminders in the list
void printList(struct Node* head) {
    struct Node* current = head;
    while (current != NULL) {
        printf("%s\n", current->reminder);
        current = current->next;
    }
}

// Count number of nodes in the list
int countNodes(struct Node* head) {
    int count = 0;
    struct Node* current = head;
    while (current != NULL) {
        count++;
        current = current->next;
    }
    return count;
} 
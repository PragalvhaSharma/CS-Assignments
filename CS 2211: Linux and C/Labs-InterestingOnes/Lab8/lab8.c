#include <stdio.h>
#include <string.h>
#include <stdbool.h>
#include <ctype.h>

#define MAX_PEOPLE 100

// Enums for role types and menu choices
typedef enum {
    ADD = 1,
    UPDATE,
    PRINT,
    DELETE,
    EXIT
} Choice;

typedef enum {
    STUDENT,
    PROFESSOR
} RoleType;

// Structures for student and professor information
typedef struct {
    int StudentId;
    float GPA;
} StudentInfo;

typedef struct {
    int ProfessorId;
    double salary;
} ProfessorInfo;

// Union to hold either student or professor information
typedef union {
    StudentInfo student;
    ProfessorInfo professor;
} RoleInfo;

// Structure to represent a person
typedef struct {
    RoleType role;
    char name[51];
    RoleInfo info;
} Person;

// Global array to store people and a counter for the number of people
Person people[MAX_PEOPLE];
int numPeople = 0;

// Function prototypes
void printMenu(void);
bool personExist(RoleType role, int id);
void readNames(char *name);
void printPerson(Person *p);
void addPerson(void);
void updatePerson(void);
void deletePerson(void);

// Function to print the menu options
void printMenu(void) {
    printf("\nMenu:\n");
    printf("1. Add a person\n");
    printf("2. Update a person\n");
    printf("3. Print all people\n");
    printf("4. Delete a person\n");
    printf("5. Exit\n");
}

// Function to check if a person exists based on role and ID
bool personExist(RoleType role, int id) {
    for (int i = 0; i < numPeople; i++) {
        if (people[i].role == role) {
            if ((role == STUDENT && people[i].info.student.StudentId == id) ||
                (role == PROFESSOR && people[i].info.professor.ProfessorId == id)) {
                return true;
            }
        }
    }
    return false;
}

// Function to read a name, skipping leading spaces and limiting to 50 characters
void readNames(char *name) {
    int c, i = 0;
    // Skip leading white spaces
    while (isspace(c = getchar()) && c != '\n');
    // Read characters until newline or max length
    while (c != '\n' && c != EOF && i < 50) {
        name[i++] = c;
        c = getchar();
    }
    name[i] = '\0';
    // Discard remaining characters in the buffer
    while (c != '\n' && c != EOF) {
        c = getchar();
    }
}

// Function to print a person's information
void printPerson(Person *p) {
    printf("-------------------------\n");
    printf("Name: %s\n", p->name);
    if (p->role == STUDENT) {
        printf("Role: Student\n");
        printf("ID: %d\n", p->info.student.StudentId);
        printf("GPA: %.2f\n", p->info.student.GPA);
    } else if (p->role == PROFESSOR) {
        printf("Role: Professor\n");
        printf("ID: %d\n", p->info.professor.ProfessorId);
        printf("Salary: %.2lf\n", p->info.professor.salary);
    }
}

// Function to add a new person to the database
void addPerson(void) {
    if (numPeople >= MAX_PEOPLE) {
        printf("Database is full.\n");
        return;
    }

    int roleInput;
    printf("Enter the role (0 for Student, 1 for Professor): ");
    scanf("%d", &roleInput);

    // Clear input buffer
    while (getchar() != '\n');

    RoleType role;
    if (roleInput == 0) {
        role = STUDENT;
    } else if (roleInput == 1) {
        role = PROFESSOR;
    } else {
        printf("Invalid role type.\n");
        return;
    }

    Person newPerson;
    newPerson.role = role;

    printf("Enter student's name: ");
    readNames(newPerson.name);

    if (role == STUDENT) {
        printf("Enter Student's ID: ");
        scanf("%d", &newPerson.info.student.StudentId);

        printf("Enter student's GPA: ");
        scanf("%f", &newPerson.info.student.GPA);

        // Check for duplicate ID within students
        if (personExist(STUDENT, newPerson.info.student.StudentId)) {
            printf("A student with this ID already exists.\n");
            return;
        }
    } else if (role == PROFESSOR) {
        printf("Enter Professor ID: ");
        scanf("%d", &newPerson.info.professor.ProfessorId);

        printf("Enter Salary: ");
        scanf("%lf", &newPerson.info.professor.salary);

        // Check for duplicate ID within professors
        if (personExist(PROFESSOR, newPerson.info.professor.ProfessorId)) {
            printf("A professor with this ID already exists.\n");
            return;
        }
    }

    people[numPeople++] = newPerson;
    printf("Person added successfully.\n");
}

// Function to update an existing person's information
void updatePerson(void) {
    int roleInput, id;
    printf("Enter role (0 for Student, 1 for Professor): ");
    scanf("%d", &roleInput);

    RoleType role;
    if (roleInput == 0) {
        role = STUDENT;
    } else if (roleInput == 1) {
        role = PROFESSOR;
    } else {
        printf("Invalid role type.\n");
        return;
    }

    printf("Enter ID: ");
    scanf("%d", &id);

    // Clear input buffer
    while (getchar() != '\n');

    for (int i = 0; i < numPeople; i++) {
        if (people[i].role == role) {
            if ((role == STUDENT && people[i].info.student.StudentId == id) ||
                (role == PROFESSOR && people[i].info.professor.ProfessorId == id)) {

                printf("Enter new name: ");
                readNames(people[i].name);

                if (role == STUDENT) {
                    printf("Enter new Student ID: ");
                    scanf("%d", &people[i].info.student.StudentId);

                    printf("Enter new GPA: ");
                    scanf("%f", &people[i].info.student.GPA);

                    // Check for duplicate ID (excluding current person)
                    for (int j = 0; j < numPeople; j++) {
                        if (j != i && people[j].role == STUDENT &&
                            people[j].info.student.StudentId == people[i].info.student.StudentId) {
                            printf("A student with this ID already exists.\n");
                            return;
                        }
                    }
                } else {
                    printf("Enter new Professor ID: ");
                    scanf("%d", &people[i].info.professor.ProfessorId);

                    printf("Enter new Salary: ");
                    scanf("%lf", &people[i].info.professor.salary);

                    // Check for duplicate ID (excluding current person)
                    for (int j = 0; j < numPeople; j++) {
                        if (j != i && people[j].role == PROFESSOR &&
                            people[j].info.professor.ProfessorId == people[i].info.professor.ProfessorId) {
                            printf("A professor with this ID already exists.\n");
                            return;
                        }
                    }
                }

                printf("Person updated successfully.\n");
                return;
            }
        }
    }
    printf("Person not found.\n");
}

// Function to delete a person from the database
void deletePerson(void) {
    int roleInput, id;
    printf("Enter role (0 for Student, 1 for Professor): ");
    scanf("%d", &roleInput);

    RoleType role;
    if (roleInput == 0) {
        role = STUDENT;
    } else if (roleInput == 1) {
        role = PROFESSOR;
    } else {
        printf("Invalid role type.\n");
        return;
    }

    printf("Enter ID: ");
    scanf("%d", &id);

    for (int i = 0; i < numPeople; i++) {
        if (people[i].role == role) {
            if ((role == STUDENT && people[i].info.student.StudentId == id) ||
                (role == PROFESSOR && people[i].info.professor.ProfessorId == id)) {
                // Shift all elements after the found index to the left by one
                for (int j = i; j < numPeople - 1; j++) {
                    people[j] = people[j + 1];
                }
                numPeople--;
                printf("Person deleted successfully.\n");
                return;
            }
        }
    }
    printf("Person not found.\n");
}

// Main function to run the program
int main() {
    int choiceInput;

    while (1) {
        printMenu();
        printf("Enter your choice: ");
        scanf("%d", &choiceInput);

        Choice choice = (Choice)choiceInput;

        // Clear input buffer
        while (getchar() != '\n');

        switch (choice) {
            case ADD:
                addPerson();
                break;
            case UPDATE:
                updatePerson();
                break;
            case PRINT:
                if (numPeople == 0) {
                    printf("No people in the database.\n");
                } else {
                    for (int i = 0; i < numPeople; i++) {
                        printPerson(&people[i]);
                    }
                }
                break;
            case DELETE:
                deletePerson();
                break;
            case EXIT:
                printf("Exiting the program.\n");
                return 0;
            default:
                printf("Invalid choice. Please try again.\n");
        }
    }

    return 0;
}

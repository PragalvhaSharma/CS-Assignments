/*
Name of the program: sorting.c
Purpose: 
This program generates an array of integers, allows the user to sort it using Quick Sort or Bubble Sort, and measures the time taken for sorting.

Author:
Student Name: Pragalvha Sharma
Student Number: 251354461
Student Username: pshar223
*/

#include <stdio.h>
#include <stdlib.h>
#include <time.h>

// Function Prototypes
void generate_random_numbers(int arr[], int start, int end);
void bubble_sort(int arr[], int size);
void quick_sort(int arr[], int low, int high);
int partition(int arr[], int low, int high);
void copy_array(int src[], int dest[], int size);
void print_array(int arr[], int size);
double sort_and_measure_time(int src[], int dest[], int size, int sort_type);

int main() {
    int n, m, x; // Variables to store user inputs and sorting choice
    char command = 'S'; // Initialize to 'S' to allow first sorting operation
    srand(time(NULL)); // Seed the random number generator with current time for randomness

    // Main loop to process user commands until the user decides to quit
    while (command != 'Q' && command != 'q') {
        // Prompt for the number of elements (n) and the array length (m)
        printf("Enter the number of elements you would like to enter (n). n must be an even number: ");
        if (scanf("%d", &n) != 1) { // Validate input for 'n'
            printf("Invalid input for n. Please enter an even integer.\n\n");
            while (getchar() != '\n'); // Clear the input buffer
            continue; // Restart the loop for valid input
        }
        printf("Enter the length of the array (m). m must be an even number and >= n: ");
        if (scanf("%d", &m) != 1) { // Validate input for 'm'
            printf("Invalid input for m. Please enter an even integer >= n.\n\n");
            while (getchar() != '\n'); // Clear the input buffer
            continue; // Restart the loop for valid input
        }

        // Validate that n is <= m and both n and m are even numbers
        if (n > m || n % 2 != 0 || m % 2 != 0) {
            printf("Error: n must be <= m and both n and m must be even numbers.\n\n");
            continue; // Restart the loop for valid input
        }

        // Allocate memory for arrays to store numbers
        int *numbers[2];
        numbers[0] = (int *)malloc(m * sizeof(int)); 
        numbers[1] = (int *)malloc(m * sizeof(int)); 
        if (numbers[0] == NULL || numbers[1] == NULL) { 
            printf("Memory allocation failed.\n");
            free(numbers[0]);
            free(numbers[1]);
            return 1;
        }

        // Allocate memory to store the original 'n' user-entered numbers
        int *original_numbers = (int *)malloc(n * sizeof(int));
        if (original_numbers == NULL) { 
            printf("Memory allocation failed.\n");
            free(numbers[0]); 
            free(numbers[1]);
            return 1;
        }

        // Prompt user to enter 'n' elements and fill remaining with random numbers if input fails
        printf("Enter %d elements: ", n);
        for (int i = 0; i < n; i++) {
            if (scanf("%d", &numbers[0][i]) != 1) {
                printf("Input error. Filling remaining values with random numbers.\n");
                generate_random_numbers(numbers[0], i, n);
                break;
            }
            original_numbers[i] = numbers[0][i]; 
        }

        // Fill the remaining elements with random numbers if n < m
        if (n < m) {
            generate_random_numbers(numbers[0], n, m);
            printf("\nYou entered %d numbers, %d random numbers were generated.\n\n", n, m - n);
        }

        // Sorting and command processing loop
        do {
            // Prompt user for sorting algorithm choice
            printf("Choose a sorting algorithm:\n");
            printf("  (0) Quick Sort\n");
            printf("  (1) Bubble Sort\n");
            printf("Enter your choice (0 or 1): ");
            if (scanf("%d", &x) != 1) {
                printf("Invalid choice. Please enter 0 or 1.\n\n");
                while (getchar() != '\n'); // Clear the input buffer
                continue;
            }

            if (x != 0 && x != 1) {
                printf("Invalid sorting algorithm choice. Please enter 0 or 1.\n\n");
                continue;
            }

            printf("\nRaw array:\n");
            print_array(numbers[0], m);

            double duration = sort_and_measure_time(numbers[0], numbers[1], m, x);

            printf("\nSorted array:\n");
            print_array(numbers[1], m);

            printf("\nSorting duration (seconds): %.6f\n", duration);

            printf("\nEnter a command from the following list:\n");
            printf("  (R) Re-generate different random numbers and choose a different sorting algorithm\n");
            printf("  (N) Enter new values for n and m, and enter new n numbers\n");
            printf("  (S) Use a different sorting algorithm to sort the same array\n");
            printf("  (Q) Quit\n");
            scanf(" %c", &command);

            if (command == 'R' || command == 'r') {
                copy_array(original_numbers, numbers[0], n);
                generate_random_numbers(numbers[0], n, m);
                printf("\nNew random numbers generated, retaining original input values.\n\n");
            } else if (command == 'N' || command == 'n') {
                break;
            } else if (command == 'S' || command == 's') {
            } else if (command == 'Q' || command == 'q') {
                break;
            } else {
                printf("Invalid command. Please try again.\n\n");
            }

        } while (command != 'Q' && command != 'q' && command != 'N' && command != 'n');

        free(numbers[0]);
        free(numbers[1]);
        free(original_numbers);
    }

    printf("\nProgram terminated.\n");
    return 0;
}

/*
Function: generate_random_numbers
Purpose: Fills the array with random integers from index 'start' to 'end'.
Parameters: 
- arr[]: The array to be filled with random numbers
- start: The starting index for random numbers
- end: The ending index for random numbers
*/

void generate_random_numbers(int arr[], int start, int end) {
    for (int i = start; i < end; i++) {
        arr[i] = rand() % 10001;
    }
}

/*
Function: bubble_sort
Purpose: Sorts the array using Bubble Sort algorithm.
Parameters:
- arr[]: The array to be sorted
- size: The size of the array
*/

void bubble_sort(int arr[], int size) {
    for (int i = 0; i < size - 1; i++) {
        for (int j = 0; j < size - i - 1; j++) {
            if (arr[j] > arr[j + 1]) {
                int temp = arr[j];
                arr[j] = arr[j + 1];
                arr[j + 1] = temp;
            }
        }
    }
}

/*
Function: quick_sort
Purpose: Sorts the array using Quick Sort algorithm.
Parameters:
- arr[]: The array to be sorted
- low: Starting index
- high: Ending index
*/

void quick_sort(int arr[], int low, int high) {
    if (low < high) {
        int pi = partition(arr, low, high);
        quick_sort(arr, low, pi - 1);
        quick_sort(arr, pi + 1, high);
    }
}

/*
Function: partition
Purpose: Partitions the array for Quick Sort.
Parameters:
- arr[]: The array to partition
- low: Starting index
- high: Ending index
Returns: The partition index
*/

int partition(int arr[], int low, int high) {
    int pivot = arr[high];
    int i = (low - 1);
    for (int j = low; j <= high - 1; j++) {
        if (arr[j] < pivot) {
            i++;
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }
    int temp = arr[i + 1];
    arr[i + 1] = arr[high];
    arr[high] = temp;
    return (i + 1);
}

/*
Function: copy_array
Purpose: Copies elements from source to destination array.
Parameters:
- src[]: Source array
- dest[]: Destination array
- size: Number of elements to copy
*/

void copy_array(int src[], int dest[], int size) {
    for (int i = 0; i < size; i++) {
        dest[i] = src[i];
    }
}

/*
Function: print_array
Purpose: Prints the elements of the array.
Parameters:
- arr[]: Array to print
- size: Number of elements in the array
*/

void print_array(int arr[], int size) {
    for (int i = 0; i < size; i++) {
        printf("%6d ", arr[i]);
        if ((i + 1) % 10 == 0) {
            printf("\n");
        }
    }
    if (size % 10 != 0) {
        printf("\n");
    }
}

/*
Function: sort_and_measure_time
Purpose: Sorts an array using the chosen algorithm and measures sorting time.
Parameters:
- src[]: Source array
- dest[]: Destination array
- size: Size of the array
- sort_type: Sorting algorithm choice (0 for Quick Sort, 1 for Bubble Sort)
Returns: Time taken to perform sorting in seconds
*/

double sort_and_measure_time(int src[], int dest[], int size, int sort_type) {
    copy_array(src, dest, size);
    clock_t start = clock();
    if (sort_type == 0) {
        quick_sort(dest, 0, size - 1);
    } else {
        bubble_sort(dest, size);
    }
    clock_t end = clock();
    return (double)(end - start) / CLOCKS_PER_SEC;
}


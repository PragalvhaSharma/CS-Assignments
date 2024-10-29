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
    int n, m, x;
    char command;
    srand(time(NULL)); // Seed the random number generator

    // Main loop to process user commands
    do {
        // Prompt for n and m values
        printf("[Enter the number of elements you would like to enter (n). n must be an even number: ");
        scanf("%d", &n);
        printf("[Enter the length of the array (m). m must be an even number and >= n: ");
        scanf("%d", &m);

        // Validate n and m
        if (n > m || n % 2 != 0 || m % 2 != 0) {
            printf("Error: n must be <= m and both n and m must be even numbers.\n");
            continue;
        }

        // Allocate 2D array with 2 rows and m columns
        int numbers[2][m];

        // Get user input for the first row or fill with random numbers if input fails
        printf("[Enter %d elements: ", n);
        for (int i = 0; i < n; i++) {
            if (scanf("%d", &numbers[0][i]) != 1) {
                printf("Input error. Filling remaining values with random numbers.\n");
                generate_random_numbers(numbers[0], i, n);
                break;
            }
        }

        // Fill remaining elements in the first row with random numbers if n < m
        if (n < m) {
            generate_random_numbers(numbers[0], n, m); // Fill from index n to m
            printf("\nYou entered %d numbers, %d random numbers were generated.\n", n, m - n);
        }

        // Sort the array based on user-selected algorithm and measure time
        do {
            // Prompt for sorting algorithm selection
            printf("::Choose a sorting algorithm::\n|| (0)Quick sort | (1)Bubble sort ||\n");
            scanf("%d", &x);

            // Print the original array
            printf("\n\nRaw array\n");
            print_array(numbers[0], m);

            // Sort and measure time
            double duration = sort_and_measure_time(numbers[0], numbers[1], m, x);

            // Print the sorted array
            printf("\nSorted array\n");
            print_array(numbers[1], m);

            // Print sorting duration
            printf("Sorting duration (Sec): %.4f\n", duration);

            // Prompt for command after sorting
            printf("\nEnter a command from the following list:\n");
            printf("(R) Re-generate different random numbers and choose a different sorting algorithm\n");
            printf("(N) Enter new values for n and m, and enter new n numbers\n");
            printf("(S) Use a different sorting algorithm to sort the same array\n");
            printf("(Q) Quit\n");
            scanf(" %c", &command);

            // Process commands based on both uppercase and lowercase inputs
            if (command == 'R' || command == 'r') {
                // Regenerate random numbers for the entire first row
                generate_random_numbers(numbers[0], 0, m);
                printf("New random numbers generated.\n");
            } else if (command == 'N' || command == 'n') {
                // Exit the loop to allow re-entry of n and m values
                break;
            }

        } while (command != 'Q' && command != 'q' && command != 'N' && command != 'n');

    } while (command != 'Q' && command != 'q');

    printf("Program terminated.\n");
    return 0;
}

/**
 * Generates random numbers between 0 and 10000 and fills the specified range.
 * 
 * @param arr   The array to fill with random numbers.
 * @param start The starting index of the range to fill.
 * @param end   The ending index of the range to fill.
 */
void generate_random_numbers(int arr[], int start, int end) {
    for (int i = start; i < end; i++) {
        arr[i] = rand() % 10001; // Generates a random number between 0 and 10000
    }
}

/**
 * Bubble Sort algorithm to sort an array.
 * 
 * @param arr   The array to sort.
 * @param size  The size of the array.
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

/**
 * Quick Sort algorithm to sort an array.
 * 
 * @param arr   The array to sort.
 * @param low   The starting index.
 * @param high  The ending index.
 */
void quick_sort(int arr[], int low, int high) {
    if (low < high) {
        int pi = partition(arr, low, high);
        quick_sort(arr, low, pi - 1);
        quick_sort(arr, pi + 1, high);
    }
}

/**
 * Partition function for Quick Sort.
 * 
 * @param arr   The array to partition.
 * @param low   The starting index.
 * @param high  The ending index.
 * @return      The partition index.
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

/**
 * Copies contents from source array to destination array.
 * 
 * @param src   The source array.
 * @param dest  The destination array.
 * @param size  The size of the array to copy.
 */
void copy_array(int src[], int dest[], int size) {
    for (int i = 0; i < size; i++) {
        dest[i] = src[i];
    }
}

/**
 * Prints the array with each number occupying at least 6 characters of space.
 * 
 * @param arr   The array to print.
 * @param size  The size of the array.
 */
void print_array(int arr[], int size) {
    for (int i = 0; i < size; i++) {
        printf("%6d ", arr[i]);
    }
    printf("\n");
}

/**
 * Sorts the array, measures the time taken, and returns the duration.
 * 
 * @param src       The source array to copy and sort.
 * @param dest      The destination array to store the sorted numbers.
 * @param size      The size of the array.
 * @param sort_type The sorting algorithm to use (0 for Quick Sort, 1 for Bubble Sort).
 * @return          The duration taken by the sorting algorithm.
 */
double sort_and_measure_time(int src[], int dest[], int size, int sort_type) {
    copy_array(src, dest, size);  // Copy to destination array for in-place sorting

    clock_t start = clock();  // Start time

    if (sort_type == 0) {
        quick_sort(dest, 0, size - 1);
    } else {
        bubble_sort(dest, size);
    }

    clock_t end = clock();  // End time

    return (double)(end - start) / CLOCKS_PER_SEC;  // Duration in seconds
}

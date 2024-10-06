
#include <stdio.h>

// Function to check if a date is earlier than the current earliest date
int is_earlier(int m1, int d1, int y1, int m2, int d2, int y2) {
    if (y1 < y2) return 1;
    if (y1 > y2) return 0;
    if (m1 < m2) return 1;
    if (m1 > m2) return 0;
    if (d1 < d2) return 1;
    return 0;
}

// Main Function
int main() {
    int month, day, year;
    int earliestMonth, earliestDay, earliestYear;

    // First prompt for input
    printf("Enter a date (mm/dd/yy): ");
    scanf("%d/%d/%d", &month, &day, &year);

    // Set the first valid input as the earliest date
    if (month != 0 && day != 0 && year != 0) {
        earliestMonth = month;
        earliestDay = day;
        earliestYear = year;
    }

    // Continue asking for dates until 0/0/0 is entered
    while (month != 0 && day != 0 && year != 0) {
        // Prompt for the next date
        printf("Enter a date (mm/dd/yy): ");
        scanf("%d/%d/%d", &month, &day, &year);

        // Check if the new date is earlier
        if (month != 0 && day != 0 && year != 0 && is_earlier(month, day, year, earliestMonth, earliestDay, earliestYear)) {
            earliestMonth = month;
            earliestDay = day;
            earliestYear = year;
        }
    }

    // Output the earliest date
    printf("%d/%d/%02d is the earliest date\n", earliestMonth, earliestDay, earliestYear);

    return 0;
}

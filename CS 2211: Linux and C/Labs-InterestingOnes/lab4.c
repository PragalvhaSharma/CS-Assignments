
#include <stdio.h>
#include <stdlib.h>

int minuteConverter(int hours, int minutes){
    return hours * 60 + minutes;
}

//number of flights is not passed because a pointer to the first index of an array is passed not the copy of the full array
int find_closest_flight(int departure_times[], int input_time_in_minutes, int numberOfFlights){
    int indexOfFlight = 0;
    int closestFlight = departure_times[indexOfFlight];
    int initialDifference = abs(departure_times[indexOfFlight] - input_time_in_minutes);

    for (int i = 1; i < numberOfFlights; i++){
        int currentDifference = abs(departure_times[i] - input_time_in_minutes);
        if (currentDifference < initialDifference){
            initialDifference = currentDifference;
            closestFlight = departure_times[i];
            indexOfFlight = i;
        }
    }

    return indexOfFlight;
}

int main() {
    // Departure times in minutes since midnight
    int departure_times[] = {
        8 * 60 + 0,    // 8:00 a.m.
        9 * 60 + 43,   // 9:43 a.m.
        11 * 60 + 19,  // 11:19 a.m.
        12 * 60 + 47,  // 12:47 p.m.
        14 * 60 + 0,   // 2:00 p.m.
        15 * 60 + 45,  // 3:45 p.m.
        19 * 60 + 0,   // 7:00 p.m.
        21 * 60 + 45   // 9:45 p.m.
    };

    // Arrival times in minutes since midnight
    int arrival_times[] = {
        10 * 60 + 16,  // 10:16 a.m.
        11 * 60 + 52,  // 11:52 a.m.
        13 * 60 + 31,  // 1:31 p.m.
        15 * 60 + 0,   // 3:00 p.m.
        16 * 60 + 8,   // 4:08 p.m.
        17 * 60 + 55,  // 5:55 p.m.
        21 * 60 + 20,  // 9:20 p.m.
        23 * 60 + 58   // 11:58 p.m.
    };

    // Strings for departure and arrival times
    char *departure_str[] = {
        "8:00 a.m.",
        "9:43 a.m.",
        "11:19 a.m.",
        "12:47 p.m.",
        "2:00 p.m.",
        "3:45 p.m.",
        "7:00 p.m.",
        "9:45 p.m."
    };

    char *arrival_str[] = {
        "10:16 a.m.",
        "11:52 a.m.",
        "1:31 p.m.",
        "3:00 p.m.",
        "4:08 p.m.",
        "5:55 p.m.",
        "9:20 p.m.",
        "11:58 p.m."
    };

    int numberOfFlights = sizeof(departure_times)/sizeof(departure_times[0]);
    int hours, minutes;
    int continue_program = 1;

    while (continue_program){
        // Get time input
        printf("Enter a 24-hour time: ");
        scanf("%d:%d", &hours, &minutes);

        int input_time_in_minutes = minuteConverter(hours, minutes);

        // Find the closest flight
        int closest_flight_index = find_closest_flight(departure_times, input_time_in_minutes, numberOfFlights);

        // Output the closest flight's departure and arrival times
        printf("Closest departure time is %s, arriving at %s\n", 
               departure_str[closest_flight_index], 
               arrival_str[closest_flight_index]);

        // Ask if the user wants to continue or quit
        printf("Enter 1 to continue or 0 to quit: ");
        scanf("%d", &continue_program);
    }

    return 0;
}
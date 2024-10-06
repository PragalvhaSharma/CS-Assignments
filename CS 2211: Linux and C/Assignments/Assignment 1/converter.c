#include <stdio.h>

// Function to convert Grams to Ounces and vice versa
void convertGrams_to_Ounces() {
    float gramToOunceConversion = 0.03527; // Conversion factor from grams to ounces
    float userInputValue; //Storing input values
    char conversionLetter; //Storing users choice of conversion

    // Prompting the user for input
    printf("\nConversion between Grams and Ounces\n");
    printf("Enter 'G' for Grams to Ounces or 'O' for Ounces to Grams: ");
    scanf(" %c", &conversionLetter);  // Handle leading spaces

    // Perform conversion based on user's choice
    if (conversionLetter == 'G' || conversionLetter == 'g') {
        // Grams to Ounces conversion
        printf("Please enter a value in Grams: ");
        scanf("%f", &userInputValue);
        printf("%.4f Grams is %.4f Ounces\n", userInputValue, userInputValue * gramToOunceConversion);
    } else if (conversionLetter == 'O' || conversionLetter == 'o') {
        // Ounces to Grams conversion
        printf("Please enter a value in Ounces: ");
        scanf("%f", &userInputValue);
        printf("%.4f Ounces is %.4f Grams\n", userInputValue, userInputValue / gramToOunceConversion);
    } else {
        // Invalid input handling
        printf("Invalid input. Please try again.\n");
    }

}

// Function to convert Square Meters to Square Yards and vice versa
void convertSquareMeters_to_Yards()  {
    float squareMTR_to_Yards = 1.19599; // Conversion factor from square meters to square yards
    float userInputValue; // Variable to store the user input value
    char conversionLetter;  // Variable to store the user's choice of conversion

    //Prompting for input
    printf("\nConversion between Square Meters and Square Yards\n");
    printf("Enter 'M' for Square Meters to Square Yards or 'Y' for Square Yards to Square Meters: ");
    scanf(" %c", &conversionLetter);

    //Perform conversion based on choice 
    if (conversionLetter == 'M' || conversionLetter == 'm') {
        // Square Meters to Square Yards conversion
        printf("Please enter a value in Square Meters: ");
        scanf("%f", &userInputValue);
        printf("%.4f Square Meters is %.4f Square Yards\n", userInputValue, userInputValue * squareMTR_to_Yards);
    } else if (conversionLetter == 'Y' || conversionLetter == 'y') {
        // Square Yards to Square Meters conversion
        printf("Please enter a value in Square Yards: ");
        scanf("%f", &userInputValue);
        printf("%.4f Square Yards is %.4f Square Meters\n", userInputValue, userInputValue / squareMTR_to_Yards);
    } else {
        // Invalid input handling
        printf("Invalid input. Please try again.\n");
    }
}

// Function to convert Litres to Pints and vice versa
void convertLitres_to_Pints(){
    float liters_to_pints = 2.11228;
    float userInputValue;
    char conversionLetter;

    // Prompting the user for input
    printf("\nConversion between Litres and Pints\n");
    printf("Enter 'L' for Litres to Pints or 'P' for Pints to Litres: ");
    scanf(" %c", &conversionLetter);

    // Perform conversion based on user's choice
    if (conversionLetter == 'L' || conversionLetter == 'l') {
        // Litres to Pints conversion
        printf("Please enter a value in Litres: ");
        scanf("%f", &userInputValue);
        printf("%.4f Litres is %.4f Pints\n", userInputValue, userInputValue * liters_to_pints);
    } else if (conversionLetter == 'P' || conversionLetter == 'p') {
        // Pints to Litres conversion
        printf("Please enter a value in Pints: ");
        scanf("%f", &userInputValue);
        printf("%.4f Pints is %.4f Litres\n", userInputValue, userInputValue / liters_to_pints);
    } else {
        // Invalid input handling
        printf("Invalid input. Please try again.\n");
    }
}

// Function to convert Meters to Feet and vice versa
void convertMeters_to_Feet() {
    char conversionLetter;
    float userInputValue;
    float meter_to_feet = 3.28084;

    // Prompting the user for input
    printf("\nConversion between Meters and Feet\n");
    printf("Enter 'M' for Meters to Feet or 'F' for Feet to Meters: ");
    scanf(" %c", &conversionLetter);

    // Perform conversion based on user's choice
    if (conversionLetter == 'M' || conversionLetter == 'm') {
        // Meters to Feet conversion
        printf("Please enter a value in Meters: ");
        scanf("%f", &userInputValue);
        printf("%.4f Meters is %.4f Feet\n", userInputValue, userInputValue * meter_to_feet);
    } else if (conversionLetter == 'F' || conversionLetter == 'f') {
        // Feet to Meters conversion
        printf("Please enter a value in Feet: ");
        scanf("%f", &userInputValue);
        printf("%.4f Feet is %.4f Meters\n", userInputValue, userInputValue / meter_to_feet);
    } else {
        //Error handling
        printf("Invalid input. Please try again.\n");
    }
}

//Main Function
int main() {
    int userInput;

    // Main menu loop
    do {
        // Display the menu options
        printf("\n--- Main Menu ---\n");
        printf("1. Grams to Ounces and vice versa\n");
        printf("2. Square meters to Square Yards and vice versa\n");
        printf("3. Litres to Pints and vice versa\n");
        printf("4. Meters to Feet and vice versa\n");
        printf("5. Quit\n");

        // Prompt for user input
        printf("Please enter an integer from 1 to 5: ");
        scanf("%d", &userInput);

        // Handling the user's choice and calling on relevant functions
        if (userInput == 1) {
            convertGrams_to_Ounces();
        } 
        else if (userInput == 2) {
            convertSquareMeters_to_Yards();
        } 
        else if (userInput == 3) {
            convertLitres_to_Pints();
        } 
        else if (userInput == 4) {
            convertMeters_to_Feet();
        } 
        else if (userInput == 5) {
            printf("Exiting the program. Goodbye!\n");
        } 
        else {
            printf("Invalid choice. Please try again.\n");
        }

    } while (userInput != 5);

    return 0;
}
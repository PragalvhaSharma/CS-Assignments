import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// The Interface class provides a command-line interface for interacting with a
// binary search tree-based dictionary (BSTDictionary). It allows users to define words,
// translate them, play associated media, and manage dictionary entries.
public class Interface {

    public static void main(String[] args) {
        // Check if the input file name is provided
        if (args.length != 1) {
            System.out.println("Usage: java Interface inputFile");
            return;
        }

        String inputFileName = args[0];
        BSTDictionary dictionary = new BSTDictionary();

        // Read the input file and populate the dictionary
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFileName))) {
            String labelLine;
            while ((labelLine = reader.readLine()) != null) {
                String dataLine = reader.readLine();
                if (dataLine == null) {
                    // Handle case where data line is missing for a label
                    System.out.println("Incomplete record for label: " + labelLine);
                    break;
                }

                // Process the label and data lines to create a Record
                String label = labelLine.trim().toLowerCase(); // Convert label to lowercase for consistency
                String dataLineTrimmed = dataLine.trim();
                int type;
                String data;

                // Determine the type and extract data based on specific prefixes or file extensions
                if (dataLineTrimmed.startsWith("-")) {
                    type = 3; // Sound file
                    data = dataLineTrimmed.substring(1).trim();
                } else if (dataLineTrimmed.startsWith("+")) {
                    type = 4; // Music file
                    data = dataLineTrimmed.substring(1).trim();
                } else if (dataLineTrimmed.startsWith("*")) {
                    type = 5; // Voice file
                    data = dataLineTrimmed.substring(1).trim();
                } else if (dataLineTrimmed.startsWith("/")) {
                    type = 2; // Translation to French
                    data = dataLineTrimmed.substring(1).trim();
                } else {
                    // Determine type based on file extension or default to definition
                    if (dataLineTrimmed.endsWith(".gif")) {
                        type = 7; // Animated image file
                        data = dataLineTrimmed;
                    } else if (dataLineTrimmed.endsWith(".jpg")) {
                        type = 6; // Image file
                        data = dataLineTrimmed;
                    } else if (dataLineTrimmed.endsWith(".html")) {
                        type = 8; // Webpage URL
                        data = dataLineTrimmed;
                    } else if (dataLineTrimmed.contains(".")) {
                        // Unrecognized file extension; default to definition
                        type = 1;
                        data = dataLineTrimmed;
                    } else {
                        type = 1; // Definition
                        data = dataLineTrimmed;
                    }
                }

                // Create Key and Record objects for the dictionary
                Key key = new Key(label, type);
                Record record = new Record(key, data);

                // Insert the record into the dictionary, handling duplicates
                try {
                    dictionary.put(record);
                } catch (DictionaryException e) {
                    // Notify the user if a duplicate key is found
                    System.out.println("Duplicate key found: (" + label + ", " + type + ")");
                }
            }
        } catch (FileNotFoundException e) {
            // Handle case where the input file does not exist
            System.out.println("Input file not found: " + inputFileName);
            return;
        } catch (IOException e) {
            // Handle other I/O errors
            System.out.println("Error reading input file: " + e.getMessage());
            return;
        }

        // Initialize the user interface using Scanner
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter next command: ");
            String line = scanner.nextLine().trim();
            if (line.equalsIgnoreCase("exit")) {
                // Exit the program if the user types 'exit'
                break;
            } else if (line.isEmpty()) {
                // Ignore empty input
                continue;
            } else {
                // Process the entered command
                processCommand(line, dictionary);
            }
        }

        scanner.close();
    }

    // Processes a single user command by parsing the input and executing the corresponding action
    private static void processCommand(String commandLine, BSTDictionary dictionary) {
        // Split the command into tokens with a maximum of 3 parts
        String[] tokens = commandLine.split("\\s+", 3);
        if (tokens.length == 0) {
            System.out.println("No command entered.");
            return;
        }

        String command = tokens[0].toLowerCase();
        try {
            switch (command) {
                case "define":
                    handleDefine(tokens, dictionary);
                    break;
                case "translate":
                    handleTranslate(tokens, dictionary);
                    break;
                case "sound":
                    handleSound(tokens, dictionary, 3, "sound");
                    break;
                case "play":
                    handleSound(tokens, dictionary, 4, "music");
                    break;
                case "say":
                    handleSound(tokens, dictionary, 5, "voice");
                    break;
                case "show":
                    handleDisplayImage(tokens, dictionary, 6, "image");
                    break;
                case "animate":
                    handleDisplayImage(tokens, dictionary, 7, "animated image");
                    break;
                case "browse":
                    handleBrowse(tokens, dictionary);
                    break;
                case "delete":
                    handleDelete(tokens, dictionary);
                    break;
                case "add":
                    handleAdd(tokens, dictionary);
                    break;
                case "list":
                    handleList(tokens, dictionary);
                    break;
                case "first":
                    handleFirst(dictionary);
                    break;
                case "last":
                    handleLast(dictionary);
                    break;
                default:
                    // Handle invalid commands
                    System.out.println("Invalid command.");
            }
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            // Handle errors related to command format
            System.out.println("Invalid command format.");
        } catch (MultimediaException e) {
            // Handle multimedia-related errors
            System.out.println("Multimedia error: " + e.getMessage());
        } catch (Exception e) {
            // Handle any other unexpected errors
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    // Handles the 'define' command to print the definition of a word
    private static void handleDefine(String[] tokens, BSTDictionary dictionary) {
        if (tokens.length < 2) {
            System.out.println("Usage: define <word>");
            return;
        }
        String label = tokens[1].toLowerCase();
        Key keyDefine = new Key(label, 1);
        Record recordDefine = dictionary.get(keyDefine);
        if (recordDefine != null) {
            // Print the definition if it exists
            System.out.println(recordDefine.getDataItem());
        } else {
            // Inform the user if the word is not found
            System.out.println("the word " + tokens[1] + " is not in the dictionary."); // Adjusted to match expected output
        }
    }

    // Handles the 'translate' command to print the French translation of a word
    private static void handleTranslate(String[] tokens, BSTDictionary dictionary) {
        if (tokens.length < 2) {
            System.out.println("Usage: translate <word>");
            return;
        }
        String label = tokens[1].toLowerCase();
        Key keyTranslate = new Key(label, 2);
        Record recordTranslate = dictionary.get(keyTranslate);
        if (recordTranslate != null) {
            // Print the French translation if it exists
            System.out.println(recordTranslate.getDataItem());
        } else {
            // Inform the user if the translation is not found
            System.out.println("there is no definition for the word " + tokens[1]); // Adjusted to match expected output
        }
    }

    // Handles the 'sound', 'play', and 'say' commands to play audio files
    private static void handleSound(String[] tokens, BSTDictionary dictionary, int type, String mediaType) throws MultimediaException {
        if (tokens.length < 2) {
            System.out.println("Usage: " + mediaType + " <word>");
            return;
        }
        String label = tokens[1].toLowerCase();
        Record record = dictionary.get(new Key(label, type));
        if (record != null) {
            new SoundPlayer().play(record.getDataItem());
        } else {
            System.out.println("there is no " + mediaType + " file for " + tokens[1]);
        }
    }

    // Handles the 'show' and 'animate' commands to display image files
    private static void handleDisplayImage(String[] tokens, BSTDictionary dictionary, int type, String mediaType) throws MultimediaException {
        if (tokens.length < 2) {
            System.out.println("Usage: " + mediaType + " <word>");
            return;
        }
        String label = tokens[1].toLowerCase();
        Record record = dictionary.get(new Key(label, type));
        if (record != null) {
            new PictureViewer().show(record.getDataItem());
        } else {
            System.out.println("there is no " + mediaType + " file for " + tokens[1]);
        }
    }

    // Handles the 'browse' command to display a webpage
    private static void handleBrowse(String[] tokens, BSTDictionary dictionary) throws MultimediaException {
        if (tokens.length < 2) {
            System.out.println("Usage: browse <word>");
            return;
        }
        String label = tokens[1].toLowerCase();
        Record record = dictionary.get(new Key(label, 8));
        if (record != null) {
            new ShowHTML().show(record.getDataItem());
        } else {
            System.out.println("there is no webpage called " + tokens[1]);
        }
    }

    // Handles the 'delete' command to remove a record from the dictionary
    private static void handleDelete(String[] tokens, BSTDictionary dictionary) {
        if (tokens.length < 3) {
            System.out.println("Usage: delete <word> <type>");
            return;
        }
        String label = tokens[1].toLowerCase();
        int type;
        try {
            type = Integer.parseInt(tokens[2]);
            if (type < 1 || type > 8) {
                // Validate that the type is within the acceptable range
                System.out.println("type must be an integer between 1 and 8."); // Adjusted to match expected output
                return;
            }
        } catch (NumberFormatException e) {
            // Handle cases where the type is not a valid integer
            System.out.println("type must be an integer."); // Adjusted to match expected output
            return;
        }
        Key keyDelete = new Key(label, type);
        try {
            // Attempt to remove the record from the dictionary
            dictionary.remove(keyDelete);
        } catch (DictionaryException e) {
            // Inform the user if the record does not exist
            System.out.println("no record in the ordered dictionary has key (" + tokens[1] + "," + type + ")"); // Adjusted to match expected output
        }
    }

    // Handles the 'add' command to insert a new record into the dictionary
    private static void handleAdd(String[] tokens, BSTDictionary dictionary) {
        if (tokens.length < 3) {
            System.out.println("Usage: add <word> <type> <data>");
            return;
        }
        // Split the remaining tokens to separate type and data
        String[] addTokens = tokens[2].split("\\s+", 2);
        if (addTokens.length < 2) {
            System.out.println("Usage: add <word> <type> <data>");
            return;
        }
        String typeStr = addTokens[0];
        String data = addTokens[1].trim();
        int type;
        try {
            type = Integer.parseInt(typeStr);
            if (type < 1 || type > 8) {
                // Validate that the type is within the acceptable range
                System.out.println("type must be an integer between 1 and 8."); // Adjusted to match expected output
                return;
            }
        } catch (NumberFormatException e) {
            // Handle cases where the type is not a valid integer
            System.out.println("type must be an integer."); // Adjusted to match expected output
            return;
        }
        // Remove surrounding quotes from data if present
        if (data.startsWith("\"") && data.endsWith("\"") && data.length() >= 2) {
            data = data.substring(1, data.length() - 1);
        }
        String label = tokens[1].toLowerCase();
        Key keyAdd = new Key(label, type);
        Record recordAdd = new Record(keyAdd, data);
        try {
            // Attempt to add the new record to the dictionary
            dictionary.put(recordAdd);
        } catch (DictionaryException e) {
            // Inform the user if a duplicate key is found
            System.out.println("a record with the given key (" + tokens[1] + ", " + type + ") is already in the ordered dictionary."); // Adjusted to match expected output
        }
    }

    // Handles the 'list' command to display all labels starting with a given prefix
    private static void handleList(String[] tokens, BSTDictionary dictionary) {
        if (tokens.length < 2) {
            System.out.println("Usage: list <prefix>");
            return;
        }
        String prefixOriginal = tokens[1];
        String prefix = prefixOriginal.toLowerCase();
        listLabels(dictionary, prefix, prefixOriginal);
    }

    // Lists all label attributes in the dictionary that start with the given prefix
    private static void listLabels(BSTDictionary dictionary, String prefix, String prefixOriginal) {
        List<String> matchingLabels = new ArrayList<>();
        Record current = dictionary.smallest();
        while (current != null) {
            String label = current.getKey().getLabel().toLowerCase();
            if (label.startsWith(prefix)) {
                // Add labels that match the prefix to the list
                matchingLabels.add(current.getKey().getLabel());
            }
            Record next = dictionary.successor(current.getKey());
            if (next == null) {
                // Stop if there are no more records
                break;
            }
            current = next;
        }
        if (!matchingLabels.isEmpty()) {
            // Print all matching labels separated by commas
            System.out.println(String.join(", ", matchingLabels));
        } else {
            // Inform the user if no labels match the prefix
            System.out.println("no label attributes in the ordered dictionary start with prefix " + prefixOriginal); // Adjusted to match expected output
        }
    }

    // Handles the 'first' command to display the record with the smallest key
    private static void handleFirst(BSTDictionary dictionary) {
        Record smallest = dictionary.smallest();
        if (smallest != null) {
            // Print the smallest record if it exists
            printRecord(smallest);
        } else {
            // Inform the user if the dictionary is empty
            System.out.println("the ordered dictionary is empty."); // Adjusted to match expected output
        }
    }

    // Handles the 'last' command to display the record with the largest key
    private static void handleLast(BSTDictionary dictionary) {
        Record largest = dictionary.largest();
        if (largest != null) {
            // Print the largest record if it exists
            printRecord(largest);
        } else {
            // Inform the user if the dictionary is empty
            System.out.println("the ordered dictionary is empty."); // Adjusted to match expected output
        }
    }

    // Prints all attributes of a given record in the format: label,type,data
    private static void printRecord(Record record) {
        Key key = record.getKey();
        System.out.println(key.getLabel() + "," + key.getType() + "," + record.getDataItem());
    }
}

import java.util.LinkedList;

public class HashDictionary implements DictionaryADT {

    private LinkedList<Data>[] table; // Array of LinkedLists for separate chaining
    private int tableSize; // Size of the hash table
    private int numRecords; // Number of records currently stored in the dictionary
    private static final int HASH_MULTIPLIER_1 = 31; // First prime multiplier for hash function
    private static final int HASH_MULTIPLIER_2 = 53; // Second prime multiplier for hash function
    private static final int OFFSET = 7; // Small prime offset to reduce clustering

    // Constructor to initialize the hash table with the specified size
    public HashDictionary(int size) {
        this.tableSize = size;
        this.table = new LinkedList[tableSize];
        // Initialize each LinkedList in the array
        for (int i = 0; i < tableSize; i++) {
            table[i] = new LinkedList<Data>();
        }
        this.numRecords = 0;
    }

    // Optimized polynomial hash function with alternating multipliers and an offset
    private int hashFunction(String key) {
        int hashValue = 0;
        // Loop through each character in the key
        for (int i = 0; i < key.length(); i++) {
            // Alternate multipliers to reduce collision
            int multiplier = (i % 2 == 0) ? HASH_MULTIPLIER_1 : HASH_MULTIPLIER_2;
            // Compute hash value using the multiplier and character code
            hashValue = (multiplier * hashValue + key.charAt(i)) % tableSize;
        }
        // Add offset to further reduce clustering
        hashValue = (hashValue + OFFSET) % tableSize;
        // Ensure the hash value is non-negative
        return Math.abs(hashValue);
    }

    // Inserts a new Data record into the dictionary
    public int put(Data record) {
        int hashIndex = hashFunction(record.getConfiguration());
        LinkedList<Data> chain = table[hashIndex];

        // Check for existing configuration to prevent duplicates
        for (Data data : chain) {
            if (data.getConfiguration().equals(record.getConfiguration())) {
                // Throw exception if duplicate configuration is found
                throw new DictionaryException(); // Default message "Dictionary Error"
            }
        }

        // Determine if a collision occurred (chain is not empty)
        boolean collision = !chain.isEmpty();
        // Add the new record to the beginning of the chain
        chain.addFirst(record);

        // Increment the number of records
        numRecords++;
        // Return 1 if a collision occurred, 0 otherwise
        return collision ? 1 : 0;
    }

    // Removes a Data record with the given configuration from the dictionary
    public void remove(String config) {
        int hashIndex = hashFunction(config);
        LinkedList<Data> chain = table[hashIndex];

        // Iterate through the chain to find the matching configuration
        for (Data data : chain) {
            if (data.getConfiguration().equals(config)) {
                // Remove the data from the chain
                chain.remove(data);
                // Decrement the number of records
                numRecords--;
                return;
            }
        }

        // Throw exception if configuration is not found
        throw new DictionaryException(); // Default message "Dictionary Error"
    }

    // Retrieves the score associated with the given configuration
    public int get(String config) {
        int hashIndex = hashFunction(config);
        LinkedList<Data> chain = table[hashIndex];

        // Search through the chain for the matching configuration
        for (Data data : chain) {
            if (data.getConfiguration().equals(config)) {
                // Return the associated score
                return data.getScore();
            }
        }
        // Return -1 if configuration is not found
        return -1;
    }

    // Returns the number of records in the dictionary
    public int numRecords() {
        return numRecords;
    }
}

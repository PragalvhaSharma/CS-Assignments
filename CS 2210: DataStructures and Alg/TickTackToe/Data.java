// The Data class holds a configuration and its corresponding score.
public class Data {

    // Stores the score associated with the configuration.
    private int Score;
    // Stores the configuration details as a string.
    private String configuration;

    // Constructor that initializes the configuration and score for this Data object.
    public Data(String config, int score) {
        // Sets the score and configuration fields with the provided values.
        Score = score;
        configuration = config;
    }

    // Returns the configuration associated with this Data object.
    public String getConfiguration() {
        return configuration;
    }

    // Returns the score associated with this Data object.
    public int getScore() {
        return Score;
    }
}

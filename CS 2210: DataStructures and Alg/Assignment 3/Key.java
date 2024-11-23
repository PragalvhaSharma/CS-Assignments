public class Key {
    private String label;
    private int type;

    // Constructor sets label (in lowercase) and type
    public Key(String theLabel, int theType) {
        this.label = theLabel.toLowerCase();
        this.type = theType;
    }

    // Returns the label
    public String getLabel() {
        return label;
    }

    // Returns the type
    public int getType() {
        return type;
    }

    // Compares this Key to another Key
    public int compareTo(Key k) {
        if (this.label.equals(k.getLabel()) && this.type == k.getType()) {
            return 0; // Keys are equal
        } else if (this.label.compareTo(k.getLabel()) < 0 || 
                   (this.label.equals(k.getLabel()) && this.type < k.getType())) {
            return -1; // This key is less
        } else {
            return 1; // This key is greater
        }
    }
}

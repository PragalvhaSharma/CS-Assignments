public class Record {
    private Key theKey;
    private String data;

    // Constructor sets the key and data
    public Record(Key k, String theData) {
        this.theKey = k;
        this.data = theData;
    }

    // Returns the key
    public Key getKey() {
        return theKey;
    }

    // Returns the data item
    public String getDataItem() {
        return data;
    }
}

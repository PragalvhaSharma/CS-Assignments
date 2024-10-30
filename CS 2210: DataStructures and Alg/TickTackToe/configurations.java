public class Configurations {

    private char[][] board;      // Game board represented as a 2D array
    private int boardSize;       // Size of the board (n x n)
    private int lengthToWin;     // Number of consecutive symbols needed to win
    private int maxLevels;       // Maximum depth for game tree exploration

    // Constructor to initialize the board and other parameters
    public Configurations(int boardSize, int lengthToWin, int maxLevels) {
        this.boardSize = boardSize;
        this.lengthToWin = lengthToWin;
        this.maxLevels = maxLevels;
        this.board = new char[boardSize][boardSize];
        
        // Initialize the board with empty spaces
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                board[i][j] = ' ';
            }
        }
    }

    // Creates and returns a new HashDictionary with a prime number size to minimize collisions
    public HashDictionary createDictionary() {
        return new HashDictionary(7000); // Prime number size between 6000-10000
    }

    // Checks if the current board configuration exists in the hash table
    // Returns the associated score if found, otherwise returns -1
    public int repeatedConfiguration(HashDictionary hashTable) {
        String config = getBoardConfiguration();
        return hashTable.get(config);
    }

    // Adds the current board configuration and its score to the hash table
    public void addConfiguration(HashDictionary hashTable, int score) {
        String config = getBoardConfiguration();
        Data data = new Data(config, score);
        hashTable.put(data);
    }

    // Stores a player's symbol ('X' or 'O') in the specified position on the board
    public void savePlay(int row, int col, char symbol) {
        board[row][col] = symbol;
    }

    // Checks if a specific square on the board is empty
    public boolean squareIsEmpty(int row, int col) {
        return board[row][col] == ' ';
    }

    // Determines if the specified symbol has a winning sequence on the board
    public boolean wins(char symbol) {
        // Check all rows and columns for a winning sequence
        for (int i = 0; i < boardSize; i++) {
            if (checkLine(symbol, i, 0, 0, 1) || // Check row i
                checkLine(symbol, 0, i, 1, 0))   // Check column i
                return true;
        }
        // Check both diagonals for a winning sequence
        return checkLine(symbol, 0, 0, 1, 1) || // Main diagonal
               checkLine(symbol, 0, boardSize - 1, 1, -1); // Anti-diagonal
    }

    // Checks if the game is a draw 
    public boolean isDraw() {
        boolean isFull = true; // Assume the board is full initially

        // Iterate through the board to find any empty spaces
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board[i][j] == ' ') { // Found an empty space
                    isFull = false;
                    break; // No need to check further
                }
            }
            if (!isFull) {
                break; // Exit outer loop as well
            }
        }

        return isFull;
    }

    // Evaluates the current state of the board
    // Returns:
    // 3 if the computer ('O') has won
    // 0 if the human player ('X') has won
    // 2 if the game is a draw
    // 1 if the game is still ongoing
    public int evalBoard() {
        if (isDraw()) { // First check if the game is a draw
            return 2; // Draw
        }

        if (wins('O')) { // Check if the computer has won
            return 3; // Computer wins
        }

        if (wins('X')) { // Check if the human player has won
            return 0; // Human wins
        }

        return 1; // Game is still ongoing
    }

    // Helper method to check for a winning line of 'symbol'
    // Starts from (startRow, startCol) and moves in the direction specified by rowDelta and colDelta
    private boolean checkLine(char symbol, int startRow, int startCol, int rowDelta, int colDelta) {
        int consecutiveCount = 0; // Counter for consecutive matching symbols
        
        for (int i = 0; i < lengthToWin; i++) {
            int row = startRow + i * rowDelta; // Calculate current row position
            int col = startCol + i * colDelta; // Calculate current column position

            // Check if the current position is within bounds and matches the symbol
            if (row < 0 || row >= boardSize || col < 0 || col >= boardSize || board[row][col] != symbol) {
                return false; // Sequence broken or out of bounds
            }

            consecutiveCount++; // Increment count for matching symbol

            // If the required sequence length is reached, return true
            if (consecutiveCount == lengthToWin) {
                return true;
            }
        }

        return false; // No winning sequence found in this direction
    }

    // Utility method to get the current board configuration as a single string
    private String getBoardConfiguration() {
        StringBuilder config = new StringBuilder();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                config.append(board[i][j]);
            }
        }
        return config.toString();
    }

}

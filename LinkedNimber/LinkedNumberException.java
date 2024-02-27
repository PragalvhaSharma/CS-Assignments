//Custom exception for an invalid condition or operation in LinkedNumber objects.
// Inherits from RuntimeException class
public class LinkedNumberException extends RuntimeException {

    //Constructs an exception with a message.
    public LinkedNumberException(String msg) {
        super(msg); // Initialize RuntimeException with the given message.
    }
}

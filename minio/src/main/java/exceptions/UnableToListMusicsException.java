package exceptions;

public class UnableToListMusicsException extends RuntimeException{
    public UnableToListMusicsException(String message) {
        super(message);
    }
}
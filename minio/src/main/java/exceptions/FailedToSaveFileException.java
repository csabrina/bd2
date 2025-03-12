package exceptions;

public class FailedToSaveFileException extends RuntimeException{
    public FailedToSaveFileException(String message) {
        super(message);
    }
}
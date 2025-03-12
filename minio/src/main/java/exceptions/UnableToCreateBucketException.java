package exceptions;

public class UnableToCreateBucketException extends RuntimeException{
    public UnableToCreateBucketException(String message) {
        super(message);
    }
}
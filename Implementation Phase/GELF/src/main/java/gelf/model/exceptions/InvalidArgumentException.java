package gelf.model.exceptions;

public class InvalidArgumentException extends Exception { 
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public InvalidArgumentException(String errorMessage) {
        super(errorMessage);
    }
}
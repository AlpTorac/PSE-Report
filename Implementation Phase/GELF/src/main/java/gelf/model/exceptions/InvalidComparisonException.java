package gelf.model.exceptions;

public class InvalidComparisonException extends Exception { 
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public InvalidComparisonException(String errorMessage) {
        super(errorMessage);
    }
}
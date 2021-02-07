package gelf.model.exceptions;

public class TooManySelectedException extends Exception { 
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public TooManySelectedException(String errorMessage) {
        super(errorMessage);
    }
}
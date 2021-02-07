package gelf.model.exceptions;

public class ExceedingFileSizeException extends Exception { 
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public ExceedingFileSizeException(String errorMessage) {
        super(errorMessage);
    }
}
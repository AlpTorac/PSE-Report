package gelf.model.exceptions;

public class TooManyPanelsOpenedException extends Exception { 
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public TooManyPanelsOpenedException(String errorMessage) {
        super(errorMessage);
    }
}
package gelf.model.commands;

import gelf.model.exceptions.InvalidFileFormatException;

/**
 * An interface that is implemented by all commands.
 * @author Xhulio Pernoca
 */
public interface Command {

    /**
     * Executes the command, making the necessary changes in the project
     * @throws InvalidFileFormatException if a String from the constructor has invalid file format
     * while being executed by the command
     */
    public void execute() throws InvalidFileFormatException;
    
    /**
     * Undoes the execution of the command, making the necessary changes in the project
     */
    public void undo();
}

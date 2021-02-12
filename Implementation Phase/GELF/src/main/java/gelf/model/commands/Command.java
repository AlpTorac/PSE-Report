package gelf.model.commands;

import gelf.model.exceptions.InvalidFileFormatException;

/**
 * An interface that is implemented by all commands.
 */
public interface Command {

    public void execute() throws InvalidFileFormatException;
    
    public void undo() throws InvalidFileFormatException;
}

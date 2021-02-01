package gelf.model.commands;

/**
 * An interface that is implemented by all commands.
 */
public interface Command {

    public void execute();
    
    public void undo();
}

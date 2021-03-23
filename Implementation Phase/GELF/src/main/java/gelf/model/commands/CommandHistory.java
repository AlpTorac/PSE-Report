package gelf.model.commands;

import java.util.Stack;

import gelf.model.exceptions.InvalidFileFormatException;
import gelf.model.project.Model;

/**
 * Keeps track of the commands and provides undoing and redoing functionality
 * @author Xhulio Pernoca
 */
public class CommandHistory {
    private Stack<Command> commands;
    private Stack<Command> undoneCommands;
    private int undoCount;
    private final int DEFAULTHISTORYSIZE = 5;

    /**
     * Instantiates the Command History
     */
    public CommandHistory() {
    	commands = new Stack<Command>();
    	undoneCommands = new Stack<Command>();
    	undoCount = DEFAULTHISTORYSIZE;
    }

    /**
     * Adds a command to the command history
     * @param command the command to be added
     */
    public void addCommand(Command command) {
        commands.add(command);
        if (commands.size() > 5) {
        	commands.remove(0);
        }
        if (undoneCommands.isEmpty()) {
            return;
        } else if (undoneCommands.peek() == command) {
            undoneCommands.pop();
        } else {
            resetUndoneCommands();
        }
    }
    
    /**
     * sets the number of possible undo operations. Not implemented yet
     * @param count the number of possible undo operations
     */
    /*public void setUndoCount(int count) {
    	undoCount = count;
    	while (commands.size() > undoCount) {
        	commands.remove(0);
        }
    	while (undoneCommands.size() > undoCount) {
    		undoneCommands.remove(0);
    	}
    }*/
    
    /**
     * Removes all the commands in the history
     */
    public void resetCommands() {
    	commands.removeAllElements();
    }
    
    /**
     * Removes all the undone commands in the history
     */
    private void resetUndoneCommands() {
    	undoneCommands.removeAllElements();
    }
    
    /**
     * Removes the latest command
     */
    public void removeLatestCommand() {
    	if (!commands.isEmpty()) {
        	commands.pop();
    	}
    }
    
    /**
     * Undoes the latest added command
     */
    public void undoCommand() {
    	if (!commands.isEmpty()) {
    		Command latestCommand = getLatestCommand();
    		latestCommand.undo();
    		undoneCommands.add(commands.pop());
    		if (undoneCommands.size() > undoCount) {
    			undoneCommands.remove(0);
    		}
    	}
        Model.getInstance().getCurrentProject().inform();
    }
    
    /**
     * Redoes the latest undone command
     * @throws InvalidFileFormatException if the parser delivers this error from one of the commands
     * Unlikely to happen, but java needs it covered
     */
    public void redoCommand() throws InvalidFileFormatException {
    	if (!undoneCommands.isEmpty()) {
    		Command latestUndoneCommand = undoneCommands.lastElement();
    		latestUndoneCommand.execute();
    	}
    }

    /**
     * Returns the latest command
     * @return the latest command
     */
    public Command getLatestCommand() {
        if (commands.isEmpty()) {
            return null;
        }
        return commands.lastElement();
    }
    
    /**
     * Returns how many commands are in the command history
     * @return how many commands are in the command history
     */
    public int getCommandsSize() {
    	return commands.size();
    }
    
    /**
     * Returns how many undone commands are in the command history
     * @return how many undone commands are in the command history
     */
    public int getUndoneCommandsSize() {
    	return undoneCommands.size();
    }
}

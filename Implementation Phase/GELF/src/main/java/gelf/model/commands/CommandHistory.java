package gelf.model.commands;

import java.util.Stack;

public class CommandHistory {
    private Stack<Command> commands;
    private Stack<Command> undoneCommands;
    private int undoCount;
    private final int DEFAULTHISTORYSIZE = 5;

    public CommandHistory() {
    	commands = new Stack<Command>();
    	undoneCommands = new Stack<Command>();
    	undoCount = DEFAULTHISTORYSIZE;
    }

    public void addCommand(Command command) {
        commands.add(command);
        if (commands.size() > 5) {
        	commands.remove(0);
        }
        resetUndoneCommands();
    }
    
    public void setUndoCount(int count) {
    	undoCount = count;
    	while (commands.size() > undoCount) {
        	commands.remove(0);
        }
    	while (undoneCommands.size() > undoCount) {
    		undoneCommands.remove(0);
    	}
    }
    
    private void resetCommands() {
    	commands.removeAllElements();
    }
    
    private void resetUndoneCommands() {
    	undoneCommands.removeAllElements();
    }
    
    public void removeLatestCommand() {
    	if (!commands.isEmpty()) {
        	commands.pop();
    	}
    	
    }
    
    public void undoCommand() {
    	if (!commands.isEmpty()) {
    		Command latestCommand = getLatestCommand();
    		latestCommand.undo();
    		undoneCommands.add(commands.pop());
    		if (undoneCommands.size() > undoCount) {
    			undoneCommands.remove(0);
    		}
    	}
    }
    
    public void redoCommand() {
    	if (!undoneCommands.isEmpty()) {
    		Command latestUndoneCommand = undoneCommands.lastElement();
    		latestUndoneCommand.execute();
    		undoneCommands.pop();
    	}
    }

    public Command getLatestCommand() {
        return commands.lastElement();
    }
    
    public int getCommandsSize() {
    	return commands.size();
    }
    
    public int getUndoneCommandsSize() {
    	return undoneCommands.size();
    }
}

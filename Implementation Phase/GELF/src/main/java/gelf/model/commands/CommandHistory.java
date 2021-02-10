package gelf.model.commands;

public class CommandHistory {
    private Command latestCommand;

    public CommandHistory() {
    }

    /**
     * Will be expanded if undo is implemented. 
     * Currently only sets latest Command.
     * @param command The latest command
     */
    public void addCommand(Command command) {
        latestCommand = command;
    }

    public Command getLatestCommand() {
        return latestCommand;
    }
}

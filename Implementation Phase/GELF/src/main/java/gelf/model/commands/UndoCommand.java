package gelf.model.commands;

import gelf.model.exceptions.InvalidFileFormatException;
import gelf.model.project.Model;

public class UndoCommand implements Command {
	private CommandHistory commandHistory = Model.getInstance().getCurrentCommandHistory();

	public UndoCommand() {
	}

	public void execute() throws InvalidFileFormatException {
		commandHistory.undoCommand();
	}

	public void undo() throws InvalidFileFormatException {
		commandHistory.redoCommand();
	}
}

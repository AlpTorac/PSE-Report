package gelf.model.commands;

import gelf.model.project.Model;

public class UndoCommand implements Command{
	private CommandHistory commandHistory = Model.getInstance().getCurrentCommandHistory();
	
	public UndoCommand() {
	}

	public void execute() {
		commandHistory.undoCommand();
	}

	public void undo() {
		commandHistory.redoCommand();
	}
}

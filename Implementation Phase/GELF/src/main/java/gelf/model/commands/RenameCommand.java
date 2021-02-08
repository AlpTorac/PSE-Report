package gelf.model.commands;

import gelf.model.elements.Element;

public class RenameCommand implements Command {
	
	private String oldName;
	private String newName;
	private Element element;
	
	public RenameCommand(Element element, String newName) {
		this.oldName = element.getName();
		this.newName = newName;
	}

	public void execute() {
		element.setName(newName);
		Model currentModel = Model.getInstance();
		currentModel.getProject().notify();
		currentModel.getCommandHistory().addCommand(this);
	}

	public void undo() {
		element.setName(oldName);
		Model currentModel = Model.getInstance();
		currentModel.getProject().notify();
		currentModel.getCommandHistory().removeLatestCommand();
	}

}

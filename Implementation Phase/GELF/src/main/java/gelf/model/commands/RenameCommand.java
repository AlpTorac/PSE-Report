package gelf.model.commands;

import gelf.model.elements.Element;
import gelf.model.project.Model;

public class RenameCommand implements Command {
	
	private String oldName;
	private String newName;
	private Element element;
	private Model currentModel = Model.getInstance();
	
	public RenameCommand(Element element, String newName) {
		this.element = element;
		this.oldName = element.getName();
		this.newName = newName;
	}

	public void execute() {
		element.setName(newName);
		currentModel.getCurrentCommandHistory().addCommand(this);
		currentModel.getCurrentProject().inform();
	}

	public void undo() {
		element.setName(oldName);
		currentModel.getCurrentProject().inform();
	}

}

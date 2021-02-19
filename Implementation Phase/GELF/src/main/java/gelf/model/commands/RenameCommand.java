package gelf.model.commands;

import gelf.model.elements.Element;
import gelf.model.project.Model;
/**
 * Renames an element.
 * @author Kerem Kara
 */
public class RenameCommand implements Command {
	
	private String oldName;
	private String newName;
	private Element element;
	private Model currentModel = Model.getInstance();
	
	/**
	 * Initializes the command
	 * @param element Element, that is going to renamed.
	 * @param newName New name of the element that is going to be renamed.
	 */
	public RenameCommand(Element element, String newName) {
		this.element = element;
		this.oldName = element.getName();
		this.newName = newName;
	}
	/**
	 * Executes the command by changing the name of the element.
	 */
	public void execute() {
		element.setName(newName);
		currentModel.getCurrentCommandHistory().addCommand(this);
		currentModel.getCurrentProject().inform();
	}
	/**
	 * Undoes the command by reverting the element's name to the old name.
	 */
	public void undo() {
		element.setName(oldName);
		currentModel.getCurrentProject().inform();
	}

}

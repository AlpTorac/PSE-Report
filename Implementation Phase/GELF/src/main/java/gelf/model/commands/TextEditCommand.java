package gelf.model.commands;

import gelf.model.parsers.LibertyParser;
import gelf.model.project.Model;
import gelf.model.project.Project;
import gelf.model.elements.*;
import gelf.model.exceptions.InvalidFileFormatException;

/**
 * Updates the data in a given element according to the user input String in the
 * text edit
 * 
 * @author Xhulio Pernoca
 */
public class TextEditCommand implements Command {

    // clones of the new and old elements so that the state of the element can be
    // reverted
    private Element newElementClone;
    private Element oldElementClone;

    // reference to the element in question
    private Element element;

    // Model and project instances
    private Model currentModel = Model.getInstance();
    private Project currentProject = currentModel.getCurrentProject();

    /**
     * Instantiates the Command and parses the required content to come up with the
     * newElement so that work can be avoided when command is redone
     * 
     * @param oldContent the old String content
     * @param element    the element in question
     * @throws InvalidFileFormatException if the new String Content doesn't fit
     *                                    Liberty File format
     */
    public TextEditCommand(String newContent, Element element) throws InvalidFileFormatException {
        this.element = element;
        try {
            if (element instanceof Library) {
                newElementClone = LibertyParser.parseLibrary(newContent);
            } else if (element instanceof Cell) {
                newElementClone = LibertyParser.parseCell(newContent, ((Cell) element).getParentLibrary().getName());
            } else {
                Pin oldPin = (Pin) element;
                newElementClone = LibertyParser.parsePin(newContent, oldPin.getParent().getInPins(),
                        oldPin.getParent().getParentLibrary().getName() + "/" + oldPin.getParent().getName());
            }
            oldElementClone = element.clone();
        } catch (InvalidFileFormatException e) {
            throw new InvalidFileFormatException(e.getMessage());
        } catch (Exception e) {
            throw new InvalidFileFormatException("File format is invalid");
        }
        if (!oldElementClone.getClass().equals(newElementClone.getClass())) {
            throw new InvalidFileFormatException("Element type change not possible within " + "the Element visualiser");
        }
    }

    /**
     * Executes the command by replacing the active element data with the new data
     */
    public void execute() {
        Project.replaceElementData(element, newElementClone);
        currentModel.getCurrentCommandHistory().addCommand(this);
        currentProject.inform();
    }

    /**
     * Undoes the command by replacing the active element data with the old one
     */
    public void undo() {
        Project.replaceElementData(element, oldElementClone);
        currentProject.inform();
    }
}

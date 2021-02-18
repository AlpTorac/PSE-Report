package gelf.model.commands;

import gelf.model.parsers.LibertyParser;
import gelf.model.project.Model;
import gelf.model.project.Project;
import gelf.model.elements.*;
import gelf.model.exceptions.InvalidFileFormatException;

/**
 * Updates the data in a given element according to the user input String in the text edit
 * @author Xhulio Pernoca
 */
public class TextEditCommand implements Command {
    //Useful depending on how the undo functionality is implemented for TextEdit
    private String oldContent;
    private String newContent;

    //clones of the new and old elements so that the state of the element can be reverted
    private Element newElementClone;
    private Element oldElementClone;

    //reference to the element in question
    private Element element;

    //Model and project instances
    private Model currentModel = Model.getInstance();
    private Project currentProject = currentModel.getCurrentProject();

    /**
     * Instantiates the Command and parses the required content to come up with the newElement
     * so that work can be avoided when command is redone
     * @param oldContent the old String content
     * @param newContent the new String content
     * @param element the element in question
     * @throws InvalidFileFormatException if the new String Content doesn't fit Liberty File format
     */
    public TextEditCommand(String oldContent, String newContent, Element element) throws InvalidFileFormatException {
        this.oldContent = oldContent;
        this.newContent = newContent;
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
            throw new InvalidFileFormatException("Element type change not possible within" +
            "the Element visualiser");
        }
    }

    /**
     * Executes the command by replacing the active element data with the new data
     */
    public void execute() {
        replaceElementData(element, newElementClone);
        currentModel.getCurrentCommandHistory().addCommand(this);
        currentProject.inform();
    }

    /**
     * Undoes the command by replacing the active element data with the old one
     */
    public void undo() {
    	replaceElementData(element, oldElementClone);
        currentProject.inform();
    }
    
    /**
     * Replaces the element data on on element so that the reference of that
     * element does not change and it keeps its File data
     * @param element the element to be replaced
     * @param dataElement the element containing the data
     */
    private void replaceElementData(Element element, Element dataElement) {
        if (element instanceof Library) {
            Library lib = (Library) element;
            Library dataLib = (Library) dataElement;
            lib.replaceData(dataLib);
        } else if (element instanceof Cell) {
            Cell cell = (Cell) element;
            Cell dataCell = (Cell) dataElement;
            cell.replaceData(dataCell);
        } else if (element instanceof InputPin) {
            InputPin pin = (InputPin) element;
            InputPin dataPin = (InputPin) dataElement;
            pin.replaceData(dataPin);
        } else if (element instanceof OutputPin) {
            OutputPin pin = (OutputPin) element;
            OutputPin dataPin = (OutputPin) dataElement;
            pin.replaceData(dataPin);
        }
    }
}

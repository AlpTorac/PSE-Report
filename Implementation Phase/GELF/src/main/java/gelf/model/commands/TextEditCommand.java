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

    private Element newElementClone;
    private Element oldElementClone;
    private Element element;
    private Model currentModel = Model.getInstance();
    private Project currentProject = currentModel.getCurrentProject();

    public TextEditCommand(String oldContent, String newContent, Element element) throws InvalidFileFormatException {
        this.oldContent = oldContent;
        this.newContent = newContent;
        this.oldElementClone = ((Library) element).clone();
        this.element = element;
        if (element instanceof Library) {
            newElementClone = LibertyParser.parseLibrary(newContent);
        } else if (element instanceof Cell) {
            newElementClone = LibertyParser.parseCell(newContent, ((Cell) element).getParentLibrary().getName());
        } else {
            Pin oldPin = (Pin) element;
            newElementClone = LibertyParser.parsePin(newContent, oldPin.getParent().getInPins(), 
                            oldPin.getParent().getParentLibrary().getName() + "/" + oldPin.getParent().getName());
        }
        
        if (!oldElementClone.getClass().equals(newElementClone.getClass())) {
            throw new InvalidFileFormatException("Element type change not possible within" +
            "the Element visualiser");
        }
    }

    public void execute() {
        replaceElementData(element, newElementClone);
        currentModel.getCurrentCommandHistory().addCommand(this);
        currentProject.inform();
    }

    public void undo() {
    	replaceElementData(element, oldElementClone);
        currentProject.inform();
    }
    
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

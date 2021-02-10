package gelf.model.commands;

import gelf.model.parsers.LibertyParser;
import gelf.model.project.Model;
import gelf.model.elements.*;

import java.util.ArrayList;

/**
 * NEED TO DISCUSS HOW OLD\NEW CONTENT IS SENT BACK TO THE PANEL
 */
public class TextEditCommand implements Command {
    String oldContent;
    String newContent;
    Element newElement;
    Element oldElement;

    public TextEditCommand(String oldContent, String newContent, Element element) throws InvalidFileFormatException {
        this.oldContent = oldContent;
        this.newContent = newContent;
        this.oldElement = element;
        if (element instanceof Library) {
            newElement = LibertyParser.parseLibrary(newContent);
        } else if (element instanceof Cell) {
            newElement = LibertyParser.parseCell(newContent);
        } else {
            newElement = LibertyParser.parsePin(newContent);
        }
    }

    public void execute() {
        replaceElement(oldElement, newElement);
        Model.getInstance().getCurrentCommandHistory().addCommand(this);
    }

    /**
     * Method to replace old elements with new ones using different strategies according
     * to the class type. It can also be used by undo(). SHOULD THIS BE IN PROJECT?
     * @param oldElement the element to be replaced
     * @param newElement the element it is replaced with
     */
    private void replaceElement(Element oldElement, Element newElement) {
        Model currentModel = Model.getInstance();
        if (newElement instanceof Library) {
            Library newLibrary = (Library) newElement;
            ArrayList<Library> libraries = currentModel.getCurrentProject().getLibraries();
            libraries.remove(oldElement);
            libraries.add(newLibrary);
            currentModel.getCurrentProject().setLibraries(libraries);
        } else if (newElement instanceof Cell) {
            Cell newCell = (Cell) newElement;
            ArrayList<Cell> cells = newCell.getParentLibrary().getCells();
            cells.remove(oldElement);
            cells.add(newCell);
            newCell.getParentLibrary().setCells(cells);
        } else if (newElement instanceof InputPin) {
            InputPin newPin = (InputPin) newElement;
            ArrayList<InputPin> pins = newPin.getParent().getInPins();
            pins.remove(oldElement);
            pins.add(newPin);
            newPin.getParent().setInPins(pins);
        } else {
            OutputPin newPin = (OutputPin) newElement;
            ArrayList<OutputPin> pins = newPin.getParent().getOutPins();
            pins.remove(oldElement);
            pins.add(newPin);
            newPin.getParent().setOutPins(pins);
        }
    }

    public void undo() {

    }
}

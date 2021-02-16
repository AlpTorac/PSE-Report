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

    private Element newElement;
    private Element oldElement;
    private Model currentModel = Model.getInstance();
    private Project currentProject = currentModel.getCurrentProject();

    public TextEditCommand(String oldContent, String newContent, Element element) throws InvalidFileFormatException {
        this.oldContent = oldContent;
        this.newContent = newContent;
        this.oldElement = element;
        if (element instanceof Library) {
            newElement = LibertyParser.parseLibrary(newContent);
        } else if (element instanceof Cell) {
            newElement = LibertyParser.parseCell(newContent);
        } else {
            newElement = LibertyParser.parsePin(newContent, ((Pin) element).getParent().getInPins());
        }
    }

    public void execute() {
        currentProject.replaceElement(oldElement, newElement);
        currentModel.getCurrentCommandHistory().addCommand(this);
        currentProject.inform();
    }

    public void undo() {
    	currentProject.replaceElement(newElement, oldElement);
        currentProject.inform();
    }
}

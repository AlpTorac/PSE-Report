package gelf.model.commands;

import gelf.model.parsers.LibertyParser;
import gelf.model.project.Model;
import gelf.model.elements.Library;
import gelf.model.exceptions.InvalidFileFormatException;

/**
 * Creates a Library from scratch
 * @author Xhulio Pernoca
 */
public class CreateLibraryCommand implements Command {
    private Library createdLibrary;
    Model currentModel = Model.getInstance();

    /**
     * Instantiates the command to creat a library
     * @param name the name of the library
     * @param content the string content of the library
     * @throws InvalidFileFormatException if the content of the library is not in Liberty file format
     */
    public CreateLibraryCommand(String name, String content) throws InvalidFileFormatException {
        createdLibrary = LibertyParser.parseLibrary(content);
        createdLibrary.setName(name);
    }

    /**
     * Executes the command
     */
    public void execute() {
        currentModel.getCurrentProject().getLibraries().add(createdLibrary);
        currentModel.getCurrentCommandHistory().addCommand(this);
		currentModel.getCurrentProject().inform();
    }

    /**
     * Undoes the command by removing the library
     */
    public void undo() {
    	currentModel.getCurrentProject().getLibraries().remove(createdLibrary);
		currentModel.getCurrentProject().inform();
    }
}

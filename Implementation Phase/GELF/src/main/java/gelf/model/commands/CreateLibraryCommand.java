package gelf.model.commands;

import gelf.model.parsers.LibertyParser;
import gelf.model.project.Model;
import gelf.model.elements.Library;
import gelf.model.exceptions.InvalidFileFormatException;

public class CreateLibraryCommand implements Command {
    private Library createdLibrary;
    Model currentModel = Model.getInstance();

    public CreateLibraryCommand(String name, String content) throws InvalidFileFormatException {
        createdLibrary = LibertyParser.parseLibrary(content);
        createdLibrary.setName(name);
    }

    public void execute() {
        currentModel.getCurrentProject().getLibraries().add(createdLibrary);
        currentModel.getCurrentCommandHistory().addCommand(this);
		currentModel.getCurrentProject().inform();
    }

    public void undo() {
    	currentModel.getCurrentProject().getLibraries().remove(createdLibrary);
		currentModel.getCurrentProject().inform();
    }
}

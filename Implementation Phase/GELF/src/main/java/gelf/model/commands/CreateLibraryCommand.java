package gelf.model.commands;

import gelf.model.parsers.LibertyParser;
import gelf.model.elements.Library;

public class CreateLibraryCommand implements Command {
    private Library createdLibrary;

    public CreateLibraryCommand(String name, String content) throws InvalidFileFormatException {
        createdLibrary = LibertyParser.parseLibrary(content);
        createdLibrary.setName(name);
    }

    public void execute() {
        Model currentModel = Model.getInstance();
        currentModel.getProject().getLibraries().add(createdLibrary);
        currentModel.getCommandHistory().addCommand(this);
    }

    public void undo() {

    }
}
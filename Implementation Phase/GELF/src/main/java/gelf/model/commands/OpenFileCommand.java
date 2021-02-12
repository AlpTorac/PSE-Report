package gelf.model.commands;

import java.io.File;

import gelf.model.elements.Library;
import gelf.model.exceptions.InvalidFileFormatException;
import gelf.model.parsers.LibertyParser;
import gelf.model.project.FileManager;
import gelf.model.project.Model;

public class OpenFileCommand implements Command {
	private Library openedLibrary;
	private File openedFile;
	private String libraryString;
	
	public OpenFileCommand() {
		
	}

	@Override
	public void execute() throws InvalidFileFormatException {
		openedFile = FileManager.openFile();
		LibertyParser.parseLibrary(libraryString);
		Model currentModel = Model.getInstance();
		currentModel.getCurrentProject().notify();
		currentModel.getCurrentCommandHistory().addCommand(this);
	}

	@Override
	public void undo() {
		
	}

}

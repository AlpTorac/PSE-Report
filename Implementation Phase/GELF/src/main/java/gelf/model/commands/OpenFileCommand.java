package gelf.model.commands;

import java.io.File;

import gelf.model.elements.Library;
import gelf.model.parsers.LibertyParser;

public class OpenFileCommand implements Command {
	private Library openedLibrary;
	private File openedFile;
	private String libraryString;
	
	public OpenFileCommand() {
		
	}

	@Override
	public void execute() {
		openedFile = FileManager.openFile();
		LibertyParser.parseLibrary(libraryString);
		Model currentModel = Model.getInstance();
		currentModel.getProject().notify();
		currentModel.getCommandHistory().addCommand(this);
	}

	@Override
	public void undo() {
		
	}

}

package gelf.model.commands;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import gelf.model.elements.Library;
import gelf.model.exceptions.InvalidFileFormatException;
import gelf.model.parsers.LibertyParser;
import gelf.model.project.FileManager;
import gelf.model.project.Model;


public class OpenFileCommand implements Command {
	private Library openedLibrary;
	private File openedFile;
	private String libraryContent;
	
	public OpenFileCommand() {
		
	}

	@Override
	public void execute() throws InvalidFileFormatException {
		openedFile = FileManager.openFile();
		if (openedFile == null) {
			return;
		}
		try {
			libraryContent = readFile(openedFile.getAbsolutePath());
			openedLibrary = LibertyParser.parseLibrary(libraryContent);
			openedLibrary.setPath(openedFile.getAbsolutePath());
			openedLibrary.setLibraryFile(openedFile);
			Model currentModel = Model.getInstance();
			currentModel.getCurrentProject().notify();
			currentModel.getCurrentCommandHistory().addCommand(this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String readFile(String pathname) throws IOException {

	    File file = new File(pathname);
	    StringBuilder fileContents = new StringBuilder((int)file.length());        

	    try (Scanner scanner = new Scanner(file)) {
	        while(scanner.hasNextLine()) {
	            fileContents.append(scanner.nextLine() + System.lineSeparator());
	        }
	        return fileContents.toString();
	    }
	}

	@Override
	public void undo() {
		
	}

}

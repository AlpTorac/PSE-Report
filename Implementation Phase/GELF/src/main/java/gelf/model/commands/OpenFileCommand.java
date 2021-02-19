package gelf.model.commands;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import gelf.model.elements.Library;
import gelf.model.exceptions.InvalidFileFormatException;
import gelf.model.parsers.LibertyParser;
import gelf.model.project.FileManager;
import gelf.model.project.Model;

/**
 * Opens a selected file, converts it to string and sends it to the parser.
 * @author Kerem Kara
 */
public class OpenFileCommand implements Command {
	private Library openedLibrary;
	private File openedFile;
	private String libraryContent;
	private Model currentModel = Model.getInstance();
	
	public OpenFileCommand() {	
	}
	/**
	 * Executes the command by calling openFile() from FileManager
	 */
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
			openedLibrary.setElementContent(libraryContent);

			ArrayList<Library> libraries = currentModel.getCurrentProject().getLibraries();
			libraries.add(openedLibrary);
			currentModel.getCurrentProject().setLibraries(libraries);

			currentModel.getCurrentProject().inform();
			currentModel.getCurrentCommandHistory().addCommand(this);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidFileFormatException e) {
            throw new InvalidFileFormatException(e.getMessage());
        } catch (Exception e) {
            throw new InvalidFileFormatException("File format is invalid");
        }
	}
	/**
	 * Converts the file to a String
	 * @param pathname Path of the given file
	 * @return String content of the file
	 * @throws IOException
	 */
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

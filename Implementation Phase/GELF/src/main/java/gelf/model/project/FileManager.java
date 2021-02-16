package gelf.model.project;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class FileManager extends JFrame{
	
	
	public FileManager() {
	}
   
	public static File openFile() {
		FileChooserOpen fc = new FileChooserOpen("Open");
		return fc.getSelectedFile();
	}
	
	public static void saveFile(String content, String extension) 
			throws IOException {
		FileChooserSave fc = new FileChooserSave("Save");
		String path = fc.getSelectedFile().getAbsolutePath() + extension;
		File file = new File(path);
		writeStringToFile(file, content);
	}
	
	public static void saveFileToPath(String content, String extension, String path) 
			throws IOException {
		String newPath = path + extension;
		File file = new File(newPath);
		writeStringToFile(file, content);
	}
	
	public static void writeStringToFile(File file, String content) 
			throws IOException {
			    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			    writer.write(content);
			    writer.close();
	}


	
	/**
	 * 
	 * @param file Properties of this file will be shown
	 * @return String array of properties
	 */
	@SuppressWarnings("deprecation")
	public static String[] showProperties(File file) {
		file.length();
		file.lastModified();
		String[] propArray = new String[3];
		propArray[0] = "Path: "+ file.getAbsolutePath();
		propArray[1] = "Size: " + String.valueOf(file.length()) + " bytes";
		Date d = new Date(file.lastModified());
		propArray[2] = "Last modified: " + d.toGMTString();
		
		return propArray;
	}
}
	
 


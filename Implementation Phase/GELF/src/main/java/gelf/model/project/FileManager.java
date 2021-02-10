package gelf.model.project;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class FileManager extends JFrame{
	
	
	public FileManager() {
	}
   
	public static File openFile() {
		FileChooserOpen fc = new FileChooserOpen("Open");
		if(fc.getSelectedFile() == null) {
			fc.showCancel();
		}
		return fc.getSelectedFile();
	}
	
	public static File openFile(String name) {
		return null;
		
	}
	
	public static void saveFile(String name, String[] fileData, String path) {
		JFileChooser chooser = new JFileChooser(); 
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("Select a directory to save the library");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);

		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
		    System.out.println("getSelectedFile() : " + chooser.getSelectedFile());
			/*
		    String dirName = chooser.getCurrentDirectory().getPath();
			File dir = new File (dirName);
			 // not sure what to do after this
			File actualFile = new File (dir, libraryFile.getPath());
			Writer output = null;
		    output = new BufferedWriter(new FileWriter(actualFile));
		    
		    output.close();
		    */
		} 
		else {
		     System.out.println("No Selection ");
		}
	}
/*
	public static void saveFile(String name, String, String, String) {
		
	}
	*/
	
	public static void main(String[] args) {
		System.out.println(openFile());
	}
 
}

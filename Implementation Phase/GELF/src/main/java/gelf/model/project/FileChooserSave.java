package gelf.model.project;

import java.awt.Dimension;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

public class FileChooserSave extends JFrame {
	
	private static final long serialVersionUID = 1L;
	JFileChooser fc = new JFileChooser();
	
	private File selectedFile;

   public FileChooserSave(String title)
   {
	   super(title);
	   fc.setPreferredSize(new Dimension(800,600));
      fc.addChoosableFileFilter(new FileFilter()
      {
          @Override
          public boolean accept(File file)
          {
         	 if (file.isDirectory()) {
                  return true;
              } else {
                  return false;
              }
          }
 	
          @Override
          public String getDescription()
          {
             return "Directories only";
          }
       });
      
      fc.setAcceptAllFileFilterUsed(false);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      
      switch (fc.showSaveDialog(FileChooserSave.this))
      {
         case JFileChooser.APPROVE_OPTION:
            final int option = JOptionPane.showConfirmDialog(
            		FileChooserSave.this, "Save: "+
                                          fc.getSelectedFile(),
                                          "FileChooserSave",
                                          JOptionPane.YES_NO_OPTION);
            if(option == JOptionPane.YES_OPTION){
            	selectedFile = fc.getSelectedFile();
            }
            else if(option == JOptionPane.NO_OPTION){
            	showCancel();
            }
            break;

         case JFileChooser.CANCEL_OPTION:
            JOptionPane.showMessageDialog(FileChooserSave.this, "Cancelled",
                                          "FileChooserOpen",
                                          JOptionPane.OK_OPTION);
            break;
      
         case JFileChooser.ERROR_OPTION:
            JOptionPane.showMessageDialog(FileChooserSave.this, "Error",
                                          "FileChooserOpen",
                                          JOptionPane.OK_OPTION);
      }        
   }
   public void showCancel() {
	   JOptionPane.showMessageDialog(FileChooserSave.this, "Cancelled",
               "FileChooserOpen",
               JOptionPane.OK_OPTION);
   }
   
   public File getSelectedFile() {
	   return selectedFile;
   }
}

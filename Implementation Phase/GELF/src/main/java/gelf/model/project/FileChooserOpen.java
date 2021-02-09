package gelf.model.project;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

public class FileChooserOpen extends JFrame {
	
	private static final long serialVersionUID = 1L;
	JFileChooser fc = new JFileChooser();
	
	private File selectedFile;

   public FileChooserOpen(String title)
   {
      super(title);
      fc.setFileFilter(new FileFilter()
      {
         @Override
         public boolean accept(File file)
         {
        	 if (file.isDirectory()) {
                 return true;
             } else {
                 String filename = file.getName().toLowerCase();
                 return filename.endsWith(".lib") ;
             }
         }

         @Override
         public String getDescription()
         {
            return ".lib files";
         }
      });
      fc.setAcceptAllFileFilterUsed(false);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      
      switch (fc.showOpenDialog(FileChooserOpen.this))
      {
         case JFileChooser.APPROVE_OPTION:
            final int option = JOptionPane.showConfirmDialog(
            		FileChooserOpen.this, "Open: "+
                                          fc.getSelectedFile(),
                                          "FileChooserOpen",
                                          JOptionPane.YES_NO_OPTION);
            if(option == JOptionPane.OK_OPTION){
            	selectedFile = fc.getSelectedFile();
            }
            else if(option == JOptionPane.CANCEL_OPTION){
            }
            break;

         case JFileChooser.CANCEL_OPTION:
            JOptionPane.showMessageDialog(FileChooserOpen.this, "Cancelled",
                                          "FileChooserOpen",
                                          JOptionPane.OK_OPTION);
            break;
      
         case JFileChooser.ERROR_OPTION:
            JOptionPane.showMessageDialog(FileChooserOpen.this, "Error",
                                          "FileChooserOpen",
                                          JOptionPane.OK_OPTION);
      }        
   }
   public void showCancel() {
	   JOptionPane.showMessageDialog(FileChooserOpen.this, "Cancelled",
               "FileChooserOpen",
               JOptionPane.OK_OPTION);
   }
   
   public File getSelectedFile() {
	   return selectedFile;
   }
}

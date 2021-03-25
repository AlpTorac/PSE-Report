package gelf.model.project;

import java.awt.Dimension;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

public class FileChooserOpen extends JFrame {

   private static final long serialVersionUID = 1L;
   JFileChooser fc = new JFileChooser();

   private File selectedFile;

   public FileChooserOpen(String title) {
      super(title);
      fc.setPreferredSize(new Dimension(700, 500));
      fc.addChoosableFileFilter(new FileFilter() {
         @Override
         public boolean accept(File file) {
            if (file.isDirectory()) {
               return true;
            } else {
               String filename = file.getName().toLowerCase();
               return filename.endsWith(".lib");
            }
         }

         @Override
         public String getDescription() {
            return ".lib files";
         }
      });

      fc.setAcceptAllFileFilterUsed(true);
      setDefaultCloseOperation(EXIT_ON_CLOSE);

      switch (fc.showOpenDialog(FileChooserOpen.this)) {
      case JFileChooser.APPROVE_OPTION:
         final int option = JOptionPane.showConfirmDialog(FileChooserOpen.this, "Open: " + fc.getSelectedFile(),
               "FileChooserOpen", JOptionPane.YES_NO_OPTION);
         if (option == JOptionPane.YES_OPTION) {
            selectedFile = fc.getSelectedFile();
         }
         break;

      case JFileChooser.ERROR_OPTION:
         JOptionPane.showMessageDialog(FileChooserOpen.this, "Error", "FileChooserOpen", JOptionPane.OK_OPTION);
      }
   }

   public File getSelectedFile() {
      return selectedFile;
   }
}

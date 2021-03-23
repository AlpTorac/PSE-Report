package gelf.controller.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import gelf.model.elements.Cell;
import gelf.model.elements.Element;
import gelf.model.elements.Library;
import gelf.model.elements.Pin;
import gelf.model.project.Model;
import gelf.view.composites.Outliner;

/**
 * Listener for saving the library as a new file.
 * @author Ege Uzhan
 */
public class SaveAsListener implements ActionListener{
	
	private Outliner outliner;
	
	/** 
	 * Initializes the listener.
	 * @param outliner The outliner.
	 */
	public SaveAsListener(Outliner outliner) {
		this.outliner = outliner;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (Model.getInstance().getCurrentProject().getLibraries().isEmpty()) {
			JOptionPane.showMessageDialog(new JFrame(), "No library has been loaded in the application.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (outliner.getSelectedElements().isEmpty()) {
			JOptionPane.showMessageDialog(new JFrame(), "No library has been selected.", "Error", JOptionPane.ERROR_MESSAGE);
			return;		
		}
		
		if (outliner.getSelectedElements().size() == 1) {
			Element element = outliner.getSelectedElements().get(0);
			if (element instanceof Pin) {
				Library library = ((Pin) element).getParent().getParentLibrary();
				try {
					library.saveLibraryAs();
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(new JFrame(), e1.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
			else if (element instanceof Cell) {
				Library library = ((Cell) element).getParentLibrary();
				try {
					library.saveLibraryAs();
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(new JFrame(), e1.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
			else {
				Library library = (Library) element;
				try {
					library.saveLibraryAs();
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(new JFrame(), e1.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
			
		}
		else {
			JOptionPane.showMessageDialog(new JFrame(), "Select one element to save.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
	}

}

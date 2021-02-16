package gelf.controller.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import gelf.model.elements.Cell;
import gelf.model.elements.Element;
import gelf.model.elements.InputPin;
import gelf.model.elements.Library;
import gelf.model.elements.OutputPin;
import gelf.model.elements.Pin;
import gelf.model.project.Model;
import gelf.model.project.Project;
import gelf.view.composites.Outliner;
import gelf.view.composites.SubWindow;
/*
 * Listener for saving changes.
 */
public class SaveListener implements ActionListener{
	
	private Outliner outliner;
	
	public SaveListener(Outliner outliner) {
		this.outliner = outliner;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (Model.getInstance().getCurrentProject().getLibraries().isEmpty()) {
			JOptionPane.showMessageDialog(new JFrame(), "No library has been loaded in the application.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (outliner.getSelectedElements().size() == 1) {
			Element element = outliner.getSelectedElements().get(0);
			if (element instanceof Pin) {
				Library library = ((Pin) element).getParent().getParentLibrary();
				try {
					library.saveLibrary();
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(new JFrame(), e1.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
			else if (element instanceof Cell) {
				Library library = ((Cell) element).getParentLibrary();
				try {
					library.saveLibrary();
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(new JFrame(), e1.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
			else {
				Library library = (Library) element;
				try {
					library.saveLibrary();
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

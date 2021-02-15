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
import gelf.model.elements.Library;
import gelf.model.elements.Pin;
import gelf.model.project.Model;
import gelf.model.project.Project;
import gelf.view.composites.SubWindow;
import gelf.view.composites.TextEditor;

/*
 * Listener for saving the library as a new file.
 */
public class SaveAsListener implements ActionListener, FocusListener{
	
	ArrayList<SubWindow> subwindows;
	
	public SaveAsListener() {
		subwindows = new ArrayList<SubWindow>();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (subwindows.size() == 1) {
			Element element = subwindows.get(0).getElement();
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
	}

	@Override
	public void focusGained(FocusEvent e) {
		if (e.getSource() instanceof SubWindow) {
			subwindows.add((SubWindow) e.getSource());
		}
		else {
			
		}
		
	}

	@Override
	public void focusLost(FocusEvent e) {
		subwindows.remove((SubWindow) e.getSource());
	}

}

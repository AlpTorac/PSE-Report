package gelf.controller.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.HashSet;

import gelf.model.elements.Cell;
import gelf.model.elements.Element;
import gelf.model.elements.Library;
import gelf.model.elements.Pin;
import gelf.model.project.Model;
import gelf.model.project.Project;
import gelf.view.composites.SubWindow;

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
				library.saveLibraryAs();
			}
			else if (element instanceof Cell) {
				Library library = ((Cell) element).getParentLibrary();
				library.saveLibraryAs();
			}
			else {
				Library library = (Library) element;
				library.saveLibraryAs();
			}
			
		}
	}

	@Override
	public void focusGained(FocusEvent e) {
		subwindows.add((SubWindow) e.getSource());
	}

	@Override
	public void focusLost(FocusEvent e) {
		subwindows.remove((SubWindow) e.getSource());
	}

}

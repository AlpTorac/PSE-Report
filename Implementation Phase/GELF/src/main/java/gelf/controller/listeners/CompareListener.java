package gelf.controller.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import gelf.model.elements.Cell;
import gelf.model.elements.Element;
import gelf.model.elements.Library;
import gelf.model.elements.Pin;
import gelf.model.project.Model;
import gelf.view.composites.Outliner;
import gelf.view.composites.SubWindow;
import gelf.view.composites.SubWindowArea;

public class CompareListener implements ActionListener {

	private Outliner outliner;
	private SubWindowArea subwindows;
	
	public CompareListener(Outliner outliner, SubWindowArea subwindows) {
		this.outliner = outliner;
		this.subwindows = subwindows;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (outliner.getSelectedElements().size() <= 1) {
			JOptionPane.showMessageDialog(new JFrame(), "Select at least 2 elements to compare.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		ArrayList<Library> libraries = new ArrayList<Library>();
		ArrayList<Element> elements = new ArrayList<Element>();
		for (Element element: outliner.getSelectedElements()) {
			if (element instanceof Library) {
				libraries.add((Library) element);
			}
			else {
				elements.add(element);
			}
			
		}
		if (libraries.size() >= 1 && elements.size() >= 1) {
			JOptionPane.showMessageDialog(new JFrame(), "Choose either multiple libraries or multiple cells & pins.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (libraries.isEmpty()) {
			subwindows.addSubWindow(new SubWindow(elements, Model.getInstance().getCurrentProject(), outliner, subwindows, 200, 200));
		}
		else if (elements.isEmpty()) {
			ArrayList<Element> libraryList = new ArrayList<Element>();
			for (Library library: libraries) {
				libraryList.add(library);
			}
			subwindows.addSubWindow(new SubWindow(libraryList, Model.getInstance().getCurrentProject(), outliner, subwindows, 200, 200));
		}
		
		
	}

}

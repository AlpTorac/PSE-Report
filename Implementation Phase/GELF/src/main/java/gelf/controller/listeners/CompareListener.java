package gelf.controller.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import gelf.model.elements.Cell;
import gelf.model.elements.Element;
import gelf.model.elements.InputPin;
import gelf.model.elements.Library;
import gelf.model.project.Model;
import gelf.view.composites.Outliner;
import gelf.view.composites.SubWindow;
import gelf.view.composites.SubWindowArea;

/**
 * Listener for the compare button.
 * @author Ege Uzhan
 */
public class CompareListener implements ActionListener {

	private Outliner outliner;
	private SubWindowArea subwindows;
	
	/**
	 * Initializes the listener.
	 * @param outliner Outliner
	 * @param subwindows SubWindowArea 
	 */
	public CompareListener(Outliner outliner, SubWindowArea subwindows) {
		this.outliner = outliner;
		this.subwindows = subwindows;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (Model.getInstance().getCurrentProject().getLibraries().isEmpty()) {
			JOptionPane.showMessageDialog(new JFrame(), "No library has been loaded in the application.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (outliner.getSelectedElements().isEmpty() || outliner.getSelectedElements().size() <= 1 || outliner.getSelectedElements().size() > 2) {
			JOptionPane.showMessageDialog(new JFrame(), "Select 2 elements to compare.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		ArrayList<Element> libraries = new ArrayList<Element>();
		ArrayList<Element> cells = new ArrayList<Element>();
		ArrayList<Element> inputPins = new ArrayList<Element>();
		ArrayList<Element> outputPins = new ArrayList<Element>();
		for (Element element: outliner.getSelectedElements()) {
			if (element instanceof Library) {
				libraries.add(element);
			}
			else if (element instanceof Cell) {
				cells.add(element);
			}
			else if (element instanceof InputPin) {
				inputPins.add(element);
			}
			else {
				outputPins.add(element); 
			}
			
		}
		
		if (outliner.getSelectedElements().size() == cells.size()) {
			subwindows.addSubWindow(new SubWindow(cells, Model.getInstance().getCurrentProject(), outliner, subwindows, 200, 200));
		}
		else if (outliner.getSelectedElements().size() == inputPins.size()) {
			subwindows.addSubWindow(new SubWindow(inputPins, Model.getInstance().getCurrentProject(), outliner, subwindows, 200, 200));
		}
		else if (outliner.getSelectedElements().size() == outputPins.size()) {
			subwindows.addSubWindow(new SubWindow(outputPins, Model.getInstance().getCurrentProject(), outliner, subwindows, 200, 200));
		}
		else if (outliner.getSelectedElements().size() == libraries.size()) {
			subwindows.addSubWindow(new SubWindow(libraries, Model.getInstance().getCurrentProject(), outliner, subwindows, 200, 200));
		}
		else {
			JOptionPane.showMessageDialog(new JFrame(), "Choose multiple elements of the same type.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
	}

}

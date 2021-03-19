package gelf.controller.listeners;

import gelf.model.elements.Cell;
import gelf.model.elements.Element;
import gelf.model.elements.Library;
import gelf.model.elements.Pin;
import gelf.model.project.Model;
import gelf.model.commands.RenameCommand;
import gelf.view.composites.Outliner;
import gelf.view.composites.SubWindow;
import gelf.view.composites.SubWindowArea;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Listener for renaming a liberty file.
 * @author Ege Uzhan
 */
public class RenameListener implements ActionListener {
	
	private Outliner outliner;
	private SubWindowArea subwindows;
	
	/**
	 * Initializes the listener.
	 * @param outliner The outliner.
	 * @param subwindows The subwindow area
	 */
	public RenameListener(Outliner outliner, SubWindowArea subwindows) {
		this.outliner = outliner;
		this.subwindows = subwindows;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (outliner.getSelectedElements().size() != 1) {
			JOptionPane.showMessageDialog(new JFrame(), "Please select only one element to rename", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
        Element element = outliner.getSelectedElements().get(0);
        
        String newName = JOptionPane.showInputDialog(new JFrame(), "New Name: ", "Rename", JOptionPane.OK_CANCEL_OPTION);
        if (!newName.matches("\\w+")) {
        	JOptionPane.showMessageDialog(new JFrame(), "Not a valid name", "Error", JOptionPane.ERROR_MESSAGE);
			return;
        	
        }
		if (checkExistingName(element, newName)) {
			return;
		}
        
		RenameCommand rename = new RenameCommand(element, newName);
		rename.execute();
		
		for (SubWindow subwindow: subwindows.subWindows) {
		}
		
	}
	
	/**
	 * Checks if there is an element with the same name.
	 * @param element Selected Element in the outliner
	 * @param elementName New name given to the element.
	 */
	public boolean checkExistingName(Element element, String elementName) {
		if (element instanceof Library) {
			for (Library library: Model.getInstance().getCurrentProject().getLibraries()) {
				if (library.getName().equals(elementName)) {
					JOptionPane.showMessageDialog(new JFrame(), "A library with the same name already exists.", "Error", JOptionPane.ERROR_MESSAGE);
					return true;
				}
			}
		} else if (element instanceof Cell) {
			Cell cell = (Cell) element;
			Library parent = cell.getParentLibrary();
			for (Cell cells: parent.getCells()) {
				if (cells.getName().equals(elementName)) {
					JOptionPane.showMessageDialog(new JFrame(), "A cell with the same name already exists in the library.",
							"Error", JOptionPane.ERROR_MESSAGE);
					return true;
				}
			}
		} else {
			Pin pin = (Pin) element;
			Cell parent = pin.getParent();
			for (Pin pins: parent.getInPins()) {
				if (pins.getName().equals(elementName)) {
					JOptionPane.showMessageDialog(new JFrame(), "An input pin with the same name already exists in the cell",
							"Error", JOptionPane.ERROR_MESSAGE);
					return true;
				}
			}
			for (Pin pins: parent.getOutPins()) {
				if (pins.getName().equals(elementName)) {
					JOptionPane.showMessageDialog(new JFrame(), "An output pin with the same name already exists in the cell",
							"Error", JOptionPane.ERROR_MESSAGE);
					return true;
				}
			}
		}
		return false;
	}

	

}

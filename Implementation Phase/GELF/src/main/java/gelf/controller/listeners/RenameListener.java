package gelf.controller.listeners;

import gelf.model.elements.Cell;
import gelf.model.elements.Element;
import gelf.model.elements.Library;
import gelf.model.elements.Pin;
import gelf.model.commands.RenameCommand;
import gelf.view.composites.Outliner;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/*
 * Listener for renaming a liberty file.
 */
public class RenameListener implements ActionListener {
	
	private Outliner outliner;
	
	public RenameListener(Outliner outliner) {
		this.outliner = outliner;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (outliner.getSelectedElements().size() != 1) {
			return;
		}
		Element element = outliner.getSelectedElements.get(0);
		String newName = outliner.getNewName().getText();
		
		if (element instanceof Library) {
			Library library = (Library) element;
			if (outliner.contains(newName)) {
				JOptionPane.showMessageDialog(new JFrame(), "A library with the same name already exists.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		if (element instanceof Cell) {
			Cell cell = (Cell) element;
			Library parent = cell.getParentLibrary();
			if (outliner.contains(newName)) {
				JOptionPane.showMessageDialog(new JFrame(), "A cell with the same name already exists in the library.",
						"Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		if (element instanceof Pin) {
			Pin pin = (Pin) element;
			Cell parent = pin.getParent();
			if (outliner.contains(newName)) {
				JOptionPane.showMessageDialog(new JFrame(), "A pin with the same name already exists in the cell",
						"Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		RenameCommand rename = new RenameCommand(element, newName);
		rename.execute();
	}

}

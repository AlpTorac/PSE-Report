package gelf.controller.listeners;

import gelf.view.composites.Outliner;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import gelf.model.commands.Command;
import gelf.model.commands.DeleteCommand;
import gelf.model.elements.Cell;
import gelf.model.elements.Element;

/**
 * Listener for deleting a cell from the library permanently.
 * @author Ege Uzhan
 */
public class DeleteCellListener implements ActionListener {

	private Outliner outliner;
	
	/**
	 * Initializes the listener
	 * @param outliner The outliner.
	 */
	public DeleteCellListener(Outliner outliner) {
		this.outliner = outliner;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (outliner.getSelectedElements().isEmpty()) {
			JOptionPane.showMessageDialog(new JFrame(), "Select at least one cell.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		ArrayList<Cell> cells = new ArrayList<Cell>();
		for (Element element : outliner.getSelectedElements()) {
			if (element instanceof Cell) {
				cells.add((Cell) element);
			}
			else {
				JOptionPane.showMessageDialog(new JFrame(), "Only cells can be deleted.", "Error", JOptionPane.ERROR_MESSAGE);
				cells.removeAll(cells);
				return;
			}
		}
		DeleteCommand delete = new DeleteCommand(cells);
		delete.execute();
		
	}

}

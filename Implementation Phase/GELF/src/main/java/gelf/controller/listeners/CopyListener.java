package gelf.controller.listeners;

import gelf.view.composites.Outliner;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import gelf.model.project.Model;
import gelf.model.project.Project;

import java.util.HashSet;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import gelf.model.elements.Cell;
import gelf.model.elements.Element;

/**
 * Listener for copying a cell to another library.
 * @author Ege Uzhan
 */
public class CopyListener implements ActionListener {

	private Outliner outliner;
	
	/**
	 * Initializes the listener
	 * @param outliner The outliner.
	 */
	public CopyListener(Outliner outliner) {
		this.outliner = outliner;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (outliner.getSelectedElements().isEmpty()) {
			JOptionPane.showMessageDialog(new JFrame(), "Select at least one cell.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		HashSet<Element> cells = new HashSet<Element>();
		for (Element element: outliner.getSelectedElements()) {
			if (element instanceof Cell) {
				cells.add((Cell) element);
			}
			else {
				JOptionPane.showMessageDialog(new JFrame(), "Only cells can be copied.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		Model currentModel = Model.getInstance();
		Project project = currentModel.getCurrentProject();
		project.setCopiedElements(cells);
	}

}

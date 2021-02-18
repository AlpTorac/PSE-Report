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

/*
 * Listener for copying a cell to another library.
 */
public class CopyListener implements ActionListener {

	private Outliner outliner;
	
	public CopyListener(Outliner outliner) {
		this.outliner = outliner;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		HashSet<Cell> cells = new HashSet<Cell>();
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

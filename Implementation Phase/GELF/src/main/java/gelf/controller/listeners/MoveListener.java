package gelf.controller.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;

import gelf.model.elements.Cell;
import gelf.model.elements.Element;
import gelf.model.project.Model;
import gelf.model.project.Project;
import gelf.view.composites.Outliner;

/*
 * Listener for moving a cell to another library.
 */
public class MoveListener implements ActionListener {

	private Outliner outliner;
	
	public MoveListener(Outliner outliner) {
		this.outliner = outliner;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		HashSet<Cell> cells = new HashSet<Cell>();
		for (Element element: outliner.getSelectedElements()) {
			if (element instanceof Cell) {
				cells.add((Cell) element);
			}
			else {
				//TODO error
				return;
			}
		}
		Model currentModel = Model.getInstance();
		Project project = currentModel.getCurrentProject();
		for (Element element: cells) {
			project.getCopiedElements().add(element);
		}
	}

	

}

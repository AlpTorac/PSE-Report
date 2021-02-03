package gelf.controller.listeners;

import gelf.view.composites.Outliner;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gelf.model.commands.Command;
import gelf.model.commands.DeleteCommand;

/*
 * Listener for deleting a cell from the library permanently.
 */
public class DeleteCellListener implements ActionListener {

	private Outliner outliner;
	
	public DeleteCellListener(Outliner outliner) {
		this.outliner = outliner;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Command delete = new DeleteCommand(outliner.getSelectedCells());
		delete.execute();
		
	}

}

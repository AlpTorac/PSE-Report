package gelf.controller.listeners;

import gelf.view.composites.Outliner;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * Listener for removing an element from the project.
 */
public class RemoveListener implements ActionListener {

	private Outliner outliner;
	
	public RemoveListener(Outliner outliner) {
		this.outliner = outliner;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		RemoveCommand remove = new RemoveCommand();
		remove.execute();
	}

}

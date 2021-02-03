package gelf.controller.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * Listener for the undo button.
 */
public class UndoListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		UndoCommand undo = new UndoCommand();
		undo.execute();
	}

}

package gelf.controller.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * Listener for the redo button.
 */
public class RedoListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		RedoCommand redo = new RedoCommand();
		redo.execute();
	}

}

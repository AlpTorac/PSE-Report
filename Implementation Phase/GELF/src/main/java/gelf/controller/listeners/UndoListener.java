package gelf.controller.listeners;

import gelf.model.commands.UndoCommand;
import gelf.model.exceptions.InvalidFileFormatException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * Listener for the undo button.
 */
public class UndoListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		UndoCommand undo = new UndoCommand();
		try {
			undo.execute();
		} catch (InvalidFileFormatException e1) {
			//TODO error
		}
	}

}

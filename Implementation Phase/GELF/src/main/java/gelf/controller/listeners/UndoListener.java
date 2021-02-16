package gelf.controller.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gelf.model.project.Model;

/*
 * Listener for the undo button.
 */
public class UndoListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		Model.getInstance().getCurrentCommandHistory().undoCommand();
	}
}

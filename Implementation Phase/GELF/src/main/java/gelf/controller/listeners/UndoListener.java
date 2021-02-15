package gelf.controller.listeners;

import gelf.model.commands.UndoCommand;
import gelf.model.exceptions.InvalidFileFormatException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

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
			JOptionPane.showMessageDialog(new JFrame(), e1.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
	}

}

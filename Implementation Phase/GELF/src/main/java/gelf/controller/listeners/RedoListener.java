package gelf.controller.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import gelf.model.exceptions.InvalidFileFormatException;
import gelf.model.project.Model;

/*
 * Listener for the redo button.
 */
public class RedoListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			Model.getInstance().getCurrentCommandHistory().redoCommand();
		} catch (InvalidFileFormatException e1) {
			JOptionPane.showMessageDialog(new JFrame(), e1.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		return;
	}
	}

}

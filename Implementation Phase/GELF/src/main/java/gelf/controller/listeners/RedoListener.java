package gelf.controller.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import gelf.model.commands.CommandHistory;
import gelf.model.exceptions.InvalidFileFormatException;
import gelf.model.project.Model;

/**
 * Listener for the redo button.
 * @author Ege Uzhan
 */
public class RedoListener implements ActionListener {
	private CommandHistory history = Model.getInstance().getCurrentCommandHistory();

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			if (history.getUndoneCommandsSize() == 0) {
				JOptionPane.showMessageDialog(new JFrame(), "No command can be redone", "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				history.redoCommand();
			}
		} catch (InvalidFileFormatException e1) {
			JOptionPane.showMessageDialog(new JFrame(), e1.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		return;
	}
	}

}

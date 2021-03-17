package gelf.controller.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import gelf.model.commands.CommandHistory;
import gelf.model.project.Model;

/**
 * Listener for the undo button.
 * @author Ege Uzhan
 */
public class UndoListener implements ActionListener {
	private CommandHistory history = Model.getInstance().getCurrentCommandHistory();

	@Override
	public void actionPerformed(ActionEvent e) {
		if (history.getCommandsSize() == 0) {
			JOptionPane.showMessageDialog(new JFrame(), "No command can be undone", "Error", JOptionPane.ERROR_MESSAGE);
		} else {
			history.undoCommand();
		}
	}
}

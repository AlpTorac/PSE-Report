package gelf.controller.listeners;

import gelf.model.commands.OpenFileCommand;
import gelf.model.exceptions.InvalidFileFormatException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/*
 * Listener for loading a liberty file into the application.
 */
public class LoadListener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		OpenFileCommand openFileCommand = new OpenFileCommand();
		try {
			openFileCommand.execute();
		} catch (InvalidFileFormatException e1) {
			JOptionPane.showMessageDialog(new JFrame(), "Invalid file format.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
	}

}

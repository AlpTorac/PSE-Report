package gelf.controller.listeners;

import gelf.model.commands.OpenFileCommand;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * Listener for loading a liberty file into the application.
 */
public class LoadListener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		OpenFileCommand openFileCommand = new OpenFileCommand();
		openFileCommand.execute();
	}

}

package gelf.controller.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * Listener for importing an existing project.
 */
public class LoadProjectListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		OpenFileCommand openFileCommand = new OpenFileCommand();
		openFileCommand.execute();
	}

}

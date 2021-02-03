package gelf.controller.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * Listener for saving the library as a new file.
 */
public class SaveAsListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		Model.save();
	}

}

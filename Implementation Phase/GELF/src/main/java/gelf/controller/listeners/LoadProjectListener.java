package gelf.controller.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import gelf.project.Model;

/*
 * Listener for importing an existing project.
 */
public class LoadProjectListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		Model.loadProject();
		
	}

}

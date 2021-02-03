package gelf.controller.listeners;

import gelf.view.composites.Outliner;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * Listener for renaming a liberty file.
 */
public class RenameListener implements ActionListener {
	
	private Outliner outliner;
	
	public RenameListener(Outliner outliner) {
		this.outliner = outliner;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		RenameCommand rename = new RenameCommand();
		rename.execute();
	}

}

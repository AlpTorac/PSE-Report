package gelf.controller.listeners;

import gelf.view.composites.Outliner;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * Listener for pasting action.
 */
public class PasteListener implements ActionListener {

	private Outliner outliner;
	
	public PasteListener(Outliner outliner) {
		this.outliner = outliner;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		PasteCommand paste = new PasteCommand();
		paste.execute();
	}

}

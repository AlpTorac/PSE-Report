package gelf.controller.listeners;

import gelf.model.commands.PasteCommand;
import gelf.model.elements.Library;
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
		if (outliner.getSelectedElements().size() == 1 && outliner.getSelectedElements().get(0) instanceof Library) {
			Library destinationLibrary = outliner.getSelectedElements().get(0);
			PasteCommand paste = new PasteCommand(destinationLibrary);
			paste.execute();
		}
		else {
			//TODO error
		}
	}

}

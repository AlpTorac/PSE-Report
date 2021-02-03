package gelf.controller.listeners;

import gelf.view.composites.Outliner;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * Listener for comparing elements.
 */
public class CompareListener implements ActionListener {
	
	private Outliner outliner;
	
	public CompareListener(Outliner outliner) {
		this.outliner = outliner;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		CompareCommand compare = new CompareCommand();
		compare.execute();
	}

}

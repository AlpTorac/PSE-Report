package gelf.controller.listeners;

import gelf.view.composites.Outliner;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * Listener for merging libraries.
 */
public class MergeListener implements ActionListener {

	private Outliner outliner;
	
	public MergeListener(Outliner outliner) {
		this.outliner = outliner;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		MergeCommand merge = new MergeListener();
		merge.execute();
	}

}

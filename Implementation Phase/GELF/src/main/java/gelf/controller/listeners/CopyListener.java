package gelf.controller.listeners;

import gelf.view.composites.Outliner;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gelf.model.elements.Element;

/*
 * Listener for copying a cell to another library.
 */
public class CopyListener implements ActionListener {

	private Outliner outliner;
	
	public CopyListener(Outliner outliner) {
		this.outliner = outliner;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		ArrayList<Element> elements = new ArrayList<Element>();
		CopyAction copy = new CopyAction();
		copy.execute();
	}

}

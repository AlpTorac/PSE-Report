package gelf.controller.listeners;

import gelf.view.composites.Outliner;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;

/*
 * Listener the search bar of the outliner.
 */
public class SearchListener implements TextListener {
	
	private Outliner outliner;
	
	public SearchListener(Outliner outliner) {
		this.outliner = outliner;
	}

	@Override
	public void textValueChanged(TextEvent e) {
		// TODO Auto-generated method stub
		
	}

}

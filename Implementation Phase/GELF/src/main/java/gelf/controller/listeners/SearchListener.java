package gelf.controller.listeners;

import gelf.view.components.Panel;
import gelf.view.composites.Outliner;

import java.awt.TextComponent;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;

/*
 * Listener the search bar of the outliner.
 */
public class SearchListener implements TextListener {
	
	private Panel panel;
	
	public SearchListener(Panel panel) {
		this.panel = panel;
	}

	@Override
	public void textValueChanged(TextEvent e) {
		TextComponent tc = (TextComponent) e.getSource();
	    String text = tc.getText();
	    if (tc.getText().equals("")) {
	    	//outliner.show(text);
	    }
	    else {
	    	for (TreeNode node: outliner.tree()) {
	    		if (node.name.equals text) {
	    			//outliner.show(text);
	    		}
	    	}
	    }
	}

}

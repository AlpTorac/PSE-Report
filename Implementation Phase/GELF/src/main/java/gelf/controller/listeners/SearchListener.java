package gelf.controller.listeners;

import gelf.view.components.Panel;
import gelf.view.composites.Outliner;

import java.awt.TextComponent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.tree.DefaultMutableTreeNode;

/*
 * Listens the search bar of the outliner.
 */
public class SearchListener implements KeyListener {
	
	private Outliner outliner;
	
	public SearchListener(Outliner outliner) {
		this.outliner = outliner;
		
	}
	
	private void search() {
		String searchedText = outliner.searchBox.getText();
		outliner.getTree();
	    else {
	    	
	    }
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			search();
		}
			
	}
		

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}

	

}

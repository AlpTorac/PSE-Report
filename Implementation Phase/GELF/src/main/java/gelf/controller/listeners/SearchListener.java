package gelf.controller.listeners;

import gelf.model.elements.Cell;
import gelf.model.elements.InputPin;
import gelf.model.elements.Library;
import gelf.model.elements.OutputPin;
import gelf.model.project.Model;
import gelf.view.composites.Outliner;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Listens the search bar of the outliner.
 * @author Ege Uzhan
 */
public class SearchListener implements KeyListener {
	
	private Outliner outliner;
	
	/**
	 * Initializes the listener.
	 * @param outliner The outliner.
	 */
	public SearchListener(Outliner outliner) {
		this.outliner = outliner;
	}
	
	/**
	 * Traverses the tree and expands all the paths of the nodes 
	 * that have the given name.
	 * @param tree Searched tree
	 * @param node Root node for search.
	 * @param name Searched name.
	 */
	public void traverseAndExpandTree(JTree tree, DefaultMutableTreeNode node, String name) {
		for (Library library: Model.getInstance().getCurrentProject().getLibraries()) {
			if (library.getName().startsWith(name)) {
				library.setExpanded(true);
			}
			for (Cell cell: library.getCells()) {
				
				if (cell.getName().startsWith(name)) {
					library.setExpanded(true);
				}
				for (InputPin pin: cell.getInPins()) {
					if (pin.getName().startsWith(name)) {
						pin.getParent().setExpanded(true);
					}
				}
				for (OutputPin pin: cell.getOutPins()) {
					if (pin.getName().startsWith(name)) {
						pin.getParent().setExpanded(true);
					}
				}
			}	
		}
		Model.getInstance().inform();
		
					
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			String name = outliner.searchBox.getText();
			JTree tree = outliner.tree;
			DefaultMutableTreeNode root = (DefaultMutableTreeNode) tree.getModel().getRoot();
            if (name.equals("") || name.equals(" ") || name == null) {
				for (Library library: Model.getInstance().getCurrentProject().getLibraries()) {
					for (Cell cell: library.getCells()) {
						cell.setExpanded(false);
					}
					library.setExpanded(false);
				}
				Model.getInstance().inform();
				return;
			}
			traverseAndExpandTree(tree, root, name);	
	
		}
			
	}
		

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}

	

}

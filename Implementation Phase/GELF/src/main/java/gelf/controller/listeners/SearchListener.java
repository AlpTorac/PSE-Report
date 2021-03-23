package gelf.controller.listeners;

import gelf.model.elements.Element;
import gelf.view.composites.Outliner;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

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
		ArrayList<TreePath> elements = new ArrayList<TreePath>();
		if (!node.getUserObject().equals("Root") && ((Element) node.getUserObject()).getName().equals(name)) {
			elements.add(new TreePath(node.getPath()));
			node = (DefaultMutableTreeNode) node.getParent();
			if (!node.isLeaf()) {
				tree.expandPath(new TreePath(node.getPath()));
			}
			return;
		}
		for (int i = 0; i < node.getChildCount(); i++) {
			traverseAndExpandTree(tree, (DefaultMutableTreeNode)node.getChildAt(i), name);
		}
		tree.setSelectionPaths(elements.toArray(new TreePath[elements.size()]));
		
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			String name = outliner.searchBox.getText();
			JTree tree = outliner.tree;
			DefaultMutableTreeNode root = (DefaultMutableTreeNode) tree.getModel().getRoot();
            if (name.equals("") || name.equals(" ") || name == null) {
				tree.collapsePath(new TreePath(root.getPath()));
			}
			traverseAndExpandTree(tree, root, name);	
	
		}
			
	}
		

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}

	

}

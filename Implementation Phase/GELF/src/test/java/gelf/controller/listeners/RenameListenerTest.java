package gelf.controller.listeners;

import java.awt.Component;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RenameListenerTest extends ListenerTest {

	private static DefaultMutableTreeNode root;
	private static DefaultMutableTreeNode libraryNode;
	
	@BeforeAll
	private static void initTest() {
		init();
		root = (DefaultMutableTreeNode) outliner.tree.getModel().getRoot();
		libraryNode = (DefaultMutableTreeNode) root.getFirstChild();
	}
	
	JMenuItem getPopupItem() {
		JPopupMenu pm = null;
		
		for (Component c : outliner.tree.getComponents()) {
			if (c instanceof JPopupMenu) {
				pm = (JPopupMenu) c;
			}
		}
		JMenuItem it = null;
		for (Component item : pm.getComponents()) {
			if (item instanceof JMenuItem) {
				if (((JMenuItem) item).getText().equals("Rename")) {
					it = (JMenuItem) item;
				}
			}
		}
		return it;
	}
	
	@Test
	void renameTest() {
		TreePath firstElementPath = new TreePath(new DefaultMutableTreeNode[] {root, libraryNode});
		outliner.tree.getSelectionModel().setSelectionPath(firstElementPath);
		getPopupItem().doClick();
	}

}

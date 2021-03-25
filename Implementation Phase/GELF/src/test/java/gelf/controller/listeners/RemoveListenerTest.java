package gelf.controller.listeners;

import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import gelf.model.elements.Library;

class RemoveListenerTest extends ListenerTest {

	private static DefaultMutableTreeNode root;
	private static DefaultMutableTreeNode firstLibraryNode;
	private static DefaultMutableTreeNode firstElement;
	
	@BeforeAll
	private static void initTest() {
		init();
		root = (DefaultMutableTreeNode) outliner.tree.getModel().getRoot();
		firstLibraryNode = (DefaultMutableTreeNode) root.getFirstChild();
	}
	
	@Test
	void libraryRemoveTest() {
		TreePath firstElementPath = new TreePath(new DefaultMutableTreeNode[] {root, firstLibraryNode});
		outliner.tree.getSelectionModel().setSelectionPath(firstElementPath);
		outliner.itemRemove.doClick();
		ArrayList<Library> libraries = proj.getLibraries();
		libraries.add(library);
		proj.setLibraries(libraries);
		proj.inform();
		root = (DefaultMutableTreeNode) outliner.tree.getModel().getRoot();
		firstLibraryNode = (DefaultMutableTreeNode) root.getFirstChild();
	}
	
	@Test
	void cellRemoveTest() {
		firstElement = (DefaultMutableTreeNode) firstLibraryNode.getFirstChild();
		TreePath firstElementPath = new TreePath(new DefaultMutableTreeNode[] {root, firstLibraryNode, firstElement});
		outliner.tree.getSelectionModel().setSelectionPath(firstElementPath);
		outliner.itemRemove.doClick();
	}
	
	@Test
	void inputPinRemoveTest() {
		DefaultMutableTreeNode cell1Node = (DefaultMutableTreeNode) firstLibraryNode.getFirstChild();
		firstElement = (DefaultMutableTreeNode) cell1Node.getChildAt(0);
		TreePath firstElementPath = new TreePath(new DefaultMutableTreeNode[] {root, firstLibraryNode, cell1Node, firstElement});
		outliner.tree.getSelectionModel().setSelectionPath(firstElementPath);
		outliner.itemRemove.doClick();
	}
	
	@Test
	void outputPinRemoveTest() {
		DefaultMutableTreeNode cell1Node = (DefaultMutableTreeNode) firstLibraryNode.getFirstChild();
		firstElement = (DefaultMutableTreeNode) cell1Node.getChildAt(cell1Node.getChildCount() - 1);
		TreePath firstElementPath = new TreePath(new DefaultMutableTreeNode[] {root, firstLibraryNode, cell1Node, firstElement});
		outliner.tree.getSelectionModel().setSelectionPath(firstElementPath);
		outliner.itemRemove.doClick();
	}
	
	@Test
	void emptyProjectTest() {
		ArrayList<Library> libraries = proj.getLibraries();
		ArrayList<Library> emptyList = new ArrayList<Library>();
		proj.setLibraries(emptyList);
		proj.inform();
		outliner.itemRemove.doClick();
		proj.setLibraries(libraries);
		proj.inform();
	}
}

package gelf.controller.listeners;

import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import gelf.model.elements.Library;

class CompareListenerTest extends ListenerTest {
	private static DefaultMutableTreeNode root;
	private static DefaultMutableTreeNode firstLibraryNode;
	private static DefaultMutableTreeNode lastLibraryNode;
	private static DefaultMutableTreeNode firstElement;
	private static DefaultMutableTreeNode secondElement;
	
	@BeforeAll
	private static void initTest() {
		init();
		ArrayList<Library> libraries = proj.getLibraries();
		libraries.add(library);
		proj.setLibraries(libraries);
		proj.inform();
		root = (DefaultMutableTreeNode) outliner.tree.getModel().getRoot();
		firstLibraryNode = (DefaultMutableTreeNode) root.getFirstChild();
		lastLibraryNode = (DefaultMutableTreeNode) root.getLastChild();
	}
	
	@Test
	void libraryCompareTest() {
		TreePath firstElementPath = new TreePath(new DefaultMutableTreeNode[] {root, firstLibraryNode});
		TreePath lastElementPath = new TreePath(new DefaultMutableTreeNode[] {root, lastLibraryNode});
		outliner.tree.getSelectionModel().setSelectionPaths(new TreePath[] {firstElementPath, lastElementPath});
		outliner.itemCompare.doClick();
	}
	
	@Test
	void cellCompareTest() {
		firstElement = (DefaultMutableTreeNode) firstLibraryNode.getFirstChild();
		secondElement = (DefaultMutableTreeNode) firstLibraryNode.getLastChild();
		TreePath firstElementPath = new TreePath(new DefaultMutableTreeNode[] {root, firstLibraryNode, firstElement});
		TreePath lastElementPath = new TreePath(new DefaultMutableTreeNode[] {root, firstLibraryNode, secondElement});
		outliner.tree.getSelectionModel().setSelectionPaths(new TreePath[] {firstElementPath, lastElementPath});
		outliner.itemCompare.doClick();
	}
	
	@Test
	void inputPinCompareTest() {
		DefaultMutableTreeNode cell1Node = (DefaultMutableTreeNode) firstLibraryNode.getFirstChild();
		DefaultMutableTreeNode cell2Node = (DefaultMutableTreeNode) firstLibraryNode.getLastChild();
		firstElement = (DefaultMutableTreeNode) cell1Node.getChildAt(0);
		secondElement = (DefaultMutableTreeNode) cell2Node.getChildAt(0);
		TreePath firstElementPath = new TreePath(new DefaultMutableTreeNode[] {root, firstLibraryNode, cell1Node, firstElement});
		TreePath lastElementPath = new TreePath(new DefaultMutableTreeNode[] {root, firstLibraryNode, cell2Node, secondElement});
		outliner.tree.getSelectionModel().setSelectionPaths(new TreePath[] {firstElementPath, lastElementPath});
		outliner.itemCompare.doClick();
	}
	
	@Test
	void outputPinCompareTest() {
		DefaultMutableTreeNode cell1Node = (DefaultMutableTreeNode) firstLibraryNode.getFirstChild();
		DefaultMutableTreeNode cell2Node = (DefaultMutableTreeNode) firstLibraryNode.getLastChild();
		firstElement = (DefaultMutableTreeNode) cell1Node.getChildAt(cell1Node.getChildCount() - 1);
		secondElement = (DefaultMutableTreeNode) cell2Node.getChildAt(cell2Node.getChildCount() - 1);
		TreePath firstElementPath = new TreePath(new DefaultMutableTreeNode[] {root, firstLibraryNode, cell1Node, firstElement});
		TreePath lastElementPath = new TreePath(new DefaultMutableTreeNode[] {root, firstLibraryNode, cell2Node, secondElement});
		outliner.tree.getSelectionModel().setSelectionPaths(new TreePath[] {firstElementPath, lastElementPath});
		outliner.itemCompare.doClick();
	}
	
	@Test
	void emptyProjectTest() {
		ArrayList<Library> libraries = proj.getLibraries();
		ArrayList<Library> emptyList = new ArrayList<Library>();
		proj.setLibraries(emptyList);
		proj.inform();
		outliner.itemCompare.doClick();
		proj.setLibraries(libraries);
		proj.inform();
	}
	
	@Test
	void oneElementProjectTest() {
		TreePath firstElementPath = new TreePath(new DefaultMutableTreeNode[] {root, firstLibraryNode});
		outliner.tree.getSelectionModel().setSelectionPath(firstElementPath);
		outliner.itemCompare.doClick();
	}
	
	@Test
	void differentElementsTest() {
		TreePath firstElementPath = new TreePath(new DefaultMutableTreeNode[] {root, firstLibraryNode});
		DefaultMutableTreeNode cell2Node = (DefaultMutableTreeNode) firstLibraryNode.getLastChild();
		secondElement = (DefaultMutableTreeNode) cell2Node.getChildAt(0);
		TreePath lastElementPath = new TreePath(new DefaultMutableTreeNode[] {root, firstLibraryNode, cell2Node, secondElement});
		outliner.tree.getSelectionModel().setSelectionPaths(new TreePath[] {firstElementPath, lastElementPath});
		outliner.itemCompare.doClick();
	}
}

package gelf.controller.listeners;

import gelf.model.exceptions.*;
import gelf.model.project.Model;
import gelf.model.commands.TextEditCommand;
import gelf.model.elements.Cell;
import gelf.model.elements.Element;
import gelf.model.elements.Library;
import gelf.view.composites.Outliner;
import gelf.view.composites.SubWindow;
import gelf.view.composites.SubWindow.ManipulatorType;
import gelf.view.composites.SubWindowArea;
import gelf.view.composites.TextEditor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;


/**
 * Listener for the text editor.
 * @author Ege Uzhan
 */
public class EditListener implements ActionListener{
	
	private TextEditor editor;
	private Outliner outliner;
	private SubWindowArea subwindows;
	
	/**
	 * Initializes the listener.
	 * @param editor The text editor
	 * @param outliner The outliner.
	 * @param subwindows The subwindow area
	 */
	public EditListener(TextEditor editor, Outliner outliner, SubWindowArea subwindows) {
		this.editor = editor;
		this.outliner = outliner;
		this.subwindows = subwindows;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String oldContent = editor.getDocument();
		String newContent = editor.getActualText();
		ArrayList<Library> libraries = Model.getInstance().getCurrentProject().getLibraries();
		Element element = editor.getElement();
		String name = element.getName();
		JTree tree = outliner.tree;
		TreeModel treeModel = tree.getModel();
		DefaultMutableTreeNode root =(DefaultMutableTreeNode) treeModel.getRoot();
		Element newElement;
		try {
			TextEditCommand edit = new TextEditCommand(oldContent, newContent, element);
			edit.execute();
		} catch (InvalidFileFormatException exc) {
			JOptionPane.showMessageDialog(new JFrame(), exc.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			editor.revert();
			return;
		}
		editor.getSubWindow().setElement(element);
		editor.getSubWindow().setManipulatorType(ManipulatorType.TEXT_EDITOR);
		
		traverseAndExpandTree(tree, root, element);
		
	}
	
	/**
	 * Traverses the tree and expands the path of the edited node.
	 * @param tree Tree searched
	 * @param node Start node
	 * @param element Element to find
	 */
	public void traverseAndExpandTree(JTree tree, DefaultMutableTreeNode node, Element element) {
		if (node.getUserObject().equals(element) && !(element instanceof Library)) {
			node = (DefaultMutableTreeNode) node.getParent();
			tree.expandPath(new TreePath(node.getPath()));
			return;
		}
		for (int i = 0; i < node.getChildCount(); i++) {
			traverseAndExpandTree(tree, (DefaultMutableTreeNode)node.getChildAt(i), element);
		}
	}
	
}

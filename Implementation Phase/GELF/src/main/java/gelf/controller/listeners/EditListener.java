package gelf.controller.listeners;

import gelf.model.exceptions.*;
import gelf.model.project.Model;
import gelf.model.commands.TextEditCommand;
import gelf.model.elements.Cell;
import gelf.model.elements.Element;
import gelf.model.elements.Library;
import gelf.view.composites.Outliner;
import gelf.view.composites.SubWindow;
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


/*
 * Listener for the text editor.
 */
public class EditListener implements ActionListener{
	
	private TextEditor editor;
	private Outliner outliner;
	private SubWindowArea subwindows;
	
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
		
		
		//traverseAndExpandTree(tree, root, element);
		
	}
	
	/*public void traverseAndExpandTree(JTree tree, DefaultMutableTreeNode node, Element element) {
		if ((node.getUserObject())==element.toString() && !(element instanceof Library)) {
			tree.expandPath(new TreePath(node.getPath()));
			return;
		}
		for (int i = 0; i < node.getChildCount(); i++) {
			traverseAndExpandTree(tree, (DefaultMutableTreeNode)node.getChildAt(0), element);
		}
	}*/
	
}

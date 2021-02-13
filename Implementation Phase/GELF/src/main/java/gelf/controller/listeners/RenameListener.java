package gelf.controller.listeners;

import gelf.model.elements.Cell;
import gelf.model.elements.Element;
import gelf.model.elements.Library;
import gelf.model.elements.Pin;
import gelf.model.project.Model;
import gelf.model.commands.RenameCommand;
import gelf.view.composites.Outliner;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;

/*
 * Listener for renaming a liberty file.
 */
public class RenameListener implements TreeModelListener {
	
	private Outliner outliner;
	
	public RenameListener(Outliner outliner) {
		this.outliner = outliner;
	}
	

	@Override
	public void treeNodesChanged(TreeModelEvent e) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) (e.getTreePath().getLastPathComponent());
        Element element = (Element) node.getUserObject();
		String newName = node.getUserObject().toString();
		if (checkExistingName(element, newName)) {
			return;
		}
       /* try {
            int index = e.getChildIndices()[0];
           node = (DefaultMutableTreeNode) (node.getChildAt(index));
        } catch (NullPointerException exc) {
        	
        }*/
        
		RenameCommand rename = new RenameCommand(element, newName);
		rename.execute();
	}
	
	/*
	 * Checks if there is an element with the same name.
	 * @param element
	 * @param elementName
	 */
	public boolean checkExistingName(Element element, String elementName) {
		if (element instanceof Library) {
			for (Library library: Model.getInstance().getCurrentProject().getLibraries()) {
				if (library.getName().equals(elementName)) {
					JOptionPane.showMessageDialog(new JFrame(), "A library with the same name already exists.", "Error", JOptionPane.ERROR_MESSAGE);
					return true;
				}
			}
		}
		if (element instanceof Cell) {
			Cell cell = (Cell) element;
			Library parent = cell.getParentLibrary();
			for (Cell cells: parent.getCells()) {
				if (cells.getName().equals(elementName)) {
					JOptionPane.showMessageDialog(new JFrame(), "A cell with the same name already exists in the library.",
							"Error", JOptionPane.ERROR_MESSAGE);
					return true;
				}
			}
		}
		else {
			Pin pin = (Pin) element;
			Cell parent = pin.getParent();
			for (Pin pins: parent.getInPins()) {
				if (pins.getName().equals(elementName)) {
					JOptionPane.showMessageDialog(new JFrame(), "A pin with the same name already exists in the cell",
							"Error", JOptionPane.ERROR_MESSAGE);
					return true;
				}
			}
			for (Pin pins: parent.getOutPins()) {
				if (pins.getName().equals(elementName)) {
					JOptionPane.showMessageDialog(new JFrame(), "A pin with the same name already exists in the cell",
							"Error", JOptionPane.ERROR_MESSAGE);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void treeNodesInserted(TreeModelEvent e) {}

	@Override
	public void treeNodesRemoved(TreeModelEvent e) {}

	@Override
	public void treeStructureChanged(TreeModelEvent e) {}

}

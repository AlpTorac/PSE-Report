package gelf.controller.listeners;

import gelf.model.elements.Pin;
import gelf.model.project.Model;
import gelf.view.composites.Outliner;
import gelf.view.composites.SubWindow;
import gelf.view.composites.SubWindowArea;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import gelf.model.commands.DeleteCommand;
import gelf.model.elements.Cell;
import gelf.model.elements.Element;

/**
 * Listener for deleting a cell from the library permanently.
 * @author Ege Uzhan
 */
public class DeleteCellListener implements ActionListener {

	private Outliner outliner;
	private SubWindowArea subwindows;
	
	/**
	 * Initializes the listener
	 * @param outliner The outliner.
	 */
	public DeleteCellListener(Outliner outliner, SubWindowArea subwindows) {
		this.outliner = outliner;
		this.subwindows = subwindows;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (Model.getInstance().getCurrentProject().getLibraries().isEmpty()) {
			JOptionPane.showMessageDialog(new JFrame(), "No library has been loaded in the application.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (outliner.getSelectedElements().isEmpty()) {
			JOptionPane.showMessageDialog(new JFrame(), "Select at least one cell.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		ArrayList<Cell> cells = new ArrayList<Cell>();
		for (Element element : outliner.getSelectedElements()) {
			if (element instanceof Cell) {
				cells.add((Cell) element);
			}
			else {
				JOptionPane.showMessageDialog(new JFrame(), "Only cells can be deleted.", "Error", JOptionPane.ERROR_MESSAGE);
				cells.removeAll(cells);
				return;
			}
		}
		DeleteCommand delete = new DeleteCommand(cells);
		delete.execute();
		ArrayList<SubWindow> toRemove = new ArrayList<SubWindow>();
		for (SubWindow window : subwindows.subWindows) {
		    if (cells.contains(window.getElement())) {
		        toRemove.add(window);
		    }
		    else if (window.getElement() instanceof Pin && cells.contains(((Pin) window.getElement()).getParent())) {
		    	toRemove.add(window);
		    }
		    
		}
		for (SubWindow str: toRemove) {
			subwindows.removeSubWindow(str);
		}
		
	}

}

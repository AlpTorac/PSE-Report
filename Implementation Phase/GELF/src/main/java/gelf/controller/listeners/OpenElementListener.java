package gelf.controller.listeners;

import gelf.view.composites.Outliner;
import gelf.view.composites.SubWindow;
import gelf.view.composites.SubWindowArea;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;

import gelf.model.elements.Element;
import gelf.model.project.Model;

/**
 * Listener for opening an element in the working area.
 * @author Ege Uzhan
 */
public class OpenElementListener implements ActionListener, MouseListener {
	
	private Outliner outliner;
	private SubWindowArea subwindows; 
	
	/**
	 * Initializes the listener
	 * @param outliner The outliner.
	 * @param subwindows The subwindow area
	 */
	public OpenElementListener(Outliner outliner, SubWindowArea subwindows) {
		this.outliner = outliner;
		this.subwindows = subwindows;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (Model.getInstance().getCurrentProject().getLibraries().isEmpty()) {
			JOptionPane.showMessageDialog(new JFrame(), "No library has been loaded in the application.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (outliner.getSelectedElements().isEmpty() || outliner.getSelectedElements().size() != 1) {
			JOptionPane.showMessageDialog(new JFrame(), "Select only one element to open in the working area.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		Element element = outliner.getSelectedElements().get(0);
		subwindows.addSubWindow(new SubWindow(element, Model.getInstance().getCurrentProject(), outliner ,subwindows, 200, 200));
		
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                    outliner.tree.getLastSelectedPathComponent();
             if (node == null || node.getUserObject().equals("Root")) {
            	 return;
             }
			Element element = (Element) node.getUserObject();
			subwindows.addSubWindow(new SubWindow(element, Model.getInstance().getCurrentProject(), outliner,  subwindows, 200, 200));			
			
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}

	
}

package gelf.controller.listeners;

import gelf.view.composites.Outliner;
import gelf.view.composites.SubWindow;
import gelf.view.composites.SubWindowArea;
import gelf.view.composites.Visualizer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import gelf.model.elements.Element;
import gelf.model.project.Model;

/*
 * Listener for opening an element in the working area.
 */
public class OpenElementListener implements ActionListener, MouseListener {
	
	private Outliner outliner;
	private SubWindowArea subwindows; 
	
	public OpenElementListener(Outliner outliner, SubWindowArea subwindows) {
		this.outliner = outliner;
		this.subwindows = subwindows;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (outliner.getSelectedElements().size() != 1) {
			JOptionPane.showMessageDialog(new JFrame(), "Select one element to open in the working area.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		Element element = outliner.getSelectedElements().get(0);
		subwindows.addSubWindow(new SubWindow(element, Model.getInstance().getCurrentProject() ,subwindows, 100, 100));
		
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                    outliner.tree.getLastSelectedPathComponent();
             if (node == null) {
            	 return;
             }
			Element element = (Element) node.getUserObject();
			subwindows.addSubWindow(new SubWindow(element, Model.getInstance().getCurrentProject(), subwindows, 100, 100));			
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

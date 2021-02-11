package gelf.controller.listeners;

import gelf.view.composites.Outliner;
import gelf.view.composites.SubWindow;
import gelf.view.composites.SubWindowArea;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import gelf.model.elements.Element;

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
		if (outliner.getSelectedElements().size() >= 3) {
			//TODO error
			return;
		}
		for (Element element: outliner.getSelectedElements()) {
			subwindows.addSubWindow(new SubWindow(element));
		}
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) {
			Element element = outliner.getSelectedElements();
		    subwindows.add(new SubWindow(element));
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
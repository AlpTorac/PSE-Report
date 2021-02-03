package gelf.controller.listeners;

import gelf.view.composites.Outliner;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import gelf.model.elements.Element;

/*
 * Listener for opening an element in the working area.
 */
public class OpenElementListener implements ActionListener, MouseListener {
	
	public Outliner outliner;
	
	public OpenElementListener(Outliner outliner) {
		this.outliner = outliner;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Element element = outliner.getSelectedElements();
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) {
			Element element = outliner.getSelectedElements();
		    Subwindow(element);
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
}

package gelf.controller.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import gelf.model.elements.Element;
import gelf.model.elements.Library;
import gelf.view.composites.Outliner;

public class CompareListener implements ActionListener {

	private Outliner outliner;
	
	public CompareListener(Outliner outliner) {
		this.outliner = outliner;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (outliner.getSelectedElements().size() <= 1) {
			JOptionPane.showMessageDialog(new JFrame(), "Select at least 2 elements to compare.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		ArrayList<Element> elements;
		for (Element element: outliner.getSelectedElements()) {
			if (element instanceof Library) {
				
			}
		}
		
	}

}

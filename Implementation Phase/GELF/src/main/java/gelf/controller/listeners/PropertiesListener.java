package gelf.controller.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gelf.model.elements.Library;
import gelf.view.composites.Outliner;

public class PropertiesListener implements ActionListener {
	
	private Outliner outliner;
	
	public PropertiesListener(Outliner outliner) {
		this.outliner = outliner;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!outliner.getSelectedElements().isEmpty() && outliner.getSelectedElements().size() == 1) {
			if (outliner.getSelectedElements().get(0) instanceof Library) {
				((Library) outliner.getSelectedElements().get(0)).showProperties();;
			}
		}
		
	}
	
	
}

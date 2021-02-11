package gelf.controller.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gelf.model.elements.Element;

/*
 * Listener for saving changes.
 */
public class SaveListener implements ActionListener {
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		Element element = Model.getOpenedInText();
		element.save();
		
	}

}

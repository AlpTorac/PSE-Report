package gelf.controller.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gelf.model.elements.Element;

/*
 * Listener for saving the library as a new file.
 */
public class SaveAsListener implements ActionListener {
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		Element element = Model.getOpenedInText();
		element.saveAs();
	}

}

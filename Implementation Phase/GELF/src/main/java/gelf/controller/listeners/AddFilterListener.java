package gelf.controller.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import gelf.view.*;

/*
 * Listener for adding a filter.
 */
public class AddFilterListener implements ActionListener {
	
	private Panel panel;

	public AddFilterListener(Panel panel) {
		this.panel = panel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		AddFilterCommand addFilter = new AddFilterCommand();
		addFilter.execute();
	}

}

package gelf.controller.listeners;

import gelf.view.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * Listener for removing a filter.
 */
public class RemoveFilterListener implements ActionListener {

	private Panel panel;
	
	public RemoveFilterListener(Panel panel) {
		this.panel = panel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		RemoveFilterCommand removeFilter = new RemoveFilterCommand();
		removeFilter.execute();
	}

}

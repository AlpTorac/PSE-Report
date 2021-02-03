package gelf.controller.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;

/*
 * Listener for scaling values of certain attributes.
 */
public class ScaleListener implements ActionListener, TextListener{
	
	private Panel panel;
	
	public ScaleListener(Panel panel) {
		this.panel = panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void textValueChanged(TextEvent e) {
		// TODO Auto-generated method stub
		
	}
}

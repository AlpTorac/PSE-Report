package gelf.controller.listeners;

import java.awt.TextComponent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;

import gelf.model.commands.ScaleCommand;
import gelf.view.components.Panel;

/*
 * Listener for scaling values of certain attributes.
 */
public class ScaleListener implements ActionListener, TextListener{
	
	private Panel panel;
	float scale;
	
	public ScaleListener(Panel panel) {
		this.panel = panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ScaleCommand scale = new ScaleCommand(panel.toScale(), scale);
		scale.execute();
	}

	@Override
	public void textValueChanged(TextEvent e) {
		TextComponent tc = (TextComponent)e.getSource();
	    scale = Float.parseFloat(tc.getText());
	}
}

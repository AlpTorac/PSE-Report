package gelf.controller.listeners;

import gelf.model.commands.ScaleCommand;
import gelf.model.elements.attributes.Attribute;
import gelf.view.components.Panel;

import java.awt.TextComponent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;



/*
 * Listener for scaling values of certain attributes.
 */
public class ScaleListener implements ActionListener {
	
	private Panel panel;
	
	public ScaleListener(Panel panel) {
	
		this.panel = panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		try {
			String scaleValue = JOptionPane.showInputDialog(panel,"Enter your message","Messages",JOptionPane.OK_CANCEL_OPTION);
				
			float value = Float.parseFloat(scaleValue);
			Attribute attribute = panel.combobox.getSelected();
			ScaleCommand scale = new ScaleCommand(attribute, value);
			scale.execute();
		} catch (NumberFormatException exc) {
			JOptionPane.showMessageDialog(new JFrame(), "Please enter a float value.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
	
	}
		
		
	

}

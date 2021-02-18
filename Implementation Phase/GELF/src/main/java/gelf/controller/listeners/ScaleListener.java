package gelf.controller.listeners;

import gelf.model.commands.ScaleCommand;
import gelf.model.elements.attributes.Attribute;
import gelf.view.components.Panel;
import gelf.view.composites.Visualizer;

import java.awt.TextComponent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;



/**
 * Listener for scaling values of certain attributes.
 * @author Ege Uzhan
 */
public class ScaleListener implements ActionListener {
	
	private Visualizer panel;
	
	
	/**
	 * Initializes the listener
	 * @param panel Visualizer which the button listened belongs to.
	 */
	public ScaleListener(Visualizer panel) {
	
		this.panel = panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Attribute attribute;
		String scaleValue = JOptionPane.showInputDialog(new JFrame(),"Enter the scale value.", "Scale", JOptionPane.OK_CANCEL_OPTION);
		
		try {
			float value = Float.parseFloat(scaleValue);
			ScaleCommand scale = new ScaleCommand(attribute, value);
			scale.execute();
		} catch (NumberFormatException exc) {
			JOptionPane.showMessageDialog(new JFrame(), "Please enter a float value.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
	
	}
		
		
	

}

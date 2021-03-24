package gelf.controller.listeners;

import gelf.model.commands.ScaleCommand;
import gelf.view.composites.Visualizer;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

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
		String scaleValue = JOptionPane.showInputDialog(new JFrame(),"Enter the scale value.", "Scale", JOptionPane.OK_OPTION);
		if (scaleValue == null) {
			JOptionPane.showMessageDialog(new JFrame(), "Cancelled", "Scale", JOptionPane.ERROR_MESSAGE);
			return;
		}
		try {
			float value = Float.parseFloat(scaleValue);
			ScaleCommand scale = new ScaleCommand(value, panel.getElement(), panel.attribute.toString(), 
					panel.selectedInPow, panel.selectedOutPow, panel.selectedTim);
			scale.execute();
		} catch (NumberFormatException exc) {
			JOptionPane.showMessageDialog(new JFrame(), "Please enter a float value.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}	
	}
		
		
	

}

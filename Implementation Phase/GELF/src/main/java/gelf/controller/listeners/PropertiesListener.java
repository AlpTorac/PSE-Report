package gelf.controller.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

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
				if (!((Library) outliner.getSelectedElements().get(0)).getInnerPath().contains(".")) {
					JOptionPane.showMessageDialog(new JFrame(), "The library does not exist as a file. Please use the 'Save As...'  option first", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				((Library) outliner.getSelectedElements().get(0)).showProperties();;
			}
			else {
				JOptionPane.showMessageDialog(new JFrame(), "Only library properties can be shown.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		else {
			JOptionPane.showMessageDialog(new JFrame(), "Please select only one library.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

	}


}

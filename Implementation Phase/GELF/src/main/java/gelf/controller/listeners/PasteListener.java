package gelf.controller.listeners;

import gelf.model.commands.PasteCommand;
import gelf.model.elements.Library;
import gelf.model.project.Model;
import gelf.view.composites.Outliner;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Listener for pasting action.
 * @author Ege Uzhan
 */
public class PasteListener implements ActionListener {

	private Outliner outliner;
	
	/**
	 * Initializes the listener
	 * @param outliner The outliner.
	 */
	public PasteListener(Outliner outliner) {
		this.outliner = outliner;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (Model.getInstance().getCurrentProject().getLibraries().isEmpty()) {
			JOptionPane.showMessageDialog(new JFrame(), "No library has been loaded in the application.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (outliner.getSelectedElements().isEmpty() || outliner.getSelectedElements().size() != 1) {
			JOptionPane.showMessageDialog(new JFrame(), "Select one library for pasting.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (outliner.getSelectedElements().get(0) instanceof Library) {
			Library destinationLibrary = (Library) outliner.getSelectedElements().get(0);
			PasteCommand paste = new PasteCommand(destinationLibrary);
			paste.execute();
		}
		else {
			JOptionPane.showMessageDialog(new JFrame(), "Paste can only be performed on libraries.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
	}

}

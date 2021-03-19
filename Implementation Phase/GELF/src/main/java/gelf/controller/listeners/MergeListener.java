package gelf.controller.listeners;

import gelf.model.commands.MergeCommand;
import gelf.model.elements.Element;
import gelf.model.elements.Library;
import gelf.model.project.Model;
import gelf.view.composites.Outliner;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Listener for merging libraries.
 * @author Ege Uzhan
 */
public class MergeListener implements ActionListener {

	private Outliner outliner;
	
	/**
	 * Initializes the listener
	 * @param outliner The outliner.
	 */
	public MergeListener(Outliner outliner) {
		this.outliner = outliner;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (Model.getInstance().getCurrentProject().getLibraries().isEmpty()) {
			JOptionPane.showMessageDialog(new JFrame(), "No library has been loaded in the application.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (outliner.getSelectedElements().isEmpty() || outliner.getSelectedElements().size() <= 1) {
			JOptionPane.showMessageDialog(new JFrame(), "Select at least 2 libraries.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		ArrayList<Library> libraries = new ArrayList<Library>();
		for (Element element: outliner.getSelectedElements()) {
			if (element instanceof Library) {
				libraries.add((Library) element);
			}
			else {
				JOptionPane.showMessageDialog(new JFrame(), "Only libraries can be merged.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		String name = JOptionPane.showInputDialog(new JFrame(), "Please enter a name for the new library", "Merge", JOptionPane.OK_CANCEL_OPTION);
		if (name == null || name == "") {
			JOptionPane.showMessageDialog(new JFrame(), "Merging cancelled", "Alert", JOptionPane.WARNING_MESSAGE);
			return;
		}
		MergeCommand merge = new MergeCommand(name, libraries);
		merge.execute();
	}

}

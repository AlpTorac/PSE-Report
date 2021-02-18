package gelf.controller.listeners;

import gelf.model.elements.Element;
import gelf.model.elements.Library;
import gelf.model.project.Model;
import gelf.model.project.Project;
import gelf.view.composites.Outliner;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Listener for removing an element from the project.
 * @author Ege Uzhan
 */
public class RemoveListener implements ActionListener {

	private Outliner outliner;
	
	/**
	 * Initializes the listener
	 * @author Ege Uzhan The outliner.
	 */
	public RemoveListener(Outliner outliner) {
		this.outliner = outliner;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (outliner.getSelectedElements().isEmpty()) {
			JOptionPane.showMessageDialog(new JFrame(), "Select at least one library.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		ArrayList<Library> libraries = new ArrayList<Library>();
		for (Element element: outliner.getSelectedElements()) {
			if (element instanceof Library) {
				libraries.add((Library) element);
			}
			else {
				JOptionPane.showMessageDialog(new JFrame(), "Only libraries can be removed.", "Error", JOptionPane.ERROR_MESSAGE);
				libraries.removeAll(libraries);
				return;
			}
		}
		Model currentModel = Model.getInstance();
		Project currentProject = currentModel.getCurrentProject();
		ArrayList<Library> newLibraries = new ArrayList<Library>();
		for (Library library: currentProject.getLibraries()) {
			if (!libraries.contains(library)) {
				newLibraries.add(library);
			}
		}
		currentProject.setLibraries(newLibraries);
		currentProject.inform();
	}

}

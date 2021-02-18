package gelf.controller.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashSet;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import gelf.model.elements.Cell;
import gelf.model.elements.Element;
import gelf.model.elements.Library;
import gelf.model.elements.Pin;
import gelf.model.project.Model;
import gelf.model.project.Project;

/**
 * Listener for the save all button.
 * @author Ege Uzhan
 *
 */
public class SaveAllListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (Model.getInstance().getCurrentProject().getLibraries().isEmpty()) {
			JOptionPane.showMessageDialog(new JFrame(), "No library has been loaded in the application.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		Model currentModel = Model.getInstance();
		Project project = currentModel.getCurrentProject();
		HashSet<Library> libraries = new HashSet<Library>();
		for (Element element: project.getOpenedInTextElements()) {
			if (element instanceof Pin) {
				libraries.add(((Pin) element).getParent().getParentLibrary());
			}
			else if (element instanceof Cell) {
				libraries.add(((Cell) element).getParentLibrary());
			}
			else {
				libraries.add((Library) element);
			}
		}
		
		for (Library library: libraries) {
			try {
				library.saveLibrary();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(new JFrame(), e.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
	}

}

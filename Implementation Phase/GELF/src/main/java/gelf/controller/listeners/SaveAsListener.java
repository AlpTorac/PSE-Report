package gelf.controller.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;

import gelf.model.elements.Cell;
import gelf.model.elements.Element;
import gelf.model.elements.Library;
import gelf.model.elements.Pin;
import gelf.model.project.Model;
import gelf.model.project.Project;

/*
 * Listener for saving the library as a new file.
 */
public class SaveAsListener implements ActionListener {
	
	@Override
	public void actionPerformed(ActionEvent e) {
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
			//TODO saveas
		}
	}

}

package gelf.controller.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import gelf.model.project.Model;
import gelf.model.project.Project;

/**
 * Listener for importing an existing project.
 * @author Ege Uzhan
 */
public class LoadProjectListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		Model currentModel = Model.getInstance();
		currentModel.loadProject();
	}

}

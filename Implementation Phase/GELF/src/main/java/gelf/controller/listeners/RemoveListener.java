package gelf.controller.listeners;

import gelf.model.commands.RemoveCommand;
import gelf.model.elements.Element;
import gelf.model.elements.Library;
import gelf.view.composites.Outliner;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/*
 * Listener for removing an element from the project.
 */
public class RemoveListener implements ActionListener {

	private Outliner outliner;
	
	public RemoveListener(Outliner outliner) {
		this.outliner = outliner;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
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
		RemoveCommand remove = new RemoveCommand(libraries);
		remove.execute();
	}

}

package gelf.controller.listeners;

import gelf.model.commands.MergeCommand;
import gelf.model.elements.Element;
import gelf.model.elements.Library;
import gelf.view.composites.Outliner;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/*
 * Listener for merging libraries.
 */
public class MergeListener implements ActionListener {

	private Outliner outliner;
	
	public MergeListener(Outliner outliner) {
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
				JOptionPane.showMessageDialog(new JFrame(), "Only libraries can be merged.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		MergeCommand merge = new MergeCommand(null, libraries);
		merge.execute();
	}

}

package gelf.controller.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * Listener for moving a cell to another library.
 */
public class MoveListener implements ActionListener {

	private Outliner outliner;
	
	public MoveListener(Outliner outliner) {
		this.outliner = outliner;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		MoveCommand move = new MoveCommand();
		move.execute();
	}

}

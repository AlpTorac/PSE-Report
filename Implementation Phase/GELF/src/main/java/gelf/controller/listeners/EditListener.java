package gelf.controller.listeners;

import gelf.view.composites.TextEditor;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/*
 * Listener for the text editor.
 */
public class EditListener implements DocumentListener {
	
	private TextEditor editor;
	
	public EditListener(TextEditor editor) {
		this.editor = editor;
	}

	@Override
	public void changedUpdate(DocumentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertUpdate(DocumentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeUpdate(DocumentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}

package gelf.controller.listeners;

import gelf.model.commands.TextEditCommand;
import gelf.view.composites.TextEditor;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/*
 * Listener for the text editor.
 */
public class EditListener {
	
	private TextEditor editor;
	
	public EditListener(TextEditor editor) {
		this.editor = editor;
	}

	public void insertUpdate(DocumentEvent arg0) {
		String oldContent = editor.getDocument();
		String newContent ; 
		//TODO evaluate changes
		TextEditCommand edit = new TextEditCommand(oldContent, newstring, editor.getElement());
		edit.execute();
	}

	

}

package gelf.controller.listeners;

import gelf.model.exceptions.*;
import gelf.model.commands.TextEditCommand;
import gelf.model.elements.Element;
import gelf.view.composites.TextEditor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


/*
 * Listener for the text editor.
 */
public class EditListener implements ActionListener{
	
	private TextEditor editor;
	
	public EditListener(TextEditor editor) {
		this.editor = editor;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String oldContent = editor.getDocument();
		String newContent = editor.getActualText();
		Element element = editor.getElement();
		try {
			TextEditCommand edit = new TextEditCommand(oldContent, newContent, element);
			edit.execute();
		} catch (InvalidFileFormatException exc) {
			//TODO error
		}
	}
	
}

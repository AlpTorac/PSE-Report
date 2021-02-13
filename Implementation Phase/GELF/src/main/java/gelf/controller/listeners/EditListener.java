package gelf.controller.listeners;

import gelf.model.exceptions.*;
import gelf.model.commands.TextEditCommand;
import gelf.model.elements.Element;
import gelf.view.composites.TextEditor;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


/*
 * Listener for the text editor.
 */
public class EditListener implements KeyListener{
	
	private TextEditor editor;
	
	public EditListener(TextEditor editor) {
		this.editor = editor;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			String oldContent = editor.getDocument();
			String newContent = editor.getActualText();
			Element element = editor.getElement();
			//TODO evaluate changes
			try {
				TextEditCommand edit = new TextEditCommand(oldContent, newContent, element);
				edit.execute();
			} catch (InvalidFileFormatException exc) {
				//TODO error
			}
			
			
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}
}

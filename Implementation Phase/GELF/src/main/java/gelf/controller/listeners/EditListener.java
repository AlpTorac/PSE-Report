package gelf.controller.listeners;

import gelf.model.exceptions.*;
import gelf.model.commands.TextEditCommand;
import gelf.model.elements.Element;
import gelf.view.composites.TextEditor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


/**
 * Listener for the text editor.
 * @author Ege Uzhan
 */
public class EditListener implements ActionListener{
	
	private TextEditor editor;
	
	/**
	 * Initializes the listener.
	 * @param editor The text editor
	 * @param outliner The outliner.
	 * @param subwindows The subwindow area
	 */
	public EditListener(TextEditor editor) {
		this.editor = editor;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String newContent = editor.getActualText();
		Element element = editor.getElement();
		try {
			TextEditCommand edit = new TextEditCommand(newContent, element);
			edit.execute();
		} catch (InvalidFileFormatException exc) {
			JOptionPane.showMessageDialog(new JFrame(), exc.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			editor.revert();
			return;
		}
		
	}
	
}

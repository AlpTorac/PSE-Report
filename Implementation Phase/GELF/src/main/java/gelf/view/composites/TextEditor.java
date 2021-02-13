package gelf.view.composites;

import gelf.controller.listeners.EditListener;
import gelf.controller.listeners.SearchListener;
import gelf.model.compilers.LibertyCompiler;
import gelf.model.elements.Cell;
import gelf.model.elements.Element;
import gelf.model.elements.InputPin;
import gelf.model.elements.Library;
import gelf.model.elements.OutputPin;
import gelf.model.elements.Pin;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;



/**
 * Displays the liberty text file.
 */
public class TextEditor extends ElementManipulator implements KeyListener{
	
	private JTextField searchBox;
	private JTextArea textArea;
	private JScrollPane scrollPane;
	
	private int trace;
	
	private Element element;
	private String document;
	
	private final static Color HL_COLOR = Color.LIGHT_GRAY;
	private final Highlighter hl;
	private final Highlighter.HighlightPainter painter;
	
	/*
	 * Constructor
	 */
    public TextEditor(Element element, int width, int height){
        super(element, width, height);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    	this.setBorder(new LineBorder(Color.BLACK, 4, true));
    	hl = new DefaultHighlighter();
        painter = new DefaultHighlighter.DefaultHighlightPainter(HL_COLOR);
    	setup();
    }
    
    /*
     * Sets up the panel.
     */
    private void setup() {
    	trace = 0;
    	searchBox = new JTextField();
    	searchBox.setBackground(new Color(0.3f, 0.3f, 0.3f));
    	searchBox.setForeground(Color.WHITE);
    	searchBox.setBorder(new LineBorder(Color.BLACK, 5, true));
    	searchBox.setSize(this.getWidth(), 120);
    	
    	textArea = new JTextArea(300,300);
    	textArea.setEditable(true);
    	textArea.setBackground(new Color(0.2f, 0.2f, 0.2f));
    	textArea.setForeground(Color.WHITE);
    	
    	scrollPane = new JScrollPane(textArea, 
    			 ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    	
    	this.add(searchBox);
    	this.add(scrollPane);
    	this.setVisible(true);
    	textArea.setHighlighter(hl);
    	searchBox.addKeyListener(this);
    	
    }
    
    /*
     * Sets the element shown in the editor.
     */
   
    public void setElement(Element element) {
    	this.element = element;
    	this.textArea.setText("");
    
		textArea.setText(createText(element));
		textArea.addKeyListener(new EditListener(this));;
		document = textArea.getText();
	
    }
    
    /*
     * Reverts the changes after a wrong input.
     */
    public void revert() {
    	textArea.setText("");
    	textArea.setText(document);
    }
    
    /*
     * Saves the changes after save button is clicked.
     */
    public void saveChanges(String newText) {
        this.document = newText;
    	textArea.setText("");
    	textArea.setText(document);
    }
    
    /*
     * Creates the text shown through the LibertyCompiler.
     */
    private String createText(Element element) {
    	if (element instanceof Library) {
    		return LibertyCompiler.compile((Library) element);
    	}
    	else if (element instanceof Cell) {
    		return LibertyCompiler.compile((Cell) element);
    	}
    	else if (element instanceof InputPin) {
    		return LibertyCompiler.compile((InputPin) element);
    	}
    	else {
    		return LibertyCompiler.compile((OutputPin) element);
    	}
    }
    
    
    /*
     * Highlights the searched string on the text field.
     * @param start Starting index of the text.
     */
    private int highlight(int start) {
    	hl.removeAllHighlights();
    	
		String s = searchBox.getText();
		String content = textArea.getText();
		int index = content.indexOf(s, start);
		int end = 0;
		if (index >= 0 && index < content.length()) {  
       
            end = index + s.length();
            try {
				hl.addHighlight(index, end, painter);
				
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
            textArea.setCaretPosition(end);
            searchBox.setBackground(new Color(0.3f, 0.3f, 0.3f));
            return end;
		}
		return -1;
    }
    
    /*
     * Returns the document without the changes made in the editor.
     * @return Document text
     */
    public String getDocument() {
    	return document;
    }
    
    /*
     * Returns the element opened in the editor.
     * @return Element
     */
    public Element getElement() {
    	return element;
    }
    
    /*
     * Returns the (changed) text.
     */
    public String getActualText() {
    	return textArea.getText();
    }

	@Override
	public void keyPressed(KeyEvent arg0) {
        if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
			trace = highlight(trace);
			if (trace == -1) {
				trace = 0;
				highlight(0);
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}
    
}
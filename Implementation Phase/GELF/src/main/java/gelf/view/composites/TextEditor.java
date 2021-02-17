package gelf.view.composites;

import gelf.controller.listeners.EditListener;
import gelf.model.compilers.LibertyCompiler;
import gelf.model.elements.Cell;
import gelf.model.elements.Element;
import gelf.model.elements.InputPin;
import gelf.model.elements.Library;
import gelf.model.elements.OutputPin;
import gelf.model.project.Project;
import gelf.view.components.Button;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;
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
	private JPanel lowerPanel;
	private Button updateButton;
	private Outliner outliner;
	private SubWindowArea subwindows;
	
	private int trace;
	
	private Element element;
	private String document;
	
	private final static Color HL_COLOR = Color.LIGHT_GRAY;
	private final Highlighter hl;
	private final Highlighter.HighlightPainter painter;
	
	private final static Color HOVER_COLOR = Color.GRAY;
	private final Highlighter hoverhl;
	private final Highlighter.HighlightPainter hoverPainter;
	
	/*
	 * Constructor
	 */
    public TextEditor(Element element, Project p, Outliner o, SubWindowArea subwindows, int width, int height){
        super(element, p, width, height);
        hl = new DefaultHighlighter();
        this.subwindows = subwindows;
        this.outliner = o;
        painter = new DefaultHighlighter.DefaultHighlightPainter(HL_COLOR);
        hoverhl = new DefaultHighlighter();
        hoverPainter = new DefaultHighlighter.DefaultHighlightPainter(HOVER_COLOR);
        setup();
        this.setElement(element);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    	this.setBorder(new LineBorder(Color.BLACK, 4, true));
    	    	
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
    	textArea.setTabSize(3);
    	scrollPane = new JScrollPane(textArea, 
    			 ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    	
    	lowerPanel = new JPanel();
    	lowerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    	lowerPanel.setBackground(new Color(0.2f, 0.2f, 0.2f));
    	updateButton = new Button("Update");
    	
    	this.add(searchBox);
    	this.add(scrollPane);
    	this.setVisible(true);
    	textArea.setHighlighter(hl);
    	searchBox.addKeyListener(this);
    	this.add(lowerPanel);
    	lowerPanel.add(updateButton);
    	updateButton.addActionListener(new EditListener(this, outliner, subwindows));
    }
    
    /*
     * Sets the element shown in the editor.
     */
   
    public void setElement(Element element) {
    	this.element = element;
    	this.textArea.setText("");
    
		textArea.setText(createText(element));
		textArea.setCaretPosition(0);
		document = textArea.getText();
	
    }
    
    /*
     * Highlights the given value
     * @param value Value to be highlighted.
     */
    public void highlightText(int start) {
    	
    	if (searchBox.getText().isEmpty()) {
    		removeHighlights();
    		return;
    	}
    	
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
            
            searchBox.setBackground(new Color(0.3f, 0.3f, 0.3f));
            if (end < content.length()) {
            	content = content.substring(end, content.length());
            	highlightText(end);
            }
        }
    }
    
    /*
     * Removes all highlights from the text.
     */
    public void removeHighlights() {
    	hl.removeAllHighlights();
    }
    
    /*
     * 
     */
    public void addHoverHighlights(int value) {
    	highlightText(0);
    	jumpToNext(0);
    	
    }
    
    /*
     *
     */
    public void removeHoverHighlights() {
    	hoverhl.removeAllHighlights();
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
    private int jumpToNext(int start) {
    	
    	if (searchBox.getText().equals("")) {
       		return -1;
    	}
		String s = searchBox.getText();
		String content = textArea.getText();
		int index = content.indexOf(s, start);
		int end = 0;
		if (index >= 0 && index < content.length()) {  
       
            end = index + s.length();
            textArea.setCaretPosition(end);
            
            searchBox.setBackground(new Color(0.3f, 0.3f, 0.3f));
            if (index < content.length()) {
            	content = content.substring(index, content.length());
            }
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
     * Sets the document.
     * @param newDocument New text.
     */
    public void setDocument(Element element) {
    	this.textArea.setText(createText(element));
    	this.document = createText(element);
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
        	if (searchBox.getText() == "") {
        		removeHighlights();
        		return;
        	}
        	removeHighlights();
        	highlightText(0);
			if (trace == -1) {
				trace = 0;
				jumpToNext(0);
			}
			trace = jumpToNext(trace);
			
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}
    
}

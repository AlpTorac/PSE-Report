package gelf.view.composites;

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

import gelf.controller.listeners.EditListener;
import gelf.controller.listeners.SearchListener;
import gelf.model.elements.Cell;
import gelf.model.elements.Element;
import gelf.model.elements.Library;
import gelf.model.elements.Pin;

/**
 * Displays the liberty text file.
 */
public class TextEditor extends ElementManipulator implements KeyListener{
	
	private JTextField searchBox;
	private JTextArea textArea;
	private JScrollPane scrollPane;
	
	private FileReader fr;
	private int trace;
	
	private Element element;
	private String path;
	private String document;
	
	private final static Color HL_COLOR = Color.LIGHT_GRAY;
	private final Highlighter hl;
	private final Highlighter.HighlightPainter painter;
	
	/*
	 * Constructor
	 */
    public TextEditor(int width, int height){
        super(width, height);
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
    	//searchBox.setBorder();
    	
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
     * 
     */
    @Override
    public void setElement(Element element) {
    	this.element = element;
    	this.textArea.setText("");
    	path = null;
    	
    	if (element instanceof Library) {
    		Library library = (Library) element;
    		path = library.getPath();
    		
    	}
    	else if (element instanceof Cell) {
    		Cell cell = (Cell) element;
    		path = cell.getParentLibrary().getPath();
    		
    		
    	}
    	if (element instanceof Pin) {
    		Pin pin = (Pin) element;
    		path = pin.getParent().getParentLibrary().getPath();
    		
    	}
    	
    	try {
			fr = new FileReader(path);
			BufferedReader reader = new BufferedReader(fr);
			textArea.read(reader, "x");
			textArea.getDocument().addDocumentListener(new EditListener(this));
			document = textArea.getText();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	jumpInText();
    	
    }
    
    /*
     * Reverts the changes after a wrong input.
     */
    public void revert() {
    	textArea.setText("");
    	textArea.setText(document);
    }
    
    /*
     * 
     */
    public void saveChanges(String newText) {
    	this.document = newText;
    	textArea.setText("");
    	textArea.setText(document);
    }
    
    /*
     * 
     */
    private void jumpInText() {
    	String content = textArea.getText();
    	int index = content.indexOf(element.getName());
    	if (index >= 0 && index < content.length()) {  
    	    int end = index + element.getName().length();
            try {
				hl.addHighlight(index, end , painter);
				
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
            textArea.setCaretPosition(end);
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
     * Returns the old document.
     * @return Document text
     */
    public String getDocument() {
    	return document;
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
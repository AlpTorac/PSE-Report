package gelf.view.representation;

import java.awt.Label;

import javax.swing.JLabel;

import gelf.model.elements.*;

/*
 * Displays data about opened element.
 */
public class DataPanel extends Panel {
	
	private Label label;
	private String text;
	private Element element;
	
	
	/*
	 * Constructor for opening a cell.
	 * @param cell Cell element.
	 */
	public DataPanel(Cell cell) {
		label = new Label();
		this.element = cell;
		text = cell.getName();
		label.setText(text);
		this.setVisible(true);
	}
	
	/*
	 * Constructor for opening a pin.
	 * @param  pin Pin element.
	 */
	public DataPanel(Pin pin) {
		label = new Label();
		this.element = pin;
		text = pin.getName();
		label.setText(text);
		this.setVisible(true);
	}
	
	/*
	 * Constructor for opening a library.
	 * @param library Library element.
	 */
	public DataPanel(Library library) {
		label = new Label();
		this.element = library;
		text = library.getName();
		label.setText(text);
		this.setVisible(true);
	}
	
	/*
	 * Changes the text displayed.
	 * @param text New text to display.
	 */
	public void setText(String text) {
		this.text = text;
	}
	
	/*
	 * Sets the element selected for the panel.
	 * @param element Selected element.
	 */
	public void setElement(Element element) {
		this.element = element;
	}

}

package gelf.view.representation;

import java.awt.Label;
import java.util.ArrayList;

import javax.swing.JLabel;

import gelf.model.elements.*;

/*
 * Displays data about opened element.
 */
public class DataPanel extends Panel implements Resizable{
	
	private Label upperLabel;
	private Label middleLabel;
	private Label lowerLabel;
	private String text;
	private Element element;
	
	/*
	 * Constructor for opening a cell.
	 * @param cell Cell element.
	 */
	public DataPanel(Cell cell) {
		upperLabel = new JLabel();
		middleLabel = new JLabel();
		lowerLabel = new JLabel();
		
		upperLabel.setText(text);
		middleLabel.setText(text);
		lowerLabel.setText(text);
		
		this.setVisible(true);
	}
	
	/*
	 * Constructor for opening a pin.
	 * @param  pin Pin element.
	 */
	public DataPanel(Pin pin) {
		upperLabel = new JLabel();
		middleLabel = new JLabel();
		lowerLabel = new JLabel();
		
		upperLabel.setText(text);
		middleLabel.setText(text);
		lowerLabel.setText(text);
		
		this.setVisible(true);	
		}
	
	/*
	 * Constructor for opening a library.
	 * @param library Library element.
	 */
	public DataPanel(Library library) {
		upperLabel = new JLabel();
		middleLabel = new JLabel();
		lowerLabel = new JLabel();
		
		upperLabel.setText(text);
		middleLabel.setText(text);
		lowerLabel.setText(text);
		
		this.setVisible(true);
	}
	
	/*
	 * Changes the text displayed.
	 * @param text New text to display.
	 */
	public void setText(ArrayList<Pin> selectedPins) {

		upperLabel.setText(text);
		middleLabel.setText(text);
		lowerLabel.setText(text);
	}
}

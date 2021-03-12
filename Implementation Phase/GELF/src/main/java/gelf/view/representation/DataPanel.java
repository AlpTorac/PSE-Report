package gelf.view.representation;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import gelf.model.elements.*;
import gelf.view.components.Label;
import gelf.view.components.Panel;

/**
 * Displays data about opened element.
 * @author Ege Uzhan
 */
public class DataPanel extends Panel {

    public Label upperLabel;
    private Label middleLabel;
    private Label lowerLabel;
    private Label when;
	private Element element;
	private String selectedText;
	private String binary;
	
	/**
	 * Constructor for opening an element in the data panel.
	 * @param element Library element.
	 */
	public DataPanel(int width, int height, Element element) {
		super(width, height);
		//this.setPreferredSize(new Dimension(300, 200));
		this.setBackground(new Color(0.3f, 0.3f, 0.3f));
		this.element = element;
		upperLabel = new Label();
		when = new Label();
		middleLabel = new Label();
		lowerLabel = new Label();
		this.setBorder(new LineBorder(Color.WHITE));
		upperLabel.setForeground(Color.WHITE);
		when.setForeground(Color.WHITE);
		middleLabel.setForeground(Color.WHITE);
		lowerLabel.setForeground(Color.WHITE);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(upperLabel);
		if (element instanceof Cell) {
			this.add(when);
		}
		this.add(middleLabel);
		this.add(lowerLabel);
		setText(element);	
		this.setVisible(true);
	}
	
	
	/**
	 * Changes the text displayed.
	 * @param text New text to display.
	 */
	public void setText(Element element) {
		if (element instanceof Library) {
			Library library = (Library) element;
			upperLabel.setText("Library Name: " + library.toString());
			this.remove(when);
			this.remove(middleLabel);
			this.remove(lowerLabel);
		}
		else if(element instanceof Cell) {
			Cell cell = (Cell) element;
			upperLabel.setText("Default Leakage Power: " + cell.getDefaultLeakage());
			when.setText("When");
			selectedText = "";
			binary = "";
			
			for (InputPin input : cell.getInPins()) {
				selectedText += " " + "!" + input.getName() + "&"; 
				binary = "0" + binary ;
			}
			selectedText = selectedText.substring(0, selectedText.length() - 1);
			middleLabel.setText("Output Function:" + selectedText);
			lowerLabel.setText("Function Value: " + cell.getLeakages().getValues()[Integer.parseInt(binary, 2)]);
			
			
		}
		else {
			if (element instanceof InputPin) {
				InputPin pin = (InputPin) element;
				upperLabel.setText("Selected Pin: " + pin.getName());
				this.remove(when);
				this.remove(middleLabel);
				this.remove(lowerLabel);
			}
			else if(element instanceof OutputPin) {
				OutputPin pin = (OutputPin) element;
				upperLabel.setText("Selected Pin: " + pin.getName());
				this.remove(when);
				middleLabel.setText("Output Function: " + pin.getOutputFunction());
				this.remove(lowerLabel);
			}
			
		}

		
	}
	
	/**
	 * Updates the panel with the selected pin values.
	 * @param selectedPins List of the selected pins in the navigation panel. 
	 */
	public void updateSelectedPins(ArrayList<InputPin> selectedPins) {
		
		if (element instanceof Cell) {
			Cell cell = (Cell) element;
			String newBinary = "";
			for (InputPin pin : cell.getInPins()) {
				if (selectedPins.contains(pin)) {
					selectedText = selectedText.replace("!" + pin.getName(), pin.getName());
					newBinary =  newBinary + "1";
				}
				else {
					selectedText = selectedText.replace("!" + pin.getName(), pin.getName());
					selectedText = selectedText.replace(pin.getName(), "!" + pin.getName());
					newBinary = newBinary + "0";
				}
			}
			
			middleLabel.setText("Output Function:" + selectedText);
			lowerLabel.setText("Function Value: " + cell.getLeakages().getValues()[Integer.parseInt(newBinary, 2)]);
			binary = newBinary;
		}
		

		
	}
	
	/**
	 * Returns the element set in this panel.
	 * @return element of the panel
	 */
	public Element getElement() {
		return element;
	}
	
	/**
	 * Sets the element of the panel.
	 * @param element New element.
	 */
	public void setElement(Element element) {
		this.element = element;
	}
}

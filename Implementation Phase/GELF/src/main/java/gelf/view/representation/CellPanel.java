package gelf.view.representation;
import gelf.view.composites.Panel;
import gelf.model.elements.*;

import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

/*
 * Displays the selected cell on the navigation panel.
 */
public class CellPanel extends JPanel implements ActionListener {
	
	private Label label;
	private ArrayList<Button> buttons;
	private ArrayList<Checkbox> checkboxes;
    private Cell cell;
	private ArrayList<InputPin> inputPins;
	private ArrayList<OutputPin> outputPins;
	private BufferedImage cellImage;
	private CellImageGenerator imageGen;
	private LibraryPanel libraryPanel;
	
	
	/*
	 * Constructor
	 * @param cell Selected cell.
	 */
	public CellPanel(Cell cell) {
		label = new Label();
		this.cell = cell;
		this.inputPins = cell.getInPins();
		this.outputPins = cell.getOutPins();
		imageGen = new CellImageGenerator();
		cellImage = imageGen.buildCell(inputPins.size(), outputPins.size());
		for (InputPin input : inputPins) {
			Button button = new Button(input.getName());
			buttons.add(button);
			Checkbox checkbox = new CheckBox();
			checkboxes.add(checkbox);
		}
		for (OutputPin output : outputPins) {
			Button button = new Button(input.getName());
			buttons.add(button);
			Checkbox checkbox = new CheckBox();
			checkboxes.add(checkbox);
			
		}
		Button cellButton = new Button(cell.getName());
		buttons.add(cellButton);
		this.add(cellButton);
		
		
		label.setVisible(true);
		
		
	}
	
	/*
	 * Constructor 
	 * @param pin Selected pin.
	 */
	public CellPanel(Pin pin) {
		label = new Label();
		this.cell = pin.getCell();
		this.inputPins = cell.getInPins();
		this.outputPins = cell.getOutPins();
		
		//buttons
		
		imageGen = new CellImageGenerator();
		cellImage = imageGen.buildCell(inputPins.size(),
				outputPins.size());
		
		label.setVisible(true);
	}
	
	/*
	 * Sets the cell view for the given cell.
	 * @param cell Cell to be displayed.
	 */
	public void setCell(Cell cell) {
		this.cell = cell;
		this.inputPins = cell.getInPins();
		this.setVisible(false);
	}
	
	
	/*
	 * Switches the navigation panel to the parent library.
	 */
	public void switchToLibrary() {
		cell.getParentLibrary();
		//todo
		
	}
	
	/*
	 * Switches the navigation panel to a selected child pin.
	 * @param pin Selected pin
	 */
	public void switchToPin(Pin pin) {
		//todo
		
	}
	
	/*
	 * Switches the navigation panel from a pin to a cell.
	 * @param cell Selected cell.
	 */
	public void switchToCell(Cell cell) {
		 //todo
		
	}
	
	/*
	 * Returns the selected pins for the leakage power.
	 */
	public ArrayList<Pin> getSelectedPins(){
		ArrayList<Pin> pins = new ArrayList<Pin>();
		for (InputPin input : inputPins) {
			if (selected) {
				pins.add(input);
			}
		}
		for (OutputPin output : outputPins) {
			if (selected) {
				pins.add(output);
			}
		}
		return pins;
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		//todo
	}
	
	
	

	

}

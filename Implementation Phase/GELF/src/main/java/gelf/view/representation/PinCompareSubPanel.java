package gelf.view.representation;

import java.awt.Checkbox;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

import gelf.view.components.Button;
import gelf.model.elements.Cell;
import gelf.model.elements.Element;
import gelf.model.elements.InputPin;
import gelf.model.elements.OutputPin;
import gelf.model.elements.Pin;
import gelf.view.composites.Comparer;
import gelf.view.composites.SubWindow;

/**
 * Sub-panel for comparing cells and pins. Contains one cell
 * and its representation. 
 * @author Ege Uzhan
 */
public class PinCompareSubPanel extends CellPanel {
	

	private HashMap<Pin, Button> buttonMap;
	public Button cellButton;
	private Cell cell;
	private HashMap<Checkbox, InputPin> checkboxMap;
	private ArrayList<InputPin> selectedPins;
	private ArrayList<Pin> openedPins;
	
	
	private Comparer c;
	
	/**
	 * Instantiates a sub-panel
	 * @param width Width of the panel
	 * @param height Height of the panel
	 * @param cell Cell to be shown on the panel.
	 * @param subwindow The subwindow where this panel belongs to.
	 */
	public PinCompareSubPanel(int width, int height, Cell cell, SubWindow subwindow, ArrayList<Element> elements, Comparer c, PinComparePanel upperPanel, Color color1, Color color2) {
		super(width, height, cell, subwindow, null, null);
		this.cell = cell;
		
		
		this.buttonMap = super.getButtonMap();
		this.cellButton = super.getCellButton();
		this.openedPins = new ArrayList<Pin>();
		this.checkboxMap = super.checkboxMap;
		selectedPins = new ArrayList<InputPin>();
		this.c = c;
		boolean painted = false;
		for (InputPin input: cell.getInPins()) {
			if (elements.contains(input)) {
				if (!painted) {
					buttonMap.get(input).setBackground(color1);
					painted = true;
				} else {
					buttonMap.get(input).setBackground(color2);
				}
				for (Checkbox checkbox: super.checkboxes) {
					checkbox.setEnabled(false);
				}
				openedPins.add(input);
			}
		}
		for (OutputPin output: cell.getOutPins()) {
			if (elements.contains(output)) {
				if (elements.contains(output)) {
					if (!painted) {
						buttonMap.get(output).setBackground(color1);
						painted = true;
					} else {
						buttonMap.get(output).setBackground(color2);
					}
					openedPins.add(output);
				}
			}
		}
		if (elements.contains(cell)) {
			cellButton.setBackground(color1);
		}
		
	}
	
	/**
	 * Sets the cell of the panel
	 * @param cell New cell.
	 */
	public void setElement(Cell cell) {
		this.cell = cell;
	}
	
	/**
	 * Updates the panel with the selected pin values.
	 * @param selectedPins List of the selected pins in the navigation panel. 
	 */
	public float updateSelectedPins(ArrayList<InputPin> selectedPins) {
		String newBinary = "";
		for (InputPin pin : cell.getInPins()) {
			if (selectedPins.contains(pin)) {
				newBinary =  newBinary + "1";
			}
			else {
				newBinary = newBinary + "0";
			}
		}
		return cell.getLeakages().getValues()[Integer.parseInt(newBinary, 2)];
	}
		
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			if (openedPins.get(0) instanceof OutputPin) {
				selectedPins.add(checkboxMap.get(e.getSource()));
				ArrayList<InputPin> newList = c.getSelectedPins();
				newList.add(checkboxMap.get(e.getSource()));
				c.setSelectedPins(newList);
				for (Checkbox checkbox: super.checkboxes) {
					if (!((Checkbox)e.getSource()).equals(checkbox)) {
						checkbox.setEnabled(false);
					}
				}
			}
			
		}
		else {
			selectedPins.remove(checkboxMap.get(e.getSource()));
			ArrayList<InputPin> newList = c.getSelectedPins();
			newList.remove(checkboxMap.get(e.getSource()));
			c.setSelectedPins(newList);
			for (Checkbox checkbox: super.checkboxes) {
				checkbox.setEnabled(true);
			}
		}
		updateSelectedPins(selectedPins);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		/*if (cell.getName() == ((Label)e.getSource()).getText()) {
			if (((Label)e.getSource()).getBackground().equals(Color.BLUE)) {
				upperPanel.getOpenedCells().remove(cell);
				((Label)e.getSource()).setBackground(new Color(0.2f, 0.2f, 0.2f));
				c.setCells(upperPanel.getOpenedCells());
				
			}
			else {
				upperPanel.getOpenedCells().add(cell);
				((Label)e.getSource()).setBackground(Color.BLUE);
				c.setCells(upperPanel.getOpenedCells());
			}
			return;
		}
		for (InputPin input : inputPins) {
			if (input.getName() == (((Label)e.getSource()).getText())) {
				if (((Label)e.getSource()).getBackground().equals(Color.BLUE)) {
					upperPanel.getOpenedInPins().remove(input);
					((Label)e.getSource()).setBackground(new Color(0.2f, 0.2f, 0.2f));
					c.setInputPins(upperPanel.getOpenedInPins());
				}
				else {
					upperPanel.getOpenedInPins().add(input);
					((Label)e.getSource()).setBackground(Color.BLUE);
					c.setInputPins(upperPanel.getOpenedInPins());
				}
			}
		}
		for (OutputPin output : outputPins) {
            if (output.getName() == (((Label)e.getSource()).getText())) {
            	if (((Label)e.getSource()).getBackground().equals(Color.BLUE)) {
            		upperPanel.getOpenedOutPins().remove(output);
            		((Label)e.getSource()).setBackground(new Color(0.2f, 0.2f, 0.2f));
            		c.setOutputPins(upperPanel.getOpenedOutPins());
    			}
    			else {
    				upperPanel.getOpenedOutPins().add(output);
    				((Label)e.getSource()).setBackground(Color.BLUE);
    				c.setOutputPins(upperPanel.getOpenedOutPins());
    			}
			}
		}*/
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
		
	@Override
	public void actionPerformed(ActionEvent e) {}

}

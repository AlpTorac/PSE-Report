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
	private HashMap<Button, Color> colorMap;
	
	
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
		
		this.colorMap = new HashMap<Button, Color>();
		this.buttonMap = super.getButtonMap();
		this.cellButton = super.getCellButton();
		this.openedPins = new ArrayList<Pin>();
		this.checkboxMap = super.checkboxMap;
		selectedPins = new ArrayList<InputPin>();
		this.c = c;
		boolean painted = false;
		for (InputPin input: cell.getInPins()) {
			if (elements.contains(input)) {
				for (Checkbox checkbox: super.checkboxes) {
					checkbox.setEnabled(false);
				}
				if (!painted) {
					buttonMap.get(input).setBackground(color1);
					painted = true;
					colorMap.put(buttonMap.get(input), color1);
				} else {
					buttonMap.get(input).setBackground(color2);
					colorMap.put(buttonMap.get(input), color2);
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
						colorMap.put(buttonMap.get(output), color1);
					} else {
						buttonMap.get(output).setBackground(color2);
						colorMap.put(buttonMap.get(output), color1);
					}
					openedPins.add(output);
				}
			}
		}
		if (elements.contains(cell)) {
			for (Checkbox checkbox: super.checkboxes) {
				checkbox.setEnabled(false);
			}
			cellButton.setBackground(color1);
			colorMap.put(cellButton, color1);
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
			if (!openedPins.isEmpty() && openedPins.get(0) instanceof OutputPin) {
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
	public void mouseClicked(MouseEvent e) {}
	
	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {
		if (colorMap.containsKey((Button) e.getSource()))  {
			((Button) e.getSource()).setBackground(colorMap.get(((Button) e.getSource())));
		}
	}
		
	@Override
	public void actionPerformed(ActionEvent e) {}

}

package gelf.view.representation;

import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

import gelf.model.elements.Cell;
import gelf.model.elements.Element;
import gelf.model.elements.InputPin;
import gelf.model.elements.OutputPin;
import gelf.model.elements.Pin;
import gelf.view.components.Label;
import gelf.view.composites.SubWindow;

public class PinCompareSubPanel extends CellPanel {
	
	private ArrayList<InputPin> inputPins;
	private ArrayList<OutputPin> outputPins;
	private HashMap<Pin, Label> buttonMap;
	private Label cellButton;
	private Cell cell;

	public PinCompareSubPanel(int width, int height, Cell cell, SubWindow subwindow) {
		super(width, height, cell, subwindow, null);
		this.cell = cell;
		
		this.inputPins = super.getInputPins();
		this.outputPins = super.getOutputPins();
		this.buttonMap = super.getButtonMap();
		this.cellButton = super.getCellButton();

	}
	
	public void setElement(Cell cell) {
		this.cell = cell;
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (cell.getName() == ((Label)e.getSource()).getText()) {
			if (((Label)e.getSource()).getBackground().equals(Color.BLUE)) {
				((Label)e.getSource()).setBackground(new Color(0.2f, 0.2f, 0.2f));
				
			}
			else {
				((Label)e.getSource()).setBackground(Color.BLUE);
			}
			return;
		}
		for (InputPin input : inputPins) {
			if (input.getName() == (((Label)e.getSource()).getText())) {
				if (((Label)e.getSource()).getBackground().equals(Color.BLUE)) {
					
					((Label)e.getSource()).setBackground(new Color(0.2f, 0.2f, 0.2f));
				}
				else {
					((Label)e.getSource()).setBackground(Color.BLUE);
				}
			}
		}
		for (OutputPin output : outputPins) {
            if (output.getName() == (((Label)e.getSource()).getText())) {
            	if (((Label)e.getSource()).getBackground().equals(Color.BLUE)) {
    				
            		((Label)e.getSource()).setBackground(new Color(0.2f, 0.2f, 0.2f));
    			}
    			else {
    				((Label)e.getSource()).setBackground(Color.BLUE);
    			}
			}
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {}
		

	@Override
	public void mouseExited(MouseEvent e) {
		if (e.getSource() instanceof Label) {
			if (((Label)e.getSource()).getBackground().equals(Color.BLUE)) {return;
			}
			e.getComponent().setBackground(new Color(0.2f, 0.2f, 0.2f));
		}
		
	}


}

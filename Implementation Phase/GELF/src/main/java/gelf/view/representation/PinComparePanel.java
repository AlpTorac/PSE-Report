package gelf.view.representation;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;

import gelf.model.elements.Cell;
import gelf.model.elements.Element;
import gelf.model.elements.InputPin;
import gelf.model.elements.Library;
import gelf.model.elements.OutputPin;
import gelf.model.elements.Pin;
import gelf.view.components.Panel;
import gelf.view.composites.Comparer;
import gelf.view.composites.SubWindow;
import gelf.view.diagrams.SettingsProvider;

/**
 * Holds all subpanels for comparison.
 * @author Ege Uzhan
 *
 */
public class PinComparePanel extends Panel {
	
	public ArrayList<Cell> cells;
	private SubWindow subwindow;
    private JScrollPane scrollPane;
    private JPanel listPanel;
    private ArrayList<Cell> openedCells;
    private ArrayList<InputPin> openedInPins;
    private ArrayList<OutputPin> openedOutPins;
    private ArrayList<InputPin> selectedPins;
    private Comparer c;

    /**
     * Initializes the panel.
     * @param width Width of the panel
     * @param height Height of the panel
     * @param subwindow The subwindow area
     * @param elements elements opened in the panel
     */
	public PinComparePanel(int width, int height, SubWindow subwindow, Comparer c, ArrayList<Element> elements) {
		super(width, height);
		this.subwindow = subwindow;
		this.c = c;
		openedCells = new ArrayList<Cell>();
		openedInPins = new ArrayList<InputPin>();
		openedOutPins = new ArrayList<OutputPin>();
		selectedPins = new ArrayList<InputPin>();
		cells = new ArrayList<Cell>();
		this.setLayout(new FlowLayout());
		this.setBorder(new LineBorder(Color.BLACK));
	    
	    listPanel = new JPanel(); 
	    listPanel.setLayout(new FlowLayout());
	    listPanel.setBackground(new Color(0.3f, 0.3f, 0.3f));
	    scrollPane = new JScrollPane(listPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
	    		ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    this.add(scrollPane);
	    scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
	    listPanel.setSize(width, height);
	     
	    this.setVisible(true);
	    ArrayList<Cell> cells = new ArrayList<Cell>();
	    for (Element element: elements) {
	    	if (element instanceof InputPin) {
	    		if (!cells.contains(((Pin)element).getParent())) {
	    			cells.add(((Pin)element).getParent());
	    			openedInPins.add((InputPin) element);
	    		}
	    	}
	    	else if (element instanceof OutputPin) {
	    		if (!cells.contains(((Pin)element).getParent())) {
	    			cells.add(((Pin)element).getParent());
	    			openedOutPins.add((OutputPin) element);
	    		}
	    	}
	    	else if (element instanceof Cell) {
	    		if (!cells.contains((Cell) element)) {
	    			cells.add((Cell) element);
	    			openedCells.add((Cell) element);
	    		}
	    	}
	    }
	    if (cells.size() == 1) {
	    	for (int i = 0; i < cells.size(); i++) {
		    	Color color1 = SettingsProvider.getInstance().getValueDisplayComponentColorAt(1);
		    	Color color2 = SettingsProvider.getInstance().getValueDisplayComponentColorAt(0);
		    	listPanel.add(new PinCompareSubPanel(width, height, cells.get(i), subwindow, elements, c, this, color1, color2));
		    	
		    }
	    } else {
		    for (int i = 0; i < cells.size(); i++) {
		    	Color color = SettingsProvider.getInstance().getValueDisplayComponentColorAt(i);
		    	listPanel.add(new PinCompareSubPanel(width, height, cells.get(i), subwindow, elements, c, this, color, null));
		    	
		    }
	    }
	}
	
	public ArrayList<Cell> getOpenedCells(){
		return openedCells;
	}
	
	public ArrayList<InputPin> getOpenedInPins(){
		return openedInPins;
	}
	
	public ArrayList<OutputPin> getOpenedOutPins(){
		return openedOutPins;
	}
	
	public ArrayList<InputPin> getSelectedPins(){
		return selectedPins;
	}
}

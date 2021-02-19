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
    private HashMap<Cell, ArrayList<Element>> openedByCell;
    private HashMap<Cell, ArrayList<InputPin>> selectedPinsByCell;
    private ArrayList<Element> openedElements;
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
		openedElements = new ArrayList<Element>();
		openedByCell = new HashMap<Cell, ArrayList<Element>>();
		selectedPinsByCell = new HashMap<Cell, ArrayList<InputPin>>();
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
	    	if (element instanceof Pin) {
	    		if (!cells.contains(((Pin)element).getParent())) {
	    			cells.add(((Pin)element).getParent());
	    		}
	    	}
	    	else if (element instanceof Cell) {
	    		if (!cells.contains((Cell) element)) {
	    			cells.add((Cell) element);
	    		}
	    	}
	    }
	    for (Cell cell: cells) {
	    	openedByCell.put(cell, new ArrayList<Element>());
	    	listPanel.add(new PinCompareSubPanel(width, height, cell, subwindow, c, this));
	    }
	}
	
	/**
	 * Returns 
	 * @return 
	 */
	public HashMap<Cell, ArrayList<Element>> getOpenedByCell() {
		return openedByCell;
	}
	
	/**
	 * Returns
	 * @return
	 */
	public HashMap<Cell, ArrayList<InputPin>> getSelectedPinsByCell() {
		return selectedPinsByCell;
	}
}

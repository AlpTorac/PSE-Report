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
import gelf.model.elements.Library;
import gelf.model.elements.Pin;
import gelf.view.components.Panel;
import gelf.view.composites.SubWindow;

public class PinComparePanel extends Panel {
	
	public ArrayList<Cell> cells;
	private SubWindow subwindow;
    private JScrollPane scrollPane;
    private JPanel listPanel;

	public PinComparePanel(int width, int height, SubWindow subwindow, ArrayList<Pin> pins) {
		super(width, height);
		this.subwindow = subwindow;
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
	    
	    for (Pin pin: pins) {
	    	if (!cells.contains(pin.getParent())) {
	    		cells.add(pin.getParent());
	    	}
	    	
	    }
	    for (int i = 0; i < cells.size(); i++) {
	    	ArrayList<Pin> selectedPins = new ArrayList<Pin>();
	    		for (Pin pin: pins) {
	    			if (pin.getParent().equals(cells.get(i))) {
	    				selectedPins.add(pin);
	    			}
	    		}
	    	listPanel.add(new PinCompareSubPanel(width, height, cells.get(i), subwindow, selectedPins));
	    }

	}
}

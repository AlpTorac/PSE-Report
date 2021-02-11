package gelf.view.representation;

import gelf.model.elements.*;
import gelf.view.components.Panel;
import gelf.view.composites.SubWindow;

import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Label;
import java.awt.ScrollPane;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

/*
 * Displays all child cells of the selected library.
 */
public class LibraryPanel extends Panel implements MouseListener{
   
	private HashMap<Label, Cell> buttons;
    private JPanel listPanel;
    private Library selectedLibrary;
	private ArrayList<Cell> cells;
	private SubWindow subwindow;
    private JScrollPane scrollPane;
    private DataPanel dataPanel;
    
   
    /*
     * Constructor
     * @param library To be opened library.
     */
    public LibraryPanel(int width, int height, Library library, SubWindow subwindow, DataPanel dataPanel) {
    	super(width, height);
    	this.subwindow = subwindow;
    	this.dataPanel = dataPanel;
    	dataPanel.setElement(library);
    	selectedLibrary = library;
        cells = selectedLibrary.getCells();
       
        listPanel = new JPanel(); 
        listPanel.setBackground(new Color(0.3f, 0.3f, 0.3f));
        scrollPane = new JScrollPane(listPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
        		ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.add(scrollPane);
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        listPanel.setPreferredSize(new Dimension(200, cells.size() * 30));
         
        for (int i = 0; i < cells.size(); i++) {
        	Label label = new Label();
        	label.setText(cells.get(i).getName());
        	label.setFont(new Font("Arial", Font.PLAIN, 12));
        	label.setForeground(Color.WHITE);
        	listPanel.add(label);
        	label.addMouseListener(this);
        	buttons.put(label, cells.get(i));
        }
        this.setVisible(true);

    }
    

	@Override
	public void mouseClicked(MouseEvent e) {
		subwindow.setElement(buttons.get(e.getSource()));
		this.setVisible(false);
		dataPanel.setElement(buttons.get(e.getSource()));
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		e.getComponent().setBackground(new Color(100));
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		e.getComponent().setBackground(new Color(0));
		
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}
}

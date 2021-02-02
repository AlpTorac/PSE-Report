package gelf.view.representation;

import gelf.model.elements.*;

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
public class LibraryPanel extends Panel implements MouseListener, Resizable{
    private HashMap<Label, Cell> buttons;
    private JPanel listPanel;
    private Library selectedLibrary;
	private ArrayList<Cell> cells;
	private SubWindowArea subwindowArea;
    private JScrollPane scrollPane;
    private DataPanel dataPanel;
    /*
     * Constructor
     * @param library To be opened library.
     */
    public LibraryPanel(Library library, DataPanel dataPanel) {
    	this.dataPanel = dataPanel;
    	selectedLibrary = library;
        cells = selectedLibrary.getCells();
       
        listPanel = new JPanel();
        scrollPane = new JScrollPane(listPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
        		ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.add(scrollPane);
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
         listPanel.setPreferredSize(new Dimension(200, cells.size() * 30));
         
        for (int i = 0; i < cells.size(); i++) {
        	Label label = new JLabel();
        	label.setText(cells.get(i).getName());
        	label.setFont(new Font("Arial", Font.PLAIN, 12));
        	listPanel.add(label);
        	label.addMouseListener(this);
        	buttons.put(label, cells.get(i));
        }
        this.setVisible(true);

    }
    
    /*
     * Switches the visualizer to the cell view for a selected cell.
     * @param cell Selected cell element.
     */
    public void switchToCell(Cell cell) {
    	subwindowArea.add(new Subwindow(cell));
    	//todo
    }

	@Override
	public void mouseClicked(MouseEvent e) {
		//to be opened cell
		buttons.get(e.getComponent());
		//todo
		
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
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}

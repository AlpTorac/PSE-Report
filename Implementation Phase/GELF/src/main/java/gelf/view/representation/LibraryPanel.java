package gelf.view.representation;

import gelf.model.elements.*;
import gelf.view.components.Label;
import gelf.view.components.Panel;
import gelf.view.composites.ColorTheme;
import gelf.view.composites.SubWindow;

import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;

/**
 * Displays all child cells of the selected library.
 * @author Ege Uzhan
 */
public class LibraryPanel extends Panel implements MouseListener{
   
    public HashMap<Label, Cell> buttons;
	public ArrayList<Label> buttonList;
    private JPanel listPanel;
    private Library selectedLibrary;
	private ArrayList<Cell> cells;
	private SubWindow subwindow;
    private JScrollPane scrollPane;
    
   
    /**
     * Instantiates the panel
     * @param width Width of the panel
     * @param height  Height of the panel.
     * @param library To be opened library.
     * @param subwindow Subwindow of the panel.
     */
    public LibraryPanel(int width, int height, Library library, SubWindow subwindow) {
    	super(width, height);
    	this.setMaximumSize(new Dimension(100, 100));
    	this.subwindow = subwindow;
    	selectedLibrary = library;
    	buttonList = new ArrayList<Label>();
    	this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    	this.setBorder(new LineBorder(Color.BLACK));
        cells = selectedLibrary.getCells();
        buttons = new HashMap<Label,Cell>();
        listPanel = new JPanel(); 

		for (int i = 0; i < cells.size(); i++) {
			Label label = new Label();
			label.setText(cells.get(i).getName());
			label.setFont(new Font("Arial", Font.PLAIN, 12));
			label.setForeground(Color.WHITE);
			label.addMouseListener(this);
			
			listPanel.add(label);
			buttonList.add(label);
			buttons.put(label, cells.get(i));
		}

        listPanel.setLayout(new GridLayout(0,4));
        listPanel.setBackground(new Color(0.3f, 0.3f, 0.3f));
        scrollPane = new JScrollPane(listPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
        		ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.add(scrollPane);
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        listPanel.setSize(width, height);
         
        this.setVisible(true);

    }
    
    /**
     * Returns the selected library
     * @return selected library
     */
    public Library getLibrary() {
    	return selectedLibrary;
    }
    
    /**
     * Returns the cells of the library opened in the panel.
     * @return list of cells
     */
    public ArrayList<Cell> getCells() {
    	return cells;
    }
    

	@Override
	public void mousePressed(MouseEvent e) {
		subwindow.setElement(buttons.get(e.getSource()));
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		e.getComponent().setBackground(ColorTheme.active);
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		e.getComponent().setBackground(ColorTheme.interactable);
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}
}

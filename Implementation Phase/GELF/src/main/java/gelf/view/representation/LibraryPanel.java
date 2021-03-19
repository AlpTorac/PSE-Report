package gelf.view.representation;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;

import gelf.model.elements.Cell;
import gelf.model.elements.Library;
import gelf.view.components.Button;
import gelf.view.components.Panel;
import gelf.view.composites.ColorTheme;
import gelf.view.composites.SubWindow;

/**
 * Displays all child cells of the selected library.
 * @author Ege Uzhan
 */
public class LibraryPanel extends Panel implements ActionListener {
   
    public HashMap<Button, Cell> buttons;
	public ArrayList<Button> buttonList;
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
    	buttonList = new ArrayList<Button>();
    	this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    	this.setBorder(new LineBorder(Color.BLACK));
        cells = selectedLibrary.getCells();
        buttons = new HashMap<Button,Cell>();
        listPanel = new JPanel(); 

		for (int i = 0; i < cells.size(); i++) {
			Button button = new Button(cells.get(i).getName());
			button.setFont(new Font("Arial", Font.PLAIN, 12));
			//button.setForeground(Color.WHITE);
			button.addActionListener(this);		
			listPanel.add(button);
			buttonList.add(button);
			buttons.put(button, cells.get(i));
		}

        listPanel.setLayout(new GridLayout(0,4));
        listPanel.setBackground(ColorTheme.subsection);
        scrollPane = new JScrollPane(listPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
        		ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.add(scrollPane);
		scrollPane.setBorder(new LineBorder(ColorTheme.frame, 2));
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
	public void actionPerformed(ActionEvent e) {
		subwindow.setElement(buttons.get(e.getSource()));
	}
}

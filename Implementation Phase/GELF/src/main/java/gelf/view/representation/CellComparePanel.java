package gelf.view.representation;

import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;

import gelf.model.elements.Cell;
import gelf.model.elements.InputPin;
import gelf.model.elements.Library;
import gelf.view.components.Label;
import gelf.view.components.Panel;
import gelf.view.composites.SubWindow;

public class CellComparePanel extends Panel implements MouseListener {
	
	ArrayList<Cell> cells;
	public HashMap<Label, Cell> buttons;
	public ArrayList<Label> buttonList;
    private JPanel listPanel;
    private ArrayList<Library> libraryList;
	private SubWindow subwindow;
    private JScrollPane scrollPane;
    
	public CellComparePanel(int width, int height, SubWindow subwindow, ArrayList<Cell> cells) {
    	super(width, height);
    	this.subwindow = subwindow;
    	this.cells = cells;
    	buttonList = new ArrayList<Label>();
    	this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    	this.setBorder(new LineBorder(Color.BLACK));
        buttons = new HashMap<Label, Cell>();
        listPanel = new JPanel(); 
        listPanel.setLayout(new GridLayout(0,2));
        listPanel.setBackground(new Color(0.3f, 0.3f, 0.3f));
        scrollPane = new JScrollPane(listPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
        		ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.add(scrollPane);
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        listPanel.setSize(width, height);
         
        for (int i = 0; i < cells.size(); i++) {
        	Label label = new Label();
        	label.setText(cells.get(i).getName());
        	label.setFont(new Font("Arial", Font.PLAIN, 12));
        	buttonList.add(label);
        	label.setForeground(Color.WHITE);
        	listPanel.add(label);
        	label.addMouseListener(this);
        	buttons.put(label, cells.get(i));
        }
        this.setVisible(true);

	}
	
	
    
  
		
	@Override
	public void mouseClicked(MouseEvent e) {
		for (Cell cell: cells) {
			if (cell.getName() == ((Label)e.getSource()).getText()) {
				if (((Label)e.getSource()).getBackground().equals(Color.BLUE)) {
						
					((Label)e.getSource()).setBackground(new Color(0.2f, 0.2f, 0.2f));
				}
				else {
						
					((Label)e.getSource()).setBackground(Color.BLUE);
				}
				return;
			}
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {
		if (((Label)e.getSource()).getBackground().equals(Color.BLUE)){
			return;
		}
		e.getComponent().setBackground(new Color(0.2f, 0.2f, 0.2f));
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}
	
}
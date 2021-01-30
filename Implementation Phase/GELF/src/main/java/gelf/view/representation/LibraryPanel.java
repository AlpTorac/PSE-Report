package gelf.view.representation;

import gelf.model.elements.*;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;



/*
 * Displays all child cells of the selected library.
 */
public class LibraryPanel extends Panel implements MouseListener{
    private ArrayList<Button> buttons;
    private Library selectedLibrary;
	private ArrayList<Cell> cells;
	private SubWindowArea subwindowArea;
    private ScrollPane scrollpane;

    /*
     * Constructor
     * @param library To be opened library.
     */
    public LibraryPanel(Library library) {
        cells = library.getCells();
        scrollpane = new ScrollPane();
        this.add(slider);
        scrollpane.addMouseListener(this);
        for (int i = 0; i < cells.size(); i++) {
        	slider.add(cells.get(i).getName());
        }
        this.setVisible(true);

    }
    
    /*
     * Sets the library to be displayed.
     * @param library New library to display.
     */
    public void setElement(Library library) {
    	this.selectedLibrary = library;
    }
    
    /*
     * Switches the visualizer to the cell view for a selected cell.
     * @param cell Selected cell element.
     */
    public void switchToCell(Cell cell) {
    	this.setVisible(false);
    	
    }

	@Override
	public void mouseClicked(MouseEvent e) {
		for (int i = 0; i < cells.size(); i++) {
			if (cells.get(i).getName().equals(e.getSource())) {
				subwindowArea.add(new SubWindow(cells.get(i)));
				return;
			}
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
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

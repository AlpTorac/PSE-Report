package gelf.view.composites;

import gelf.model.elements.Cell;
import gelf.model.elements.Element;
import gelf.model.elements.Library;
import gelf.model.project.Project;

import java.awt.*;

import javax.swing.BoxLayout;

import gelf.view.components.Panel;
import gelf.view.representation.CellPanel;
import gelf.view.representation.DataPanel;
import gelf.view.representation.LibraryPanel;
/**
 * Visualizer
 */
public class Visualizer extends ElementManipulator {

	SubWindow subWindow;
	DataPanel dataPanel;
	Panel upperPanel;
	
    public Visualizer(gelf.model.elements.Element e, SubWindow w, Project p, int width, int height) {
        super(e, p, width, height);
        this.subWindow = w;
        this.setBackground(Color.green);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        upperPanel = new Panel(width, height);
        upperPanel.setLayout(new FlowLayout());
        dataPanel = new DataPanel(100, 100, e);
        this.add(upperPanel);
       
        if (e instanceof Library) {
        	upperPanel.add(new LibraryPanel(100, 100, (Library)e, w));
	    }
	    else {
	    	upperPanel.add(new CellPanel(150, 150, e, w, dataPanel));    
	    }
        upperPanel.add(dataPanel);
        dataPanel.setAlignmentY(CENTER_ALIGNMENT);
        
    } 
    
    public void setElement(Element e) {
    	upperPanel.removeAll();
    	dataPanel = new DataPanel(100, 100, e);
    	if (e instanceof Library ) {
    		upperPanel.add(new LibraryPanel(100, 100, (Library)e, subWindow));
    	}
    	else {
    		upperPanel.add(new CellPanel(100, 100, e, subWindow, dataPanel)); 
    	}
    	upperPanel.add(dataPanel);
    	this.revalidate();
    	this.repaint();
    	
  	}
}
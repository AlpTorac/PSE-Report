package gelf.view.composites;

import gelf.model.elements.Cell;
import gelf.model.elements.Element;
import gelf.model.elements.Library;
import gelf.model.project.Project;

import java.awt.*;

import javax.swing.Box;
import javax.swing.BoxLayout;

import gelf.view.components.Checkbox;
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
	Panel lowerPanel;
	
    public Visualizer(gelf.model.elements.Element e, SubWindow w, Project p, int width, int height) {
        super(e, p, width, height);
        this.subWindow = w;
		//style
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        //cell display
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
        //diagram display
		this.lowerPanel = new Panel(width, height);
		this.lowerPanel.setLayout(new BorderLayout());
		this.lowerPanel.setBackground(ColorTheme.subsection);
		this.lowerPanel.setVisible(true);
		//statistics checkboxes
		Panel stats = new Panel(width, height);
		stats.setLayout(new BoxLayout(stats, BoxLayout.X_AXIS));
		stats.setBackground(ColorTheme.section);
		stats.setVisible(true);

		Checkbox min = new Checkbox("Minimum");
		min.setVisible(true);
		stats.add(min);
		Checkbox max = new Checkbox("Maximum");
		max.setVisible(true);
		stats.add(max);
		Checkbox avg = new Checkbox("Average");
		avg.setVisible(true);
		stats.add(avg);
		Checkbox med = new Checkbox("Median");
		med.setVisible(true);
		stats.add(med);
		//stats.add(Box.createHorizontalGlue());
		this.lowerPanel.add(stats, BorderLayout.PAGE_START);
		this.add(this.lowerPanel);
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
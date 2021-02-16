package gelf.view.composites;

import gelf.model.elements.Cell;
import gelf.model.elements.Element;
import gelf.model.elements.Library;
import gelf.model.elements.Stat;
import gelf.model.project.Project;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;

import gelf.view.components.Checkbox;
import gelf.view.components.Panel;
import gelf.view.diagrams.DiagramWizard;
import gelf.view.diagrams.IDiagram;
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
	Panel diagramPanel;
	IDiagram diagram;
	
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
		this.lowerPanel.add(stats, BorderLayout.PAGE_END);
		//diagram viewport
		DiagramWizard wiz = new DiagramWizard();
		ArrayList<float[]> data = new ArrayList<float[]>();
		data.add(new float[]{1,2,3.5f,5.3f,4.9f});
		this.diagramPanel = new Panel(500, 500);
		this.diagramPanel.setBackground(ColorTheme.text);
		this.diagramPanel.setLayout(null);
		this.diagramPanel.setOpaque(true);
		this.diagram = wiz.makeBarChart(this.diagramPanel, data);
		this.diagram.attachToContainer(this.diagramPanel);
		this.diagram.refresh();
		this.diagramPanel.setVisible(true);
		this.lowerPanel.add(this.diagramPanel, BorderLayout.CENTER);

		this.add(this.lowerPanel);
		this.addComponentListener(this);
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

	@Override
	public void componentResized(ComponentEvent e) {
		super.componentResized(e);
		
		DiagramWizard wiz = new DiagramWizard();
		ArrayList<float[]> data = new ArrayList<float[]>();
		data.add(new float[]{1,2,3.5f,5.3f,4.9f});
		this.diagram.removeFromContainer();
		this.diagram = wiz.makeBarChart(this.diagramPanel, data);
		this.diagram.attachToContainer(this.diagramPanel);
		this.diagram.refresh();
	}
}
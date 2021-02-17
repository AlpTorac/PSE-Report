package gelf.view.composites;

import gelf.model.elements.Cell;
import gelf.model.elements.Element;
import gelf.model.elements.Library;
import gelf.model.elements.Stat;
import gelf.model.elements.attributes.Timing;
import gelf.model.project.Project;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;

import gelf.view.components.Checkbox;
import gelf.view.components.DropdownSelector;
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
	// dropdown enums
	//attributes
    public enum Attribute {
        INPUT_INTERNAL_POWER("Input Internal Power"),
        OUTPUT_INTERNAL_POWER("Output Internal Power"),
		LEAKAGE_POWER("Leakage Power"),
		OUTPUT_TIMING("Output Timing");
        private String str;
        private Attribute(String s) {
            this.str = s;
        }

        @Override
        public String toString() {
            return this.str;
        }
    }
	//internal power
		//power groups
		public enum PowerGroups {
			FALL_POWER("Fall-Power"),
			RISE_POWER("Rise-Power");
			private String str;
			private PowerGroups(String s) {
				this.str = s;
			}

			@Override
			public String toString() {
				return this.str;
			}
		}
	//output timing
		//timing sense
		public enum TimingSense {
			POSITIVE_UNNATE("Positive-Unate"),
			NEGATIVE_UNNATE("Negative-Unate"),
			NON_UNNATE("Non-Unate");
			private String str;
			private TimingSense(String s) {
				this.str = s;
			}

			@Override
			public String toString() {
				return this.str;
			}
		}
		public enum TimingType {
			COMBINATIONAL("Combinational"),
			THREE_STATE_ENABLE("Three-State-Enable");
			private String str;
			private TimingType(String s) {
				this.str = s;
			}

			@Override
			public String toString() {
				return this.str;
			}
		}
		public enum TimingGroup {
			CELL_FALL("Cell-Fall"),
			CELL_RISE("Cell-Rise"),
			FALL_TRANSITION("Fall-Transition"),
			RISE_TRANSITION("Rise-Transition");
			private String str;
			private TimingGroup(String s) {
				this.str = s;
			}

			@Override
			public String toString() {
				return this.str;
			}
		}

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
        //diagram display panel
		this.lowerPanel = new Panel(width, height);
		this.lowerPanel.setLayout(new BorderLayout());
		this.lowerPanel.setBackground(ColorTheme.subsection);
		this.lowerPanel.setVisible(true);
		//dropdowns
		Panel dropdowns = new Panel(width, height);
		dropdowns.setLayout(new BoxLayout(dropdowns, BoxLayout.X_AXIS));
		dropdowns.setBackground(ColorTheme.section);
		dropdowns.setVisible(true);

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

		Cell cell = (Cell)e;
		float[] leakageValues = cell.getLeakages().getValues();
		ArrayList<float[]> data = new ArrayList<float[]>();
		data.add(leakageValues);
		
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
		this.diagram.removeFromContainer();
		this.diagram = wiz.makeBarChart(this.diagramPanel, this.diagram.cloneData());
		this.diagram.attachToContainer(this.diagramPanel);
		this.diagram.refresh();
	}
}
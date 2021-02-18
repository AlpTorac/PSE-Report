package gelf.view.composites;

import gelf.model.elements.Cell;
import gelf.model.elements.Element;
import gelf.model.elements.InputPin;
import gelf.model.elements.Library;
import gelf.model.elements.OutputPin;
import gelf.model.elements.Stat;
import gelf.model.elements.attributes.InputPower;
import gelf.model.elements.attributes.OutputPower;
import gelf.model.elements.attributes.PowerGroup;
import gelf.model.elements.attributes.Timing;
import gelf.model.elements.attributes.TimingGroup;
import gelf.model.elements.attributes.TimingKey;
import gelf.model.elements.attributes.TimingSense;
import gelf.model.elements.attributes.TimingType;
import gelf.model.project.Project;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;

import gelf.view.components.Checkbox;
//import gelf.view.components.DropdownSelector;
import gelf.view.components.Panel;
import gelf.view.diagrams.DiagramWizard;
import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.IDiagramViewHelper;
import gelf.view.diagrams.IDiagramWizard;
import gelf.view.representation.CellPanel;
import gelf.view.representation.DataPanel;
import gelf.view.representation.LibraryPanel;
/**
 * Visualizer
 */
public class Visualizer extends ElementManipulator {
	Visualizer _this = this;
	SubWindow subWindow;
	DataPanel dataPanel;
	Panel upperPanel;
	Panel lowerPanel;
	Panel dropdowns;
	
	Panel diagramPanel;

	IDiagramViewHelper IDHmin;
	IDiagramViewHelper IDHmax;
	IDiagramViewHelper IDHavg;
	IDiagramViewHelper IDHmed;
	IDiagram diagram;
	private JComboBox<Attribute> libDropdown = new JComboBox<Attribute>();
	private JComboBox<Attribute> cellDropdown = new JComboBox<Attribute>();
	private JComboBox<Attribute> outpinDropdown = new JComboBox<Attribute>();
	private JComboBox<PowerGroup> powerGroupDropdown = new JComboBox<PowerGroup>();
	private JComboBox<TimingType> timingTypeDropdown = new JComboBox<TimingType>();
	private JComboBox<TimingGroup> timingGroupDropdown = new JComboBox<TimingGroup>();
	private JComboBox<TimingSense> timingSenseDropdown = new JComboBox<TimingSense>();

	//attributes enum for dropdowns
	public enum Attribute {
		INPUT_POWER("Input Power"),
		OUTPUT_POWER("Output Power"),
		TIMING("Timing"),
		LEAKAGE("Leakage"),
		DEFAULT_LEAKAGE("Default Leakage");
		private String str;
		private Attribute(String s) {
			this.str = s;
		}
		@Override
		public String toString() {
			return this.str;
		}
	}
	
	//tracks dropdown state
	public Attribute attribute = Attribute.INPUT_POWER;		//for cell/library									||	output pin
	public PowerGroup powerGroup = PowerGroup.FALL_POWER;	//for cell/library if attribute input/output power	||	output if attribute output power	||	input pin
	public TimingSense timingSense;	//for cell/library if attribute timing				||	output if attribute timing
	public TimingGroup timingGroup;	//for cell/library if attribute timing				||	output if attribute timing
	public TimingType timingType;	//for cell/library if attribute timing				||	output if attribute timing
	private Checkbox min = new Checkbox("Minimum");
	private Checkbox max = new Checkbox("Maximum");
	private Checkbox avg = new Checkbox("Average");
	private Checkbox med = new Checkbox("Median");
	
    public Visualizer(gelf.model.elements.Element e, SubWindow w, Project p, int width, int height) {
		super(e, p, width, height);
        this.subWindow = w;
		//style
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		initCellRepresentation(e, w, width, height);
		
        //diagram display panel
		this.lowerPanel = new Panel(width, height);
		this.lowerPanel.setLayout(new BorderLayout());
		this.lowerPanel.setBackground(ColorTheme.subsection);
		this.lowerPanel.setVisible(true);

		//diagram panel
		this.diagramPanel = new Panel(500, 500);
		this.diagramPanel.setBackground(ColorTheme.text);
		this.diagramPanel.setLayout(null);
		this.diagramPanel.setOpaque(true);
		this.diagramPanel.setVisible(true);
		this.lowerPanel.add(this.diagramPanel, BorderLayout.CENTER);
		
		//dropdowns
		dropdowns = new Panel(width, height);
		dropdowns.setLayout(new BoxLayout(dropdowns, BoxLayout.X_AXIS));
		dropdowns.setBackground(ColorTheme.section);
		dropdowns.setVisible(true);
		initDropdowns();
		this.lowerPanel.add(dropdowns, BorderLayout.PAGE_START);
		
		//statistics checkboxes
		Panel stats = new Panel(width, height);
		stats.setLayout(new BoxLayout(stats, BoxLayout.X_AXIS));
		stats.setBackground(ColorTheme.section);
		stats.setVisible(true);
		
		ItemListener checkboxListener = new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				updateStatDisplay();
			}
		};

		min.setVisible(true);
		min.addItemListener(checkboxListener);
		stats.add(min);

		max.setVisible(true);
		max.addItemListener(checkboxListener);
		stats.add(max);

		avg.setVisible(true);
		avg.addItemListener(checkboxListener);
		stats.add(avg);

		med.setVisible(true);
		med.addItemListener(checkboxListener);
		stats.add(med);

		this.lowerPanel.add(stats, BorderLayout.PAGE_END);
		//diagram viewport
		
		
		// Cell cell = (Cell)e;
		// float[] leakageValues = cell.getLeakages().getValues();
		// ArrayList<float[]> data = new ArrayList<float[]>();
		// data.add(leakageValues);
		

		

		this.add(this.lowerPanel);
		updateDiagram();
		this.revalidate();
		this.repaint();
		this.addComponentListener(this);
    }
	
	private void initCellRepresentation(gelf.model.elements.Element e, SubWindow w, int width, int height) {
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

		//dropdowns
		initDropdowns();
		updateDiagram();

    	this.revalidate();
    	this.repaint();
  	}

	private void initDropdowns() {
		this.dropdowns.removeAll();

		libDropdown = new JComboBox<Attribute>();
		cellDropdown = new JComboBox<Attribute>();
		outpinDropdown = new JComboBox<Attribute>();
		powerGroupDropdown = new JComboBox<PowerGroup>();
		timingTypeDropdown = new JComboBox<TimingType>();
		timingGroupDropdown = new JComboBox<TimingGroup>();
		timingSenseDropdown = new JComboBox<TimingSense>();

		libDropdown.setVisible(true);
		libDropdown.addItem(Attribute.INPUT_POWER);
		libDropdown.addItem(Attribute.OUTPUT_POWER);
		libDropdown.addItem(Attribute.TIMING);
		libDropdown.addItem(Attribute.LEAKAGE);
		libDropdown.addItem(Attribute.DEFAULT_LEAKAGE);

		cellDropdown.setVisible(true);
		cellDropdown.addItem(Attribute.INPUT_POWER);
		cellDropdown.addItem(Attribute.OUTPUT_POWER);
		cellDropdown.addItem(Attribute.TIMING);
		cellDropdown.addItem(Attribute.LEAKAGE);
		
		outpinDropdown.setVisible(true);
		outpinDropdown.addItem(Attribute.OUTPUT_POWER);
		outpinDropdown.addItem(Attribute.TIMING);
		
		powerGroupDropdown.setVisible(true);
		for(PowerGroup val : PowerGroup.values()) {
			powerGroupDropdown.addItem(val);
		}
		
		timingTypeDropdown.setVisible(true);
		for(TimingType val : TimingType.values()) {
			timingTypeDropdown.addItem(val);
		}
		
		timingGroupDropdown.setVisible(true);
		for(TimingGroup val : TimingGroup.values()) {
			timingGroupDropdown.addItem(val);
		}
		
		timingSenseDropdown.setVisible(true);
		for(TimingSense val : TimingSense.values()) {
			timingSenseDropdown.addItem(val);
		}
		
		//listeners
		ItemListener updateAttribute = new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(attribute == (Attribute)e.getItem()) return;
				//remove old dropdowns
				if(attribute == Attribute.INPUT_POWER) {
					dropdowns.remove(powerGroupDropdown);
				} else if(attribute == Attribute.OUTPUT_POWER) {
					dropdowns.remove(powerGroupDropdown);
				} else if(attribute == Attribute.TIMING) {
					dropdowns.remove(timingSenseDropdown);
					dropdowns.remove(timingTypeDropdown);
					dropdowns.remove(timingGroupDropdown);
				} else {
				}
				//update attribute
				attribute = (Attribute)e.getItem();
				updateAttributeSubDropdowns();
				updateDiagram();
			}
		};
		libDropdown.addItemListener(updateAttribute);
		cellDropdown.addItemListener(updateAttribute);
		outpinDropdown.addItemListener(updateAttribute);
		
		ItemListener updatePowerGroup = new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
				//dont include changes to the same
				if(powerGroup == (PowerGroup)e.getItem()) return;
				powerGroup = (PowerGroup)e.getItem();
				updateDiagram();
			}
		};
		powerGroupDropdown.addItemListener(updatePowerGroup);
		
		ItemListener updateTimingSense = new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
				//dont include changes to the same
				if(timingSense == (TimingSense)e.getItem()) return;
				timingSense = (TimingSense)e.getItem();
				updateDiagram();
			}
		};
		timingSenseDropdown.addItemListener(updateTimingSense);
		
		ItemListener updateTimingType = new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
				//dont include changes to the same
				if(timingType == (TimingType)e.getItem()) return;
				timingType = (TimingType)e.getItem();
				updateDiagram();
			}
		};
		timingTypeDropdown.addItemListener(updateTimingType);
		
		ItemListener updateTimingGroup = new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
				//dont include changes to the same
				if(timingGroup == (TimingGroup)e.getItem()) return;
				timingGroup = (TimingGroup)e.getItem();
				updateDiagram();
			}
		};
		timingGroupDropdown.addItemListener(updateTimingGroup);
		
		
		if(this.subWindow.getElement().getClass() == Library.class) {
			this.dropdowns.add(libDropdown);
			//update attribute
			attribute = (Attribute)libDropdown.getSelectedItem();
			updateAttributeSubDropdowns();
		} else if(this.subWindow.getElement().getClass() == Cell.class) {
			this.dropdowns.add(cellDropdown);
			//update attribute
			attribute = (Attribute)cellDropdown.getSelectedItem();
			updateAttributeSubDropdowns();
		} else if(this.subWindow.getElement().getClass() == InputPin.class) {
			this.dropdowns.add(powerGroupDropdown);
		} else {
			this.dropdowns.add(outpinDropdown);
			//update attribute
			attribute = (Attribute)outpinDropdown.getSelectedItem();
			updateAttributeSubDropdowns();
		}
		this.dropdowns.revalidate();
		this.dropdowns.repaint();
	}
	

	private void updateAttributeSubDropdowns() {
		//add appropriate dropdowns
		if(attribute == Attribute.INPUT_POWER) {
			dropdowns.add(powerGroupDropdown);
		} else if(attribute == Attribute.OUTPUT_POWER) {
			dropdowns.add(powerGroupDropdown);
		} else if(attribute == Attribute.TIMING) {
			dropdowns.add(timingSenseDropdown);
			dropdowns.add(timingTypeDropdown);
			dropdowns.add(timingGroupDropdown);
		} else {
		}
		dropdowns.revalidate();
		dropdowns.repaint();
		//updateDiagram();
	}


	//update diagram depending on dropdown status
	private void updateDiagram() {

		IDHmin = null;
		IDHmax = null;
		IDHavg = null;
		IDHmed = null;

		ArrayList<float[]> data = new ArrayList<float[]>();
		ArrayList<String[]> stringData = new ArrayList<String[]>();
		float[] values = null;
		String[] stringAr = null;
		
		if (this.subWindow.getElement().getClass() == Library.class) {
			Library lib = (Library)this.subWindow.getElement();
			if (attribute == Attribute.INPUT_POWER) {
				values = new float[lib.getCells().size()];
				stringAr = new String[lib.getCells().size()];
				int i = 0;
				
				Iterator<Cell> cellsIt = lib.getCells().iterator();
				while(cellsIt.hasNext()) {
					Cell curCell = cellsIt.next();
					if (!curCell.getAvailableInputPower().contains(powerGroup)) {
						values[i] = 0;
						stringAr[i] = curCell.getName();
					}
					else {
						curCell.calculateInPow();
						float value = 
								curCell.getInPowerStat().get(powerGroup).getAvg();
						values[i] = value;
						stringAr[i] = curCell.getName();
					}
					i++;
				}
			}
			
			else if (attribute == Attribute.OUTPUT_POWER) {
				values = new float[lib.getCells().size()];
				stringAr = new String[lib.getCells().size()];
				int i = 0;
				
				Iterator<Cell> cellsIt = lib.getCells().iterator();
				while(cellsIt.hasNext()) {
					Cell curCell = cellsIt.next();
					if (!curCell.getAvailableOutputPower().contains(powerGroup)) {
						values[i] = 0;
						stringAr[i] = curCell.getName();
					}
					else {
						curCell.calculateOutPow();
						float value = 
								curCell.getOutPowerStat().get(powerGroup).getAvg();
						values[i] = value;
						stringAr[i] = curCell.getName();
					}
					i++;
				}
			}
			
			else if (attribute == Attribute.DEFAULT_LEAKAGE) {
				values = new float[lib.getCells().size()];
				stringAr = new String[lib.getCells().size()];
				int i = 0;
				
				Iterator<Cell> cellsIt = lib.getCells().iterator();
				while(cellsIt.hasNext()) {
					Cell curCell = cellsIt.next();
					float value = 
							curCell.getDefaultLeakage();
					values[i] = value;
					stringAr[i] = curCell.getName();
					i++;
				}
			}
			
			else if (attribute == Attribute.LEAKAGE) {
				values = new float[lib.getCells().size()];
				stringAr = new String[lib.getCells().size()];
				int i = 0;
				
				Iterator<Cell> cellsIt = lib.getCells().iterator();
				while(cellsIt.hasNext()) {
					Cell curCell = cellsIt.next();
					float value = 
							curCell.getLeakages().getStats().getAvg();
					values[i] = value;
					stringAr[i] = curCell.getName();
					i++;
				}
			}
		}
		
		else if (this.subWindow.getElement().getClass() == Cell.class) {
			Cell cell = (Cell)this.subWindow.getElement();
			if (attribute == Attribute.INPUT_POWER) {
				values = new float[cell.getInPins().size()];
				int i = 0;
				
				Iterator<InputPin> inPinsIt = cell.getInPins().iterator();
				while(inPinsIt.hasNext()) {
					InputPin curInPin = inPinsIt.next();
					if (!curInPin.getAvailablePower().contains(powerGroup)) {
						values[i] = 0;
					}
					else {
						curInPin.calculatePower();
						ArrayList<InputPower> inPows = curInPin.getInputPowers();
						Iterator<InputPower> inPowIt = inPows.iterator();
						while (inPowIt.hasNext()) {
							InputPower curInPow = inPowIt.next();
							if (curInPow.getPowGroup() == powerGroup) {
								values[i] = curInPow.getStats().getAvg();
							}
						}
					}
					i++;
				}
			}
			
			else if (attribute == Attribute.OUTPUT_POWER) {
				values = new float[cell.getOutPins().size()];
				int i = 0;
				
				Iterator<OutputPin> outPinsIt = cell.getOutPins().iterator();
				while(outPinsIt.hasNext()) {
					OutputPin curOutPin = outPinsIt.next();
					if (!curOutPin.getAvailablePower().contains(powerGroup)) {
						values[i] = 0;
					}
					else {
						curOutPin.calculatePower();
						ArrayList<OutputPower> outPows = curOutPin.getOutputPowers();
						Iterator<OutputPower> outPowIt = outPows.iterator();
						while (outPowIt.hasNext()) {
							OutputPower curOutPow = outPowIt.next();
							if (curOutPow.getPowGroup() == powerGroup) {
								values[i] = curOutPow.getStats().getAvg();
							}
						}
					}
					i++;
				}
			}
			else if (attribute == Attribute.LEAKAGE) {
				values = new float[(int) Math.pow(2,(cell.getInPins().size()))];
				stringAr = new String[(int) Math.pow(2,(cell.getInPins().size()))];
				values = cell.getLeakages().getValues();
				cell.setOutputFunctions();
				stringAr = cell.getLeakages().getOutputFunctions();
			}
			
		}
		data.add(values);
		stringData.add(stringAr);
		IDiagramWizard wiz = new DiagramWizard();
		if (this.diagramPanel != null) {
			if (diagram != null) {
				diagram.removeFromContainer();
			}
			this.diagram = wiz.makeAndAttachBarChartWithDescriptions(this.diagramPanel, data, stringData);
		}
		updateStatDisplay();
	}

	private void updateStatDisplay() {
		IDiagramWizard wiz = new DiagramWizard();

		if(min.isSelected() && this.IDHmin == null) {
			this.IDHmin = wiz.addMinDisplayer(this.diagram);
		} else if(!min.isSelected() && this.IDHmin != null) {
			this.IDHmin.remove();
			this.IDHmin = null;
		}

		if(max.isSelected() && this.IDHmax == null) {
			this.IDHmax = wiz.addMaxDisplayer(this.diagram);
		} else if(!max.isSelected() && this.IDHmax != null) {
			this.IDHmax.remove();
			this.IDHmax = null;
		}

		if(avg.isSelected() && this.IDHavg == null) {
			this.IDHavg = wiz.addAvgDisplayer(this.diagram);
		} else if(!avg.isSelected() && this.IDHavg != null) {
			this.IDHavg.remove();
			this.IDHavg = null;
		}

		if(med.isSelected() && this.IDHmed == null) {
			this.IDHmed = wiz.addMedDisplayer(this.diagram);
		} else if(!med.isSelected() && this.IDHmed != null) {
			this.IDHmed.remove();
			this.IDHmed = null;
		}
	}

	@Override
	public void componentResized(ComponentEvent e) {
		super.componentResized(e);

		updateDiagram();
		// DiagramWizard wiz = new DiagramWizard();
		// this.diagram.removeFromContainer();
		// this.diagram = wiz.makeBarChart(this.diagramPanel, this.diagram.cloneData());
		// this.diagram.attachToContainer(this.diagramPanel);
		// this.diagram.refresh();
	}
}
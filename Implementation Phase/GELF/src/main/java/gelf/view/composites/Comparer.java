package gelf.view.composites;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;

import gelf.model.elements.Cell;
import gelf.model.elements.Element;
import gelf.model.elements.InputPin;
import gelf.model.elements.Library;
import gelf.model.elements.OutputPin;
import gelf.model.elements.Pin;
import gelf.model.elements.attributes.InputPower;
import gelf.model.elements.attributes.OutputPower;
import gelf.model.elements.attributes.PowerGroup;
import gelf.model.elements.attributes.TimingGroup;
import gelf.model.elements.attributes.TimingSense;
import gelf.model.elements.attributes.TimingType;
import gelf.model.project.Project;
import gelf.view.components.Checkbox;
import gelf.view.components.Panel;
import gelf.view.diagrams.DiagramWizard;
import gelf.view.diagrams.IDiagram;
import gelf.view.representation.LibraryComparePanel;
import gelf.view.representation.PinComparePanel;

/**
 * Comparer
 */
public class Comparer extends ElementManipulator {
	
	Panel upperPanel;
	Panel lowerPanel;
	Panel dropdowns;
	
	Panel diagramPanel;
	IDiagram diagram;
	
	private JComboBox<Attribute> libCellDropdown = new JComboBox<Attribute>();
	private JComboBox<Attribute> outpinDropdown = new JComboBox<Attribute>();
	private JComboBox<PowerGroup> powerGroupDropdown = new JComboBox<PowerGroup>();
	private JComboBox<TimingType> timingTypeDropdown = new JComboBox<TimingType>();
	private JComboBox<TimingGroup> timingGroupDropdown = new JComboBox<TimingGroup>();
	private JComboBox<TimingSense> timingSenseDropdown = new JComboBox<TimingSense>();
	private SubWindow subWindow;
	private ArrayList<Library> libraries;
	private ArrayList<Cell> cells;
	private ArrayList<InputPin> inputPins;
	private ArrayList<OutputPin> outputPins;
	private ArrayList<InputPin> selectedInputPins;
	
	
	//attributes enum for dropdowns
	public enum Attribute {
		INPUT_POWER("Input Power"),
		OUTPUT_POWER("Output Power"),
		TIMING("Timing");
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
	private static class DropdownStatus {
		public static Attribute attribute = Attribute.INPUT_POWER;		//for cell/library									||	output pin
		public static PowerGroup powerGroup = PowerGroup.FALL_POWER;	//for cell/library if attribute input/output power	||	output if attribute output power	||	input pin
		public static TimingSense timingSense;	//for cell/library if attribute timing				||	output if attribute timing
		public static TimingGroup timingGroup;	//for cell/library if attribute timing				||	output if attribute timing
		public static TimingType timingType;	//for cell/library if attribute timing				||	output if attribute timing
	}
	
    public Comparer(ArrayList<Element> elements, Project p, SubWindow w,  int width, int height){
        super(elements, p, width, height);
        upperPanel = new Panel(width, height);
        this.subWindow = w;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        initRepresentation();
        
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
		
		// Cell cell = (Cell)e;
		// float[] leakageValues = cell.getLeakages().getValues();
		// ArrayList<float[]> data = new ArrayList<float[]>();
		// data.add(leakageValues);
		
		//updateDiagram();
		
		//initCellRepresentation(e, w, width, height);

		this.add(this.lowerPanel);
		this.addComponentListener(this);
        
        this.setVisible(true);
    }
    
    private void initRepresentation() {
    	 upperPanel.setLayout(new FlowLayout());
         this.add(upperPanel);
         upperPanel.setVisible(true);
         
         if (super.elements.get(0) instanceof Library) {
         	ArrayList<Library> libraries = new ArrayList<Library>();
         	for (Element element: super.elements) {
         		libraries.add((Library)element);
         	}
         	upperPanel.add(new LibraryComparePanel(100, 100, subWindow, this, libraries));
     
         }
         else {
         	upperPanel.add(new PinComparePanel(100, 100, subWindow, this, super.elements));
         }
    }
    
    private void initDropdowns() {
		this.dropdowns.removeAll();
		libCellDropdown = new JComboBox<Attribute>();
		outpinDropdown = new JComboBox<Attribute>();
		powerGroupDropdown = new JComboBox<PowerGroup>();
		timingTypeDropdown = new JComboBox<TimingType>();
		timingGroupDropdown = new JComboBox<TimingGroup>();
		timingSenseDropdown = new JComboBox<TimingSense>();

		libCellDropdown.setVisible(true);
		libCellDropdown.addItem(Attribute.INPUT_POWER);
		libCellDropdown.addItem(Attribute.OUTPUT_POWER);
		libCellDropdown.addItem(Attribute.TIMING);
		
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
				//remove old dropdowns
				if(DropdownStatus.attribute == Attribute.INPUT_POWER) {
					dropdowns.remove(powerGroupDropdown);
				} else if(DropdownStatus.attribute == Attribute.OUTPUT_POWER) {
					dropdowns.remove(powerGroupDropdown);
				} else if(DropdownStatus.attribute == Attribute.TIMING) {
					dropdowns.remove(timingSenseDropdown);
					dropdowns.remove(timingTypeDropdown);
					dropdowns.remove(timingGroupDropdown);
				} else {
					System.out.println("Attribute selection error(dropdown removal failed)");
				}
				//update attribute
				DropdownStatus.attribute = (Attribute)e.getItem();
				updateAttributeSubDropdowns();
			}
		};
		libCellDropdown.addItemListener(updateAttribute);
		outpinDropdown.addItemListener(updateAttribute);
		
		ItemListener updatePowerGroup = new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
				System.out.println((PowerGroup)e.getItem());
				DropdownStatus.powerGroup = (PowerGroup)e.getItem();
				updateDiagram();
			}
		};
		powerGroupDropdown.addItemListener(updatePowerGroup);
		
		ItemListener updateTimingSense = new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
				DropdownStatus.timingSense = (TimingSense)e.getItem();
				updateDiagram();
			}
		};
		timingSenseDropdown.addItemListener(updateTimingSense);
		
		ItemListener updateTimingType = new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
				DropdownStatus.timingType = (TimingType)e.getItem();
				updateDiagram();
			}
		};
		timingTypeDropdown.addItemListener(updateTimingType);
		
		ItemListener updateTimingGroup = new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
				DropdownStatus.timingGroup = (TimingGroup)e.getItem();
				updateDiagram();
			}
		};
		timingGroupDropdown.addItemListener(updateTimingGroup);
		
		
		if(this.subWindow.getElement().getClass() == Library.class) {
			Library lib = (Library)this.subWindow.getElement();
			this.dropdowns.add(libCellDropdown);
			//update attribute
			DropdownStatus.attribute = (Attribute)libCellDropdown.getSelectedItem();
			updateAttributeSubDropdowns();
		} else if(this.subWindow.getElement().getClass() == Cell.class) {
			Cell cell = (Cell)this.subWindow.getElement();
			this.dropdowns.add(libCellDropdown);
			//update attribute
			DropdownStatus.attribute = (Attribute)libCellDropdown.getSelectedItem();
			updateAttributeSubDropdowns();
		} else if(this.subWindow.getElement().getClass() == InputPin.class) {
			InputPin inpin = (InputPin)this.subWindow.getElement();
			this.dropdowns.add(powerGroupDropdown);
		} else {
			OutputPin outpin = (OutputPin)this.subWindow.getElement();
			this.dropdowns.add(outpinDropdown);
			//update attribute
			DropdownStatus.attribute = (Attribute)outpinDropdown.getSelectedItem();
			updateAttributeSubDropdowns();
		}
		this.dropdowns.revalidate();
		this.dropdowns.repaint();
	}
    
    private void updateAttributeSubDropdowns() {
		//add appropriate dropdowns
		if(DropdownStatus.attribute == Attribute.INPUT_POWER) {
			dropdowns.add(powerGroupDropdown);
		} else if(DropdownStatus.attribute == Attribute.OUTPUT_POWER) {
			dropdowns.add(powerGroupDropdown);
		} else if(DropdownStatus.attribute == Attribute.TIMING) {
			dropdowns.add(timingSenseDropdown);
			dropdowns.add(timingTypeDropdown);
			dropdowns.add(timingGroupDropdown);
		} else {
			System.out.println("Attribute selection error(dropdown add failed)");
		}
		dropdowns.revalidate();
		dropdowns.repaint();
		updateDiagram();
	}
    
    public void setLibraries(ArrayList<Library> libraries) {
    	this.libraries = libraries;
    	updateDiagram();
    }
    
    public void setCells(ArrayList<Cell> cells) {
    	this.cells = cells;
    	updateDiagram();
    }
    
    public void setInputPins(ArrayList<InputPin> inputPins) {
    	this.inputPins = inputPins;
    	updateDiagram();
    }
    
    public void setOutputPins(ArrayList<OutputPin> outputPins) {
    	this.outputPins = outputPins;
    	updateDiagram();
    }
    
    public void setSelectedPins(ArrayList<InputPin> inputPins) {
    	this.selectedInputPins = inputPins;
    	updateDiagram();
    }
    
  //update diagram depending on dropdown status
  	private void updateDiagram() {
  		System.out.println(DropdownStatus.attribute);
  		System.out.println(DropdownStatus.powerGroup);

  		ArrayList<float[]> data = new ArrayList<float[]>();
  		ArrayList<String[]> stringData = new ArrayList<String[]>();
  		float[] values = null;
  		String[] stringAr = null;
  		
  		if (super.elements.get(0) instanceof Library) {
  			Library lib = (Library)this.subWindow.getElement();
  			if (DropdownStatus.attribute == Attribute.INPUT_POWER) {
  				values = new float[lib.getCells().size()];
  				stringAr = new String[lib.getCells().size()];
  				int i = 0;
  				
  				Iterator<Cell> cellsIt = lib.getCells().iterator();
  				while(cellsIt.hasNext()) {
  					Cell curCell = cellsIt.next();
  					if (!curCell.getAvailableInputPower().contains(DropdownStatus.powerGroup)) {
  						values[i] = 0;
  						stringAr[i] = curCell.getName();
  					}
  					else {
  						curCell.calculateInPow();
  						float value = 
  								curCell.getInPowerStat().get(DropdownStatus.powerGroup).getAvg();
  						values[i] = value;
  						stringAr[i] = curCell.getName();
  					}
  					i++;
  				}
  			}
  			
  			else if (DropdownStatus.attribute == Attribute.OUTPUT_POWER) {
  				values = new float[lib.getCells().size()];
  				stringAr = new String[lib.getCells().size()];
  				int i = 0;
  				
  				Iterator<Cell> cellsIt = lib.getCells().iterator();
  				while(cellsIt.hasNext()) {
  					Cell curCell = cellsIt.next();
  					if (!curCell.getAvailableOutputPower().contains(DropdownStatus.powerGroup)) {
  						values[i] = 0;
  						stringAr[i] = curCell.getName();
  					}
  					else {
  						curCell.calculateOutPow();
  						float value = 
  								curCell.getOutPowerStat().get(DropdownStatus.powerGroup).getAvg();
  						values[i] = value;
  						stringAr[i] = curCell.getName();
  					}
  					i++;
  				}
  			}
  		}
  		
  		else if (this.subWindow.getElement().getClass() == Cell.class) {
  			Cell cell = (Cell)this.subWindow.getElement();
  			if (DropdownStatus.attribute == Attribute.INPUT_POWER) {
  				values = new float[cell.getInPins().size()];
  				int i = 0;
  				
  				Iterator<InputPin> inPinsIt = cell.getInPins().iterator();
  				while(inPinsIt.hasNext()) {
  					InputPin curInPin = inPinsIt.next();
  					if (!curInPin.getAvailablePower().contains(DropdownStatus.powerGroup)) {
  						values[i] = 0;
  					}
  					else {
  						curInPin.calculatePower();
  						ArrayList<InputPower> inPows = curInPin.getInputPowers();
  						Iterator<InputPower> inPowIt = inPows.iterator();
  						while (inPowIt.hasNext()) {
  							InputPower curInPow = inPowIt.next();
  							if (curInPow.getPowGroup() == DropdownStatus.powerGroup) {
  								values[i] = curInPow.getStats().getAvg();
  							}
  						}
  					}
  					i++;
  				}
  			}
  			
  			else if (DropdownStatus.attribute == Attribute.OUTPUT_POWER) {
  				values = new float[cell.getOutPins().size()];
  				int i = 0;
  				
  				Iterator<OutputPin> outPinsIt = cell.getOutPins().iterator();
  				while(outPinsIt.hasNext()) {
  					OutputPin curOutPin = outPinsIt.next();
  					if (!curOutPin.getAvailablePower().contains(DropdownStatus.powerGroup)) {
  						values[i] = 0;
  					}
  					else {
  						curOutPin.calculatePower();
  						ArrayList<OutputPower> outPows = curOutPin.getOutputPowers();
  						Iterator<OutputPower> outPowIt = outPows.iterator();
  						while (outPowIt.hasNext()) {
  							OutputPower curOutPow = outPowIt.next();
  							if (curOutPow.getPowGroup() == DropdownStatus.powerGroup) {
  								values[i] = curOutPow.getStats().getAvg();
  							}
  						}
  					}
  					i++;
  				}
  			}
  			
  		}
  		data.add(values);
  		stringData.add(stringAr);
  		DiagramWizard wiz = new DiagramWizard();
  		if (this.diagramPanel != null) {
  			if (diagram != null) {
  				diagram.removeFromContainer();
  			}
  			this.diagram = wiz.makeAndAttachBarChartWithDescriptions(this.diagramPanel,
  					data, stringData);		
  		}
  	}
  	
  	@Override
	public void componentResized(ComponentEvent e) {
		super.componentResized(e);
		
		// DiagramWizard wiz = new DiagramWizard();
		// this.diagram.removeFromContainer();
		// this.diagram = wiz.makeBarChart(this.diagramPanel, this.diagram.cloneData());
		// this.diagram.attachToContainer(this.diagramPanel);
		// this.diagram.refresh();
	}
    
}

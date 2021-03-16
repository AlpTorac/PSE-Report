package gelf.view.composites;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;

import gelf.model.elements.Cell;
import gelf.model.elements.Element;
import gelf.model.elements.InputPin;
import gelf.model.elements.Library;
import gelf.model.elements.OutputPin;
import gelf.model.elements.Pin;
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
import gelf.view.components.Checkbox;
import gelf.view.components.Panel;
import gelf.view.diagrams.DiagramWizard;
import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.IDiagramWizard;
import gelf.view.diagrams.type.BarChart;
import gelf.view.diagrams.type.HeatMap;
import gelf.view.diagrams.type.Histogram;
import gelf.view.representation.LibraryComparePanel;
import gelf.view.representation.PinComparePanel;
import gelf.view.diagrams.IDiagramViewHelper;

/**
 * Comparer
 */
public class Comparer extends ElementManipulator {
	
	Panel upperPanel;
	Panel lowerPanel;
	Panel dropdowns;
	
	Panel diagramPanel;
	IDiagram diagram;
	
	IDiagramViewHelper IDHmin;
	IDiagramViewHelper IDHmax;
	IDiagramViewHelper IDHavg;
	IDiagramViewHelper IDHmed;
	
	private JComboBox<Attribute> libDropdown = new JComboBox<Attribute>();
	private JComboBox<Attribute> cellDropdown = new JComboBox<Attribute>();
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
	private ArrayList<Element> elements;
	
	//attributes enum for dropdowns
	public enum Attribute {
		LEAKAGE("Leakage"),
		DEFAULT_LEAKAGE("Default Leakage"),
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
	public Attribute attribute = Attribute.values()[0]; 	//for cell/library									||	output pin
	public PowerGroup powerGroup = PowerGroup.values()[0];	//for cell/library if attribute input/output power	||	output if attribute output power	||	input pin
	public TimingSense timingSense = TimingSense.values()[0];	//for cell/library if attribute timing				||	output if attribute timing
	public TimingGroup timingGroup = TimingGroup.values()[0];	//for cell/library if attribute timing				||	output if attribute timing
	public TimingType timingType = TimingType.values()[0];	//for cell/library if attribute timing				||	output if attribute timing
	private Checkbox min = new Checkbox("Minimum");
	private Checkbox max = new Checkbox("Maximum");
	private Checkbox avg = new Checkbox("Average");
	private Checkbox med = new Checkbox("Median");
	
    public Comparer(ArrayList<Element> elements, Project p, SubWindow w,  int width, int height){
        super(elements, p, width, height);
        this.elements = elements;
        upperPanel = new Panel(width, height);
        this.subWindow = w;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        libraries = new ArrayList<Library>();
        cells = new ArrayList<Cell>();
        inputPins= new ArrayList<InputPin>();
        outputPins = new ArrayList<OutputPin>();
        selectedInputPins = new ArrayList<InputPin>();
       
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

		libDropdown = new JComboBox<Attribute>();
		cellDropdown = new JComboBox<Attribute>();
		outpinDropdown = new JComboBox<Attribute>();
		powerGroupDropdown = new JComboBox<PowerGroup>();
		timingTypeDropdown = new JComboBox<TimingType>();
		timingGroupDropdown = new JComboBox<TimingGroup>();
		timingSenseDropdown = new JComboBox<TimingSense>();

		libDropdown.setVisible(true);
		libDropdown.addItem(Attribute.LEAKAGE);
		libDropdown.addItem(Attribute.DEFAULT_LEAKAGE);
		libDropdown.addItem(Attribute.INPUT_POWER);
		libDropdown.addItem(Attribute.OUTPUT_POWER);
		libDropdown.addItem(Attribute.TIMING);

		cellDropdown.setVisible(true);
		cellDropdown.addItem(Attribute.LEAKAGE);
		cellDropdown.addItem(Attribute.INPUT_POWER);
		cellDropdown.addItem(Attribute.OUTPUT_POWER);
		cellDropdown.addItem(Attribute.TIMING);
		
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
		
		enableCheckboxes();
		if(elements.get(0) instanceof Library) {
			this.dropdowns.add(libDropdown);
			//update attribute
			attribute = (Attribute)libDropdown.getSelectedItem();
			updateAttributeSubDropdowns();
		} else if(elements.get(0) instanceof Cell) {
			this.dropdowns.add(cellDropdown);
			//update attribute
			attribute = (Attribute)cellDropdown.getSelectedItem();
			updateAttributeSubDropdowns();
		} else if(elements.get(0) instanceof InputPin) {
			this.dropdowns.add(powerGroupDropdown);
		} else {
			this.dropdowns.add(outpinDropdown);
			//update attribute
			attribute = (Attribute)outpinDropdown.getSelectedItem();
			updateAttributeSubDropdowns();
			disableCheckboxes();
		}
		this.dropdowns.revalidate();
		this.dropdowns.repaint();
	}
    
    private void disableCheckboxes() {
		min.setSelected(false);
		max.setSelected(false);
		avg.setSelected(false);
		med.setSelected(false);
		min.setEnabled(false);
		max.setEnabled(false);
		avg.setEnabled(false);
		med.setEnabled(false);
	}

	private void enableCheckboxes() {
		min.setEnabled(true);
		max.setEnabled(true);
		avg.setEnabled(true);
		med.setEnabled(true);
	}

    
    private void updateAttributeSubDropdowns() {
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

		IDHmin = null;
		IDHmax = null;
		IDHavg = null;
		IDHmed = null;
		IDiagram[] diagrams = new IDiagram[elements.size()];
		
		for(int i = 0; i < elements.size(); i++) {
			ArrayList<ArrayList<float[]>> datas = new ArrayList<ArrayList<float[]>>();
		ArrayList<float[]> data = new ArrayList<float[]>();
		ArrayList<String[]> stringData = new ArrayList<String[]>();
		float[] values = null;
		String[] stringAr = null;
		
		if (elements.get(0) instanceof Library) {
			Library lib = (Library)elements.get(i);
			if (attribute == Attribute.INPUT_POWER) {
				values = new float[lib.getCells().size()];
				stringAr = new String[lib.getCells().size()];
				int j = 0;
				
				Iterator<Cell> cellsIt = lib.getCells().iterator();
				while(cellsIt.hasNext()) {
					Cell curCell = cellsIt.next();
					if (!curCell.getAvailableInputPower().contains(powerGroup)) {
						values[j] = 0;
						stringAr[j] = curCell.getName();
					}
					else {
						curCell.calculateInPow();
						float value = 
								curCell.getInPowerStat().get(powerGroup).getAvg();
						values[j] = value;
						stringAr[j] = curCell.getName();
					}
					j++;
				}
			}
			
			else if (attribute == Attribute.OUTPUT_POWER) {
				values = new float[lib.getCells().size()];
				stringAr = new String[lib.getCells().size()];
				int j = 0;
				
				Iterator<Cell> cellsIt = lib.getCells().iterator();
				while(cellsIt.hasNext()) {
					Cell curCell = cellsIt.next();
					if (!curCell.getAvailableOutputPower().contains(powerGroup)) {
						values[j] = 0;
						stringAr[j] = curCell.getName();
					}
					else {
						curCell.calculateOutPow();
						float value = 
								curCell.getOutPowerStat().get(powerGroup).getAvg();
						values[j] = value;
						stringAr[j] = curCell.getName();
					}
					j++;
				}
			}
			
			else if (attribute == Attribute.TIMING) {
				values = new float[lib.getCells().size()];
				stringAr = new String[lib.getCells().size()];
				int j = 0;
				
				Iterator<Cell> cellsIt = lib.getCells().iterator();
				while(cellsIt.hasNext()) {
					Cell curCell = cellsIt.next();
					if (!curCell.getAvailableTimSen().contains(timingSense)) {
						values[j] = 0;
						stringAr[j] = curCell.getName();
					}
					else if (!curCell.getAvailableTimType().contains(timingType)) {
						values[j] = 0;
						stringAr[j] = curCell.getName();
					}
					else if (!curCell.getAvailableTimGr().contains(timingGroup)) {
						values[j] = 0;
						stringAr[j] = curCell.getName();
					}
					
					else {
						curCell.calculateTiming();
						
						for (Map.Entry<TimingKey, Stat> entry : curCell.getTimingStat().entrySet()) {
						    TimingKey key = entry.getKey();
						    if (key.getTimSense() == timingSense && key.getTimType() == timingType
						    		&& key.getTimGroup() == timingGroup) {
						    	values[j] = entry.getValue().getAvg();
						    }   
						}
						stringAr[j] = curCell.getName();
					}
					j++;
				}
			}
			
			else if (attribute == Attribute.DEFAULT_LEAKAGE) {
				values = new float[lib.getCells().size()];
				stringAr = new String[lib.getCells().size()];
				int j = 0;
				
				Iterator<Cell> cellsIt = lib.getCells().iterator();
				while(cellsIt.hasNext()) {
					Cell curCell = cellsIt.next();
					float value = 
							curCell.getDefaultLeakage();
					values[j] = value;
					stringAr[j] = curCell.getName();
					j++;
				}
			}
			
			else if (attribute == Attribute.LEAKAGE) {
				values = new float[lib.getCells().size()];
				stringAr = new String[lib.getCells().size()];
				int j = 0;
				
				Iterator<Cell> cellsIt = lib.getCells().iterator();
				while(cellsIt.hasNext()) {
					Cell curCell = cellsIt.next();
					float value = 
							curCell.getLeakages().getStats().getAvg();
					values[j] = value;
					stringAr[j] = curCell.getName();
					j++;
				}
			}
			data.add(values);
			stringData.add(stringAr);
			IDiagramWizard wiz = new DiagramWizard();
			if (this.diagramPanel != null) {
				diagrams[i] = wiz.makeBarChartWithDescriptions(this.diagramPanel, data, stringData);
			}
			updateStatDisplay();
		}
		
		else if (elements.get(0) instanceof Cell) {
			Cell cell = (Cell)elements.get(i);
			
			if (attribute == Attribute.INPUT_POWER) {
				values = new float[cell.getInPins().size()];
				stringAr = new String[cell.getInPins().size()];
				int j = 0;
				
				Iterator<InputPin> inPinsIt = cell.getInPins().iterator();
				while(inPinsIt.hasNext()) {
					InputPin curInPin = inPinsIt.next();
					if (!curInPin.getAvailablePower().contains(powerGroup)) {
						values[j] = 0;
					}
					else {
						curInPin.calculatePower();
						ArrayList<InputPower> inPows = curInPin.getInputPowers();
						Iterator<InputPower> inPowIt = inPows.iterator();
						while (inPowIt.hasNext()) {
							InputPower curInPow = inPowIt.next();
							if (curInPow.getPowGroup() == powerGroup) {
								values[j] = curInPow.getStats().getAvg();
							}
						}
					}
					stringAr[j] = curInPin.getName();
					j++;
				}
			}
			
			else if (attribute == Attribute.OUTPUT_POWER) {
				values = new float[cell.getOutPins().size()];
				stringAr = new String[cell.getOutPins().size()];
				int j = 0;
				
				Iterator<OutputPin> outPinsIt = cell.getOutPins().iterator();
				while(outPinsIt.hasNext()) {
					OutputPin curOutPin = outPinsIt.next();
					if (!curOutPin.getAvailablePower().contains(powerGroup)) {
						values[j] = 0;
					}
					else {
						curOutPin.calculatePower();
						ArrayList<OutputPower> outPows = curOutPin.getOutputPowers();
						Iterator<OutputPower> outPowIt = outPows.iterator();
						while (outPowIt.hasNext()) {
							OutputPower curOutPow = outPowIt.next();
							if (curOutPow.getPowGroup() == powerGroup) {
								values[j] = curOutPow.getStats().getAvg();
							}
						}
					}
					stringAr[j] = curOutPin.getName();
					j++;
				}
			}
			
			else if (attribute == Attribute.TIMING) {
				values = new float[cell.getOutPins().size()];
				stringAr = new String[cell.getOutPins().size()];
				int j = 0;
				
				Iterator<OutputPin> outPinsIt = cell.getOutPins().iterator();
				while(outPinsIt.hasNext()) {
					OutputPin curOutPin = outPinsIt.next();
					if (!curOutPin.getAvailableTimSen().contains(timingSense)) {
						values[j] = 0;
						stringAr[j] = curOutPin.getName();
					}
					else if (!curOutPin.getAvailableTimType().contains(timingType)) {
						values[j] = 0;
						stringAr[j] = curOutPin.getName();
					}
					else if (!curOutPin.getAvailableTimGr().contains(timingGroup)) {
						values[j] = 0;
						stringAr[j] = curOutPin.getName();
					}
					
					else {
						curOutPin.calculateTiming();
						ArrayList<Timing> timings = curOutPin.getTimings();
						Iterator<Timing> timIt = timings.iterator();
						while (timIt.hasNext()) {		
							Timing curTim = timIt.next();
							if (timingSense == curTim.getTimSense() &&
									timingType == curTim.getTimType() &&
									timingGroup == curTim.getTimGroup()) {
								values[j] = curTim.getStats().getAvg();
								stringAr[j] = curOutPin.getName();
							}
						}
					}
					j++;
				}
			}
			
			else if (attribute == Attribute.LEAKAGE) {
				values = new float[(int) Math.pow(2,(cell.getInPins().size()))];
				stringAr = new String[(int) Math.pow(2,(cell.getInPins().size()))];
				values = cell.getLeakages().getValues();
				cell.setOutputFunctions();
				stringAr = cell.getLeakages().getOutputFunctions();
			}	
			data.add(values);
			stringData.add(stringAr);
			IDiagramWizard wiz = new DiagramWizard();
			if (this.diagramPanel != null) {
				diagrams[i] = wiz.makeBarChartWithDescriptions(this.diagramPanel, data, stringData);
			}
			updateStatDisplay();
		}
		else if (elements.get(0) instanceof InputPin) {
			InputPin inPin = (InputPin)elements.get(i);
			float[] index1 = null;	
			if (!inPin.getAvailablePower().contains(powerGroup)) {
				return;
			}
			
			Iterator<InputPower> inPowIt = inPin.getInputPowers().iterator();
			while (inPowIt.hasNext()) {
				InputPower curInPow = inPowIt.next();
				if (curInPow.getPowGroup() == powerGroup) {
					values = curInPow.getValues();
					index1 = curInPow.getIndex1();
				}
			}
			data.add(index1);
			data.add(values);
			IDiagramWizard wiz = new DiagramWizard();
			if (this.diagramPanel != null) {
				diagrams[i] = wiz.makeHistogram(this.diagramPanel, data);
			}
			updateStatDisplay();
			
		}
		
		else if (elements.get(0) instanceof OutputPin) {
			OutputPin outPin = (OutputPin)elements.get(i);
			values = null;
			float[] index1 = null;
			float[] index2 = null;
			
			if (selectedInputPins.isEmpty()) {
				if (this.diagramPanel != null) {
					if (diagram != null) {
						diagram.removeFromContainer();
						diagram = null;
					}
				}
				return;
			}
			if (attribute == Attribute.OUTPUT_POWER) {
				if (!outPin.getAvailablePower().contains(powerGroup)) {
					return;
				}
				
				Iterator<OutputPower> outPowIt = outPin.getOutputPowers().iterator();
				while (outPowIt.hasNext()) {
					OutputPower curOutPow = outPowIt.next();
					if (curOutPow.getPowGroup() == powerGroup && 
							selectedInputPins.contains(curOutPow.getRelatedPin())) {
						values = new float[curOutPow.getIndex1().length * curOutPow.getIndex2().length];
					    index1 = curOutPow.getIndex1();
						index2 = curOutPow.getIndex2();
						data.add(index1);
						data.add(index2);
						for (int j = 0; j < curOutPow.getIndex1().length; j++) {
							data.add(curOutPow.getValues()[j]);
						}
					}
				}
				datas.add(data);
				IDiagramWizard wiz = new DiagramWizard();
				if (this.diagramPanel != null) {
					if (diagram != null) {
						diagram.removeFromContainer();
						diagram = null;
					}
					
				}
				updateStatDisplay();
			}
			
			else if (attribute == Attribute.TIMING) {
				if (!outPin.getAvailableTimSen().contains(timingSense)) {
					return;
				}
				if (!outPin.getAvailableTimType().contains(timingType)) {
					return;
				}
				if (!outPin.getAvailableTimGr().contains(timingGroup)) {
					return;
				}
				
				Iterator<Timing> timIt = outPin.getTimings().iterator();
				while (timIt.hasNext()) {
					Timing curTim = timIt.next();
					if (curTim.getTimSense() == timingSense && curTim.getTimType() == timingType
							&& curTim.getTimGroup() == timingGroup && 
									selectedInputPins.contains(curTim.getRelatedPin())) {
						values = new float[curTim.getIndex1().length * curTim.getIndex2().length];
					    index1 = curTim.getIndex1();
						index2 = curTim.getIndex2();
						data.add(index1);
						data.add(index2);
						for (int j = 0; j < curTim.getIndex1().length; j++) {
							data.add(curTim.getValues()[j]);
						}
					}
				}
				datas.add(data);
				IDiagramWizard wiz = new DiagramWizard();
				if (this.diagramPanel != null) {
					if (diagram != null) {
						diagram.removeFromContainer();
					}
					
				}
				updateStatDisplay();
			}
			
			
		}
		
	}
		DiagramWizard wiz = new DiagramWizard();
		if (diagrams[0] instanceof BarChart) {
			BarChart[] barcharts = new BarChart[this.elements.size()];
			for(int i = 0; i < elements.size(); i++) {
				barcharts[i] = (BarChart) diagrams[i];
			}
			if (this.diagramPanel != null) {
				if (this.diagram != null) {
					this.diagram.removeFromContainer();
				}
			}
			this.diagram = wiz.overlayAndAttachBarCharts(this.diagramPanel, barcharts);
		}
		
		else if (diagrams[0] instanceof Histogram) {
			Histogram[] histograms = new Histogram[this.elements.size()];
			for(int i = 0; i < elements.size(); i++) {
				histograms[i] = (Histogram) diagrams[i];
			}
			if (this.diagramPanel != null) {
				if (this.diagram != null) {
					this.diagram.removeFromContainer();
				}
			}
			this.diagram = wiz.overlayAndAttachHistograms(this.diagramPanel, histograms);
		}
		
		else if (elements.get(0) instanceof OutputPin){
			ArrayList<float[]> newData = new ArrayList<float[]>();
			
			/*for () {
				
				
			}*/
			this.diagram = wiz.makeAndAttachHeatMap(this.diagramPanel, newData);
			
			
		}
		
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

	public void update() {
		updateDiagram();
		this.revalidate();
		this.repaint();
	}
}

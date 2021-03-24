package gelf.view.composites;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;

import gelf.controller.listeners.ScaleListener;
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
import gelf.model.project.Updatable;
import gelf.view.components.Button;
import gelf.view.components.Checkbox;
import gelf.view.components.Label;
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
public class Visualizer extends ElementManipulator implements Updatable, ComponentListener {
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
	private JComboBox<PowerGroup> inputPowerDropdown = new JComboBox<PowerGroup>();
	private JComboBox<PowerGroup> outputPowerDropdown = new JComboBox<PowerGroup>();
	private JComboBox<TimingType> timingTypeDropdown = new JComboBox<TimingType>();
	private JComboBox<TimingGroup> timingGroupDropdown = new JComboBox<TimingGroup>();
	private JComboBox<TimingSense> timingSenseDropdown = new JComboBox<TimingSense>();
    private InputPin selectedPin;
    public Timing selectedTim;
    public OutputPower selectedOutPow;
    public InputPower selectedInPow;
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
	private String[] units = new String[] {"N/A", "N/A", "N/A", "N/A", "N/A", "N/A"};
	private float scaleValue;
	private boolean isScaled;
    private Button scaleButton = new Button("Scale");
    private Label xAxisLabel = new Label();
    private Label yAxisLabel = new Label();
    private Label zAxisLabel = new Label();
	
    public Visualizer(Element e, SubWindow w, Project p, int width, int height) {
		super(e, p, width, height);
        this.subWindow = w;
		//style
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		p.addUpdatable(this);
		
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
        
        scaleButton.setVisible(true);
		scaleButton.addActionListener(new ScaleListener(this));
		scaleButton.setBackground(new Color(0.2f, 0.2f, 0.2f));
		scaleButton.setForeground(Color.WHITE);
		stats.add(scaleButton);
		
		xAxisLabel.setVisible(true);
		xAxisLabel.setForeground(Color.WHITE);
		stats.add(xAxisLabel);
		
		yAxisLabel.setVisible(true);
		yAxisLabel.setForeground(Color.WHITE);
		stats.add(yAxisLabel);

		zAxisLabel.setVisible(true);
		zAxisLabel.setForeground(Color.WHITE);
		stats.add(zAxisLabel);

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
	
	private void initCellRepresentation(Element e, SubWindow w, int width, int height) {
		//cell display
        upperPanel = new Panel(width, height);
		upperPanel.setBackground(ColorTheme.subsubsection);
        upperPanel.setLayout(new FlowLayout());

        dataPanel = new DataPanel(100, 100, e);
		dataPanel.setAlignmentY(CENTER_ALIGNMENT);
		
        if (e instanceof Library) {
			upperPanel.add(new LibraryPanel(100, 100, (Library)e, w));
	    }
	    else {
			upperPanel.add(new CellPanel(150, 150, e, w, this, dataPanel));    
	    }
		
		upperPanel.add(dataPanel);
		this.add(upperPanel);
	} 
    public Element getElement() {
    	return this.element;
    }
    public void setElement(Element e) {
		this.element = e;

    	upperPanel.removeAll();
    	dataPanel = new DataPanel(100, 100, e);
    	if (e instanceof Library ) {
    		upperPanel.add(new LibraryPanel(100, 100, (Library)e, subWindow));
    	}
    	else {
    		upperPanel.add(new CellPanel(100, 100, e, subWindow, this, dataPanel)); 
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
		inputPowerDropdown = new JComboBox<PowerGroup>();
		outputPowerDropdown = new JComboBox<PowerGroup>();
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
		
		ArrayList<PowerGroup> availInputPower = new ArrayList<PowerGroup>();
		ArrayList<PowerGroup> availOutputPower = new ArrayList<PowerGroup>();
		ArrayList<TimingGroup> availTimGr = new ArrayList<TimingGroup>();
		ArrayList<TimingSense> availTimSen = new ArrayList<TimingSense>();
		ArrayList<TimingType> availTimType = new ArrayList<TimingType>();

		if(this.element.getClass() == Library.class) {
			availInputPower = ((Library)element).getAvailableInputPower();
			availOutputPower = ((Library)element).getAvailableOutputPower();
			availTimGr = ((Library)element).getAvailableTimGr();
			availTimSen = ((Library)element).getAvailableTimSen();
			availTimType = ((Library)element).getAvailableTimType();
			units = ((Library)element).getUnits();
		} else if(this.element.getClass() == Cell.class) {
			availInputPower = ((Cell)element).getAvailableInputPower();
			availOutputPower = ((Cell)element).getAvailableOutputPower();
			availTimGr = ((Cell)element).getAvailableTimGr();
			availTimSen = ((Cell)element).getAvailableTimSen();
			availTimType = ((Cell)element).getAvailableTimType();
			units = ((Cell)element).getParentLibrary().getUnits();
		} else if(this.element.getClass() == OutputPin.class) {
			availOutputPower = ((OutputPin)element).getAvailablePower();
			availTimGr = ((OutputPin)element).getAvailableTimGr();
			availTimSen = ((OutputPin)element).getAvailableTimSen();
			availTimType = ((OutputPin)element).getAvailableTimType();
			units = ((OutputPin)element).getParent().getParentLibrary().getUnits();
		} else if(this.element.getClass() == InputPin.class) {
			availInputPower = ((InputPin)element).getAvailablePower();
			units = ((InputPin)element).getParent().getParentLibrary().getUnits();
		}

		inputPowerDropdown.setVisible(true);
		for(PowerGroup val : availInputPower) {
			inputPowerDropdown.addItem(val);
		}
		outputPowerDropdown.setVisible(true);
		for(PowerGroup val : availOutputPower) {
			outputPowerDropdown.addItem(val);
		}
		
		timingTypeDropdown.setVisible(true);
		for(TimingType val : availTimType) {
			timingTypeDropdown.addItem(val);
		}
		
		timingGroupDropdown.setVisible(true);
		for(TimingGroup val : availTimGr) {
			timingGroupDropdown.addItem(val);
		}
		
		timingSenseDropdown.setVisible(true);
		for(TimingSense val : availTimSen) {
			timingSenseDropdown.addItem(val);
		}
		if(this.element.getClass() == Library.class || this.element.getClass() == Cell.class || this.element.getClass() == OutputPin.class) {
			timingSense = availTimSen.get(0);
			timingGroup = availTimGr.get(0);
			timingType = availTimType.get(0);
		}

		//listeners
		ItemListener updateAttribute = new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(attribute == (Attribute)e.getItem()) return;
				//remove old dropdowns
				if(attribute == Attribute.INPUT_POWER) {
					dropdowns.remove(inputPowerDropdown);
				} else if(attribute == Attribute.OUTPUT_POWER) {
					dropdowns.remove(outputPowerDropdown);
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
		//init sub dropdown values
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
		inputPowerDropdown.addItemListener(updatePowerGroup);
		outputPowerDropdown.addItemListener(updatePowerGroup);
		
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
			this.dropdowns.add(inputPowerDropdown);
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
		//add appropriate dropdowns
		if(attribute == Attribute.INPUT_POWER) {
			dropdowns.add(inputPowerDropdown);
		} else if(attribute == Attribute.OUTPUT_POWER) {
			dropdowns.add(outputPowerDropdown);
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
    
    public void updateDiagram(InputPin selectedPin) {
		this.selectedPin = selectedPin;
		updateDiagram();
	}
    
	//update diagram depending on dropdown status
	public void updateDiagram() {

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
				//if (isScaled) lib.scaleInputPower(scaleValue); isScaled = false;
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
				xAxisLabel.setText("x_Axis: cells");
				yAxisLabel.setText("y-Axis: " + units[1]);
				zAxisLabel.setText("");
			}
			
			else if (attribute == Attribute.OUTPUT_POWER) {
				values = new float[lib.getCells().size()];
				stringAr = new String[lib.getCells().size()];
				int i = 0;
				//if (isScaled) lib.scaleOutputPower(scaleValue); isScaled = false;
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
				xAxisLabel.setText("x_Axis: cells");
				yAxisLabel.setText("y-Axis: " + units[1]);
				zAxisLabel.setText("");
			}
			
			else if (attribute == Attribute.TIMING) {
				values = new float[lib.getCells().size()];
				stringAr = new String[lib.getCells().size()];
				int i = 0;
				//if (isScaled) lib.scaleTiming(scaleValue); isScaled = false;
				Iterator<Cell> cellsIt = lib.getCells().iterator();
				while(cellsIt.hasNext()) {
					Cell curCell = cellsIt.next();
					if (!curCell.getAvailableTimSen().contains(timingSense)) {
						values[i] = 0;
						stringAr[i] = curCell.getName();
					}
					else if (!curCell.getAvailableTimType().contains(timingType)) {
						values[i] = 0;
						stringAr[i] = curCell.getName();
					}
					else if (!curCell.getAvailableTimGr().contains(timingGroup)) {
						values[i] = 0;
						stringAr[i] = curCell.getName();
					}
					
					else {
						curCell.calculateTiming();
						
						for (Map.Entry<TimingKey, Stat> entry : curCell.getTimingStat().entrySet()) {
						    TimingKey key = entry.getKey();
						    if (key.getTimSense() == timingSense && key.getTimType() == timingType
						    		&& key.getTimGroup() == timingGroup) {
						    	values[i] = entry.getValue().getAvg();
						    }   
						}
						stringAr[i] = curCell.getName();
					}
					i++;
				}
				xAxisLabel.setText("x_Axis: cells");
				yAxisLabel.setText("y-Axis: " + units[0]);
				zAxisLabel.setText("");
			}
			
			else if (attribute == Attribute.DEFAULT_LEAKAGE) {
				values = new float[lib.getCells().size()];
				stringAr = new String[lib.getCells().size()];
				int i = 0;
				//if (isScaled) lib.scaleDefaultLeakage(scaleValue); isScaled = false;
				Iterator<Cell> cellsIt = lib.getCells().iterator();
				while(cellsIt.hasNext()) {
					Cell curCell = cellsIt.next();
					curCell.calculate();
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
				//if (isScaled) {lib.scaleLeakages(scaleValue); isScaled = false;}
				Iterator<Cell> cellsIt = lib.getCells().iterator();
				while(cellsIt.hasNext()) {
					Cell curCell = cellsIt.next();
					curCell.calculate();
					float value = 
							curCell.getLeakages().getStats().getAvg();
					values[i] = value;
					stringAr[i] = curCell.getName();
					i++;
				}
				xAxisLabel.setText("x_Axis: cells");
				yAxisLabel.setText("y-Axis: " + units[5]);
				zAxisLabel.setText("");
			}
			data.add(values);
			stringData.add(stringAr);
			IDiagramWizard wiz = new DiagramWizard();
			if (this.diagramPanel != null) {
				if (diagram != null) {
					diagram.removeFromContainer();
					diagram = null;
				}
				this.diagram = wiz.makeAndAttachBarChartWithDescriptions(this.diagramPanel, data, stringData);
			}
			updateStatDisplay();
		}
		
		else if (this.subWindow.getElement().getClass() == Cell.class) {
			Cell cell = (Cell)this.subWindow.getElement();
			
			if (attribute == Attribute.INPUT_POWER) {
				values = new float[cell.getInPins().size()];
				stringAr = new String[cell.getInPins().size()];
				int i = 0;
				//if (isScaled) cell.scaleInputPower(scaleValue); isScaled = false;
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
					stringAr[i] = curInPin.getName();
					i++;
				}
				xAxisLabel.setText("x_Axis: pins");
				yAxisLabel.setText("y-Axis: " + units[1]);
				zAxisLabel.setText("");
			}
			
			else if (attribute == Attribute.OUTPUT_POWER) {
				values = new float[cell.getOutPins().size()];
				stringAr = new String[cell.getOutPins().size()];
				int i = 0;
				//if (isScaled) cell.scaleOutputPower(scaleValue); isScaled = false;
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
					stringAr[i] = curOutPin.getName();
					i++;
				}
				xAxisLabel.setText("x_Axis: pins");
				yAxisLabel.setText("y-Axis: " + units[1]);
				zAxisLabel.setText("");
			}
			
			else if (attribute == Attribute.TIMING) {
				values = new float[cell.getOutPins().size()];
				stringAr = new String[cell.getOutPins().size()];
				int i = 0;
				//if (isScaled) cell.scaleTiming(scaleValue); isScaled = false;
				Iterator<OutputPin> outPinsIt = cell.getOutPins().iterator();
				while(outPinsIt.hasNext()) {
					OutputPin curOutPin = outPinsIt.next();
					if (!curOutPin.getAvailableTimSen().contains(timingSense)) {
						values[i] = 0;
						stringAr[i] = curOutPin.getName();
					}
					else if (!curOutPin.getAvailableTimType().contains(timingType)) {
						values[i] = 0;
						stringAr[i] = curOutPin.getName();
					}
					else if (!curOutPin.getAvailableTimGr().contains(timingGroup)) {
						values[i] = 0;
						stringAr[i] = curOutPin.getName();
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
								values[i] = curTim.getStats().getAvg();
								stringAr[i] = curOutPin.getName();
							}
						}
					}
					i++;
				}
				xAxisLabel.setText("x_Axis: pins");
				yAxisLabel.setText("y-Axis: " + units[0]);
				zAxisLabel.setText("");
			}
			
			else if (attribute == Attribute.LEAKAGE) {
				values = new float[(int) Math.pow(2,(cell.getInPins().size()))];
				stringAr = new String[(int) Math.pow(2,(cell.getInPins().size()))];
				//if (isScaled) cell.getLeakages().scale(scaleValue); isScaled = false;
				values = cell.getLeakages().getValues();
				cell.setOutputFunctions();
				stringAr = cell.getLeakages().getOutputFunctions();
				if (xAxisLabel != null && yAxisLabel != null && zAxisLabel != null && units != null) {
				xAxisLabel.setText("x_Axis: input state power");
				yAxisLabel.setText("y-Axis: " + units[5]);
				zAxisLabel.setText("");
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
		else if (this.subWindow.getElement().getClass() == InputPin.class) {
			InputPin inPin = (InputPin)this.subWindow.getElement();
			float[] index1 = null;	
			if (!inPin.getAvailablePower().contains(powerGroup)) {
				return;
			}
			
			Iterator<InputPower> inPowIt = inPin.getInputPowers().iterator();
			while (inPowIt.hasNext()) {
				InputPower curInPow = inPowIt.next();
				if (curInPow.getPowGroup() == powerGroup) {
					//if (isScaled) curInPow.scale(scaleValue); isScaled = false;
					selectedInPow = curInPow;
					values = curInPow.getValues();
					index1 = curInPow.getIndex1();
				}
			}
			data.add(index1);
			data.add(values);
			IDiagramWizard wiz = new DiagramWizard();
			if (this.diagramPanel != null) {
				if (diagram != null) {
					diagram.removeFromContainer();
				}
				this.diagram = wiz.makeAndAttachHistogram(this.diagramPanel, data);
			}
			xAxisLabel.setText("x_Axis: input transition");
			yAxisLabel.setText("y-Axis: " + units[1]);
			zAxisLabel.setText("");
			updateStatDisplay();
		}
		
		else if (this.subWindow.getElement().getClass() == OutputPin.class) {
			OutputPin outPin = (OutputPin)this.subWindow.getElement();
			values = null;
			float[] index1 = null;
			float[] index2 = null;
			
			if (selectedPin == null) {
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
							curOutPow.getRelatedPin().getName().equals(this.selectedPin.getName())) {
						//if (isScaled) curOutPow.scale(scaleValue); isScaled = false;
						selectedOutPow = curOutPow;
						values = new float[curOutPow.getIndex1().length * curOutPow.getIndex2().length];
					    index1 = curOutPow.getIndex1();
						index2 = curOutPow.getIndex2();
						data.add(index1);
						data.add(index2);
						for (int i = 0; i < curOutPow.getIndex1().length; i++) {
							data.add(curOutPow.getValues()[i]);
						}
					}
				}
				IDiagramWizard wiz = new DiagramWizard();
				if (this.diagramPanel != null) {
					if (diagram != null) {
						diagram.removeFromContainer();
						diagram = null;
					}
					this.diagram = wiz.makeAndAttachHeatMap(this.diagramPanel, data);
				}
				xAxisLabel.setText("x_Axis: input transition");
				yAxisLabel.setText("y-Axis: output capacitance");
				zAxisLabel.setText("z-Axis: " + units[1]);
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
							curTim.getRelatedPin().getName().equals(this.selectedPin.getName())) {
						//if (isScaled) curTim.scale(scaleValue); isScaled = false;
						selectedTim = curTim;
						values = new float[curTim.getIndex1().length * curTim.getIndex2().length];
					    index1 = curTim.getIndex1();
						index2 = curTim.getIndex2();
						data.add(index1);
						data.add(index2);
						for (int i = 0; i < curTim.getIndex1().length; i++) {
							data.add(curTim.getValues()[i]);
						}
					}
				}
				IDiagramWizard wiz = new DiagramWizard();
				if (this.diagramPanel != null) {
					if (diagram != null) {
						diagram.removeFromContainer();
					}
					this.diagram = wiz.makeAndAttachHeatMap(this.diagramPanel, data);
				}
				xAxisLabel.setText("x_Axis: input transition");
				yAxisLabel.setText("y-Axis: output capacitance");
				zAxisLabel.setText("z-Axis: " + units[0]);
				updateStatDisplay();
			}
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
		updateDiagram();
		// DiagramWizard wiz = new DiagramWizard();
		// this.diagram.removeFromContainer();
		// this.diagram = wiz.makeBarChart(this.diagramPanel, this.diagram.cloneData());
		// this.diagram.attachToContainer(this.diagramPanel);
		// this.diagram.refresh();
	}
	
	@Override
	public void componentMoved(ComponentEvent e) {
	}

	@Override
	public void componentShown(ComponentEvent e) {
	}

	@Override
	public void componentHidden(ComponentEvent e) {
	}
	@Override
	public void update() {
		if (subWindow.getElement() instanceof Cell) {
			if (!((Cell)subWindow.getElement()).getParentLibrary().getCells().contains(((Cell)subWindow.getElement()))){
				subWindow.parent.removeSubWindow(subWindow);
				return;
			}
		}
		else if (subWindow.getElement() instanceof InputPin) {
			if (!((InputPin)subWindow.getElement()).getParent().getInPins().contains(((InputPin)subWindow.getElement()))){
				subWindow.parent.removeSubWindow(subWindow);
				return;
			}
		}
		else if (subWindow.getElement() instanceof OutputPin) {
			if (!((OutputPin)subWindow.getElement()).getParent().getOutPins().contains(((OutputPin)subWindow.getElement()))){
				subWindow.parent.removeSubWindow(subWindow);
				return;
			}
		}
		
		updateDiagram();
        this.setElement(this.element);
		this.revalidate();
		this.repaint();
	}
}

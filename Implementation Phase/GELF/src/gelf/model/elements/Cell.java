package gelf.model.elements;

import java.util.ArrayList;
import java.util.Iterator;

import gelf.model.elements.attributes.InputPower;
import gelf.model.elements.attributes.Leakage;
import gelf.model.elements.attributes.OutputPower;
import gelf.model.elements.attributes.PowerGroup;
import gelf.model.elements.attributes.Timing;
import gelf.model.elements.attributes.TimingGroup;
import gelf.model.elements.attributes.TimingKey;
import gelf.model.elements.attributes.TimingSense;
import gelf.model.elements.attributes.TimingType;

public class Cell extends HigherElement {
	private float[] index1;
    private float[] index2;
    private Library parentLibrary;
    private ArrayList<InputPin> inPins;
    private ArrayList<OutputPin> outPins;
    private Leakage leakages;
    private float defaultLeakage;
    
    public Cell(String name, float[] index1, float[] index2, Library parentLibrary,
    		ArrayList<InputPin> inPins, ArrayList<OutputPin> outPins,
    		Leakage leakages, float leakage) {
    	super.setName(name);
    	this.index1 = index1;
    	this.index2 = index2;
    	this.parentLibrary = parentLibrary;
    	this.inPins = inPins;
    	this.outPins = outPins;
    	this.leakages = leakages;
    	this.defaultLeakage = leakage;
    	calculate();
    }
    
	public float[] getIndex1() {
		return index1;
	}

	public void setIndex1(float[] index1) {
		this.index1 = index1;
	}

	public float[] getIndex2() {
		return index2;
	}

	public void setIndex2(float[] index2) {
		this.index2 = index2;
	}

	public Library getParentLibrary() {
		return parentLibrary;
	}

	public void setParentLibrary(Library parentLibrary) {
		this.parentLibrary = parentLibrary;
	}

	public ArrayList<InputPin> getInPins() {
		return inPins;
	}

	public void setInPins(ArrayList<InputPin> inPins) {
		this.inPins = inPins;
	}

	public ArrayList<OutputPin> getOutPins() {
		return outPins;
	}

	public void setOutPins(ArrayList<OutputPin> outPins) {
		this.outPins = outPins;
	}

	public Leakage getLeakages() {
		return leakages;
	}

	public void setLeakages(Leakage leakages) {
		this.leakages = leakages;
	}

	public float getDefaultLeakage() {
		return defaultLeakage;
	}

	public void setDefaultLeakage(float defaultLeakage) {
		this.defaultLeakage = defaultLeakage;
	}
	
	@Override
	public void calculate() {
		calculateInPow();
		calculateOutPow();
		calculateTiming();
		calculateLeakage();
	}
	
	public void calculateInPow() {
		
		Iterator<PowerGroup> avPowGrIt = availableInputPower.iterator();
		
		Iterator<InputPin> inPinIt = inPins.iterator();
		
		Iterator<InputPower> inPowIt = null;
		
		// traverse all available PowerGroups
		while(avPowGrIt.hasNext()) {
			
			// ArrayList toCalc to put InputPowers of the same PowerGroup in the same place
			ArrayList<InputPower> toCalc = new ArrayList<InputPower>();
			Iterator<InputPower> toCalcIt = toCalc.iterator();
			
			// traverse Input Pins
			while(inPinIt.hasNext()) {
				
				// traverse Input Powers of Input Pins
				inPowIt = inPinIt.next().getInputPowers().iterator();
				
				while(inPowIt.hasNext()) {
					
					// put in the list if the Input Power has the desired Power Group
					if(avPowGrIt.next() == inPowIt.next().getPowGroup()) {
						toCalc.add(inPowIt.next());
					}
				}
			}
			float min = 0;
			float max = 0;
			float avg = 0;
			float med = 0;
			
			// calculate the stats for the desired Power Group
			while(toCalcIt.hasNext()) {
				min = Math.min(min, toCalcIt.next().getStats().getMin());
				max = Math.max(max, toCalcIt.next().getStats().getMax());
				avg += toCalcIt.next().getStats().getAvg();
			}
			avg = avg / (float) toCalc.size();
			
			Stat stat = new Stat(min, max, avg, med);
			inPowerStat.put(avPowGrIt.next(), stat);
		}
	}
	
	public void calculateOutPow() {
		Iterator<PowerGroup> avPowGrIt = availableOutputPower.iterator();
		
		Iterator<OutputPin> outPinIt = outPins.iterator();
		
		Iterator<OutputPower> outPowIt = null;
		
		// traverse all available PowerGroups
		while(avPowGrIt.hasNext()) {
			
			// ArrayList toCalc to put OutputPowers of the same PowerGroup in the same place
			ArrayList<OutputPower> toCalc = new ArrayList<OutputPower>();
			Iterator<OutputPower> toCalcIt = toCalc.iterator();
			
			// traverse Output Pins
			while(outPinIt.hasNext()) {
				
				// traverse Output Powers of Input Pins
				outPowIt = outPinIt.next().getOutputPowers().iterator();
				
				while(outPowIt.hasNext()) {
					
					// put in the list if the Output Power has the desired Power Group
					if(avPowGrIt.next() == outPowIt.next().getPowGroup()) {
						toCalc.add(outPowIt.next());
					}
				}
			}
			float min = 0;
			float max = 0;
			float avg = 0;
			float med = 0;
			
			// calculate the stats for the desired Power Group
			while(toCalcIt.hasNext()) {
				min = Math.min(min, toCalcIt.next().getStats().getMin());
				max = Math.max(max, toCalcIt.next().getStats().getMax());
				avg += toCalcIt.next().getStats().getAvg();
			}
			avg = avg / (float) toCalc.size();
			
			Stat stat = new Stat(min, max, avg, med);
			outPowerStat.put(avPowGrIt.next(), stat);
		}
	}
	
	public void calculateTiming() {
		Iterator<TimingSense> avTimSenIt = availableTimSen.iterator();
		Iterator<TimingGroup> avTimGrIt = availableTimGr.iterator();
		Iterator<TimingType> avTimTypeIt = availableTimType.iterator();
		
		Iterator<OutputPin> outPinIt = outPins.iterator();
		
		Iterator<Timing> outTimIt = null;
		
		// traverse all available Timing Senses, Timing Groups, Timing Types
		while(avTimSenIt.hasNext()) {
			while(avTimGrIt.hasNext()) {
				while(avTimTypeIt.hasNext()) {
					
					// ArrayList toCalc to put Timings of the same sense, group, types
					ArrayList<Timing> toCalc = new ArrayList<Timing>();
					Iterator<Timing> toCalcIt = toCalc.iterator();
					
					// traverse Output Pins
					while(outPinIt.hasNext()) {
						
						// traverse timings of Output Pins
						outTimIt = outPinIt.next().getTimings().iterator();
						
						while(outTimIt.hasNext()) {
							
							// put in the list if the Input Power has the desired Power Group
							if(avTimSenIt.next() == outTimIt.next().getTimSense() &&
							   avTimGrIt.next() == outTimIt.next().getTimGroup() &&
							   avTimTypeIt.next() == outTimIt.next().getTimType()) {
								
								toCalc.add(outTimIt.next());
							}
						}
					}
					float min = 0;
					float max = 0;
					float avg = 0;
					float med = 0;
					
					// calculate the stats for the desired Power Group
					while(toCalcIt.hasNext()) {
						min = Math.min(min, toCalcIt.next().getStats().getMin());
						max = Math.max(max, toCalcIt.next().getStats().getMax());
						avg += toCalcIt.next().getStats().getAvg();
					}
					avg = avg / (float) toCalc.size();
					
					Stat stat = new Stat(min, max, avg, med);
					
					/**
					Map<TimingSense, Map<TimingGroup, Map<TimingType, Stat>>> map = 
							new HashMap<>();
					 **/
					
					// create a new key
					TimingKey key = new TimingKey(avTimSenIt.next(), avTimGrIt.next(), 
							avTimTypeIt.next());
					
					// put the stat of the timing in the map
					timingStat.put(key, stat);
				}
			}
		}
	}
	
	public void calculateLeakage() {
		leakages.calculate();
	}

}

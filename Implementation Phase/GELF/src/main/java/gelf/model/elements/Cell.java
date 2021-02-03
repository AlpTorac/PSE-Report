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
	
	public void interpolate(float[] index1, float[] index2) {
		Interpolator interpolator = new Interpolator();
		Iterator<InputPin> inPinIt = inPins.iterator();
		Iterator<OutputPin> outPinIt = outPins.iterator();
		
		/* Calculating new input powers of input pins */
		while(inPinIt.hasNext()) {
			InputPin curInPin = inPinIt.next();
			ArrayList<InputPower> inPows = curInPin.getInputPowers();
			Iterator<InputPower> inPowIt = inPows.iterator();
			while(inPowIt.hasNext()) {
				InputPower curInPow = inPowIt.next();
				float[] newValues = interpolator.interpolate(curInPow.getIndex1(),
						curInPow.getValues(), index1);
				curInPow.setIndex1(index1);
				curInPow.setValues(newValues);
			}
		}
		
		/* Calculating new output powers of output pins */
		while(outPinIt.hasNext()) {
			OutputPin curOutPin = outPinIt.next();
			ArrayList<OutputPower> outPows = curOutPin.getOutputPowers();
			Iterator<OutputPower> outPowIt = outPows.iterator();
			while(outPowIt.hasNext()) {
				OutputPower curOutPow = outPowIt.next();
				float[][] newValues = interpolator.interpolate(curOutPow.getIndex1(), 
						curOutPow.getIndex2(), curOutPow.getValues(), index1, index2);
				curOutPow.setIndex1(index1);
				curOutPow.setIndex2(index2);
				curOutPow.setValues(newValues);
			}
		}
		
		/* Calculating new timings of output pins */
		while(outPinIt.hasNext()) {
			OutputPin curOutPin = outPinIt.next();
			ArrayList<Timing> timings = curOutPin.getTimings();
			Iterator<Timing> timingIt = timings.iterator();
			while(timingIt.hasNext()) {
				Timing curTiming = timingIt.next();
				float[][] newValues = interpolator.interpolate(curTiming.getIndex1(), 
						curTiming.getIndex2(), curTiming.getValues(), index1, index2);
				curTiming.setIndex1(index1);
				curTiming.setIndex2(index2);
				curTiming.setValues(newValues);
			}
		}
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
			PowerGroup curPowGr = avPowGrIt.next();
			// ArrayList toCalc to put InputPowers of the same PowerGroup in the same place
			ArrayList<InputPower> toCalc = new ArrayList<InputPower>();
			Iterator<InputPower> toCalcIt = toCalc.iterator();
			
			// traverse Input Pins
			while(inPinIt.hasNext()) {
				InputPin curInPin = inPinIt.next();
				// traverse Input Powers of Input Pins
				inPowIt = curInPin.getInputPowers().iterator();
				
				while(inPowIt.hasNext()) {
					InputPower curInPow = inPowIt.next();
					// put in the list if the Input Power has the desired Power Group
					if(curPowGr == curInPow.getPowGroup()) {
						toCalc.add(curInPow);
					}
				}
			}
			float min = 0;
			float max = 0;
			float avg = 0;
			float med = 0;
			
			// calculate the stats for the desired Power Group
			while(toCalcIt.hasNext()) {
				InputPower curInPowCal = toCalcIt.next();
				min = Math.min(min, curInPowCal.getStats().getMin());
				max = Math.max(max, curInPowCal.getStats().getMax());
				avg += curInPowCal.getStats().getAvg();
			}
			avg = avg / (float) toCalc.size();
			
			Stat stat = new Stat(min, max, avg, med);
			inPowerStat.put(curPowGr, stat);
		}
	}
	
	public void calculateOutPow() {
		Iterator<PowerGroup> avPowGrIt = availableOutputPower.iterator();
		
		Iterator<OutputPin> outPinIt = outPins.iterator();
		
		Iterator<OutputPower> outPowIt = null;
		
		// traverse all available PowerGroups
		while(avPowGrIt.hasNext()) {
			PowerGroup curPowGr = avPowGrIt.next();
			// ArrayList toCalc to put OutputPowers of the same PowerGroup in the same place
			ArrayList<OutputPower> toCalc = new ArrayList<OutputPower>();
			Iterator<OutputPower> toCalcIt = toCalc.iterator();
			
			// traverse Output Pins
			while(outPinIt.hasNext()) {
				OutputPin curOutPin = outPinIt.next();
				// traverse Output Powers of Input Pins
				outPowIt = curOutPin.getOutputPowers().iterator();
				
				while(outPowIt.hasNext()) {
					OutputPower curOutPow = outPowIt.next();
					// put in the list if the Output Power has the desired Power Group
					if(curPowGr == curOutPow.getPowGroup()) {
						toCalc.add(curOutPow);
					}
				}
			}
			float min = 0;
			float max = 0;
			float avg = 0;
			float med = 0;
			
			// calculate the stats for the desired Power Group
			while(toCalcIt.hasNext()) {
				OutputPower curOutPowCal = toCalcIt.next();
				min = Math.min(min, curOutPowCal.getStats().getMin());
				max = Math.max(max, curOutPowCal.getStats().getMax());
				avg += curOutPowCal.getStats().getAvg();
			}
			avg = avg / (float) toCalc.size();
			
			Stat stat = new Stat(min, max, avg, med);
			outPowerStat.put(curPowGr, stat);
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
			TimingSense curTimSen = avTimSenIt.next();
			while(avTimGrIt.hasNext()) {
				TimingGroup curTimGr = avTimGrIt.next();
				while(avTimTypeIt.hasNext()) {
					TimingType curTimType = avTimTypeIt.next();
					// ArrayList toCalc to put Timings of the same sense, group, types
					ArrayList<Timing> toCalc = new ArrayList<Timing>();
					Iterator<Timing> toCalcIt = toCalc.iterator();
					
					// traverse Output Pins
					while(outPinIt.hasNext()) {
						OutputPin curOutPin = outPinIt.next();
						// traverse timings of Output Pins
						outTimIt = curOutPin.getTimings().iterator();
						
						while(outTimIt.hasNext()) {
							Timing curTim = outTimIt.next();
							// put in the list if the Input Power has the desired Power Group
							if(curTimSen == curTim.getTimSense() &&
							   curTimGr == curTim.getTimGroup() &&
							   curTimType == curTim.getTimType()) {
								
								toCalc.add(curTim);
							}
						}
					}
					float min = 0;
					float max = 0;
					float avg = 0;
					float med = 0;
					
					// calculate the stats for the desired Power Group
					while(toCalcIt.hasNext()) {
						Timing curTimCal = toCalcIt.next();
						min = Math.min(min, curTimCal.getStats().getMin());
						max = Math.max(max, curTimCal.getStats().getMax());
						avg += curTimCal.getStats().getAvg();
					}
					avg = avg / (float) toCalc.size();
					
					Stat stat = new Stat(min, max, avg, med);
					
					/**
					Map<TimingSense, Map<TimingGroup, Map<TimingType, Stat>>> map = 
							new HashMap<>();
					 **/
					
					// create a new key
					TimingKey key = new TimingKey(curTimSen, curTimGr, 
							curTimType);
					
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
package gelf.model.elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import gelf.model.elements.attributes.InputPower;
import gelf.model.elements.attributes.Leakage;
import gelf.model.elements.attributes.OutAttribute;
import gelf.model.elements.attributes.OutputPower;
import gelf.model.elements.attributes.PowerGroup;
import gelf.model.elements.attributes.Timing;
import gelf.model.elements.attributes.TimingGroup;
import gelf.model.elements.attributes.TimingKey;
import gelf.model.elements.attributes.TimingSense;
import gelf.model.elements.attributes.TimingType;
import gelf.model.project.Interpolator;

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
    	
    	this.setAvailableTimSen();
    	this.setAvailableTimGr();
    	this.setAvailableTimType();
    	this.setAvailableOutputPower();
    	this.setAvailableInputPower();
    	this.calculate();
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
	public Cell clone() {
		ArrayList<InputPin> clonedInPins = new ArrayList<InputPin>();
		ArrayList<OutputPin> clonedOutPins = new ArrayList<OutputPin>(); 
		Iterator<InputPin> inPinIt = inPins.iterator();
		Iterator<OutputPin> outPinIt = outPins.iterator();
		while(inPinIt.hasNext()) {
			InputPin curInPin = inPinIt.next();
			clonedInPins.add(curInPin.clone());
		}
		while(outPinIt.hasNext()) {
			OutputPin curOutPin = outPinIt.next();
			clonedOutPins.add(curOutPin.clone());
		}
		Leakage clonedLeakage = (Leakage) leakages.clone();
		Cell clonedCell = new Cell(name, index1, index2, parentLibrary, 
				clonedInPins, clonedOutPins, clonedLeakage, defaultLeakage);
		ArrayList<OutAttribute> outAttributes = new ArrayList<OutAttribute>(); 
		outPinIt = clonedOutPins.iterator();
		while(outPinIt.hasNext()) {
			OutputPin curOutPin = outPinIt.next();
			outAttributes.addAll(curOutPin.getTimings());
			outAttributes.addAll(curOutPin.getOutputPowers());
			curOutPin.setParent(clonedCell);
		}
		inPinIt = clonedInPins.iterator();
		while(inPinIt.hasNext()) {
			InputPin curInPin = inPinIt.next();
			curInPin.setParent(clonedCell);
		}
		for (OutAttribute outAttribute : outAttributes) {
			for (InputPin inPin: clonedInPins) {
				if (inPin.getName().equals(outAttribute.getRelatedPin().getName())) {
					outAttribute.setRelatedPin(inPin);
				}
			}
		}
		return clonedCell;
	}
	
	public void interpolate(float[] index1, float[] index2) {
		Iterator<InputPin> inPinIt = inPins.iterator();
		
		/* Calculating new input powers of input pins */
		while(inPinIt.hasNext()) {
			InputPin curInPin = inPinIt.next();
			ArrayList<InputPower> inPows = curInPin.getInputPowers();
			Iterator<InputPower> inPowIt = inPows.iterator();
			while(inPowIt.hasNext()) {
				InputPower curInPow = inPowIt.next();
				Interpolator interpolator = new Interpolator();
				float[] newValues = interpolator.interpolate(curInPow.getIndex1(),
						curInPow.getValues(), index1);
				curInPow.setIndex1(index1);
				curInPow.setValues(newValues);
			}
		}
		
		Iterator<OutputPin> outPinIt = outPins.iterator();
		
		/* Calculating new output powers of output pins */
		while(outPinIt.hasNext()) {
			OutputPin curOutPin = outPinIt.next();
			ArrayList<OutputPower> outPows = curOutPin.getOutputPowers();
			Iterator<OutputPower> outPowIt = outPows.iterator();
			while(outPowIt.hasNext()) {
				OutputPower curOutPow = outPowIt.next();
				Interpolator interpolator = new Interpolator();
				float[][] newValues = interpolator.bicubicInterpolate(curOutPow.getIndex1(), 
						curOutPow.getIndex2(), curOutPow.getValues(), index1, index2);
				curOutPow.setIndex1(index1);
				curOutPow.setIndex2(index2);
				curOutPow.setValues(newValues);
			}
		}
		
		outPinIt = outPins.iterator();
		
		/* Calculating new timings of output pins */
		while(outPinIt.hasNext()) {
			OutputPin curOutPin = outPinIt.next();
			ArrayList<Timing> timings = curOutPin.getTimings();
			Iterator<Timing> timingIt = timings.iterator();
			while(timingIt.hasNext()) {
				Timing curTiming = timingIt.next();
				Interpolator interpolator = new Interpolator();
				float[][] newValues = interpolator.bicubicInterpolate(curTiming.getIndex1(), 
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
		inPowerStat = new HashMap<PowerGroup, Stat>();
		if (availableInputPower == null || inPins == null) {
			return;
		}

		Iterator<PowerGroup> avPowGrIt = availableInputPower.iterator();
				
		// traverse all available PowerGroups
		while(avPowGrIt.hasNext()) {
			PowerGroup curPowGr = avPowGrIt.next();
			// ArrayList toCalc to put InputPowers of the same PowerGroup in 
			// the same place
			ArrayList<InputPower> toCalc = new ArrayList<InputPower>();
			
			Iterator<InputPin> inPinIt = inPins.iterator();
			// traverse Input Pins
			while(inPinIt.hasNext()) {
				InputPin curInPin = inPinIt.next();
				// traverse Input Powers of Input Pins
				if (curInPin.getInputPowers() == null) {
					continue;
				}
				Iterator<InputPower> inPowIt = 
						curInPin.getInputPowers().iterator();
				
				while(inPowIt.hasNext()) {
					InputPower curInPow = inPowIt.next();
					// put in the list if the Input Power has the desired Power Group
					if(curPowGr.equals(curInPow.getPowGroup())) {
						toCalc.add(curInPow);
					}
				}
			}
			if(toCalc.isEmpty()) {
				return;
			}
			float min = toCalc.get(0).getStats().getMin();
			float max = toCalc.get(0).getStats().getMax();
			float avg = 0;
			float med = 0;
			
			Iterator<InputPower> toCalcIt = toCalc.iterator();
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
		outPowerStat = new HashMap<PowerGroup, Stat>();
		if (availableOutputPower == null || outPins == null) {
			return;
		}
		Iterator<PowerGroup> avPowGrIt = availableOutputPower.iterator();
		
		
		
		// traverse all available PowerGroups
		while(avPowGrIt.hasNext()) {
			PowerGroup curPowGr = avPowGrIt.next();
			// ArrayList toCalc to put OutputPowers of the same PowerGroup in the same place
			ArrayList<OutputPower> toCalc = new ArrayList<OutputPower>();
			
			
			Iterator<OutputPin> outPinIt = outPins.iterator();
			// traverse Output Pins
			while(outPinIt.hasNext()) {
				OutputPin curOutPin = outPinIt.next();
				// traverse Output Powers of Input Pins
				if (curOutPin.getOutputPowers() == null) {
					continue;
				}
				Iterator<OutputPower> outPowIt = 
						curOutPin.getOutputPowers().iterator();
				
				while(outPowIt.hasNext()) {
					OutputPower curOutPow = outPowIt.next();
					// put in the list if the Output Power has the desired Power Group
					if(curPowGr == curOutPow.getPowGroup()) {
						toCalc.add(curOutPow);
					}
				}
			}
			if(toCalc.isEmpty()) {
				return;
			}
			float min = toCalc.get(0).getStats().getMin();
			float max = toCalc.get(0).getStats().getMax();
			float avg = 0;
			float med = 0;
			Iterator<OutputPower> toCalcIt = toCalc.iterator();
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
		timingStat = new HashMap<TimingKey, Stat>();
		if (availableTimSen == null || availableTimGr == null || 
				availableTimType == null || outPins == null) {
			return;
		}
		Iterator<TimingSense> avTimSenIt = availableTimSen.iterator();
		// traverse all available Timing Senses, Timing Groups, Timing Types
		while(avTimSenIt.hasNext()) {
			TimingSense curTimSen = avTimSenIt.next();
			
			Iterator<TimingGroup> avTimGrIt = availableTimGr.iterator();
			while(avTimGrIt.hasNext()) {
				TimingGroup curTimGr = avTimGrIt.next();
				
				Iterator<TimingType> avTimTypeIt = availableTimType.iterator();
				while(avTimTypeIt.hasNext()) {
					TimingType curTimType = avTimTypeIt.next();
					// ArrayList toCalc to put Timings of the same sense, group, types
					ArrayList<Timing> toCalc = new ArrayList<Timing>();
					
					Iterator<OutputPin> outPinIt = outPins.iterator();
					// traverse Output Pins
					while(outPinIt.hasNext()) {
						OutputPin curOutPin = outPinIt.next();
						// traverse timings of Output Pins
						if (curOutPin.getTimings() == null) {
							continue;
						}
						Iterator<Timing> outTimIt = curOutPin.
								getTimings().iterator();
						
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
					if(toCalc.isEmpty()) {
						return;
					}
					float min = toCalc.get(0).getStats().getMin();
					float max = toCalc.get(0).getStats().getMax();
					float avg = 0;
					float med = 0;
					
					Iterator<Timing> toCalcIt = toCalc.iterator();
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
					TimingKey key = new TimingKey(curTimSen, curTimType, 
							curTimGr);
					
					// put the stat of the timing in the map
					timingStat.put(key, stat);
					
				}
			}
		}
	}
	
	public void calculateLeakage() {
		if (leakages == null) {
			return;
		}
		leakages.calculate();
		leakage = leakages.getStats();
	}

	@Override
	public void setAvailableTimSen() {
		if(outPins == null) {
			return;
		}
		Iterator<OutputPin> outPinIt = outPins.iterator();
		
		while(outPinIt.hasNext()) {
			OutputPin curOutPin = outPinIt.next();
			Iterator<TimingSense> timSenIt = curOutPin.getAvailableTimSen()
					.iterator();
			
			while(timSenIt.hasNext()) {
				TimingSense curTimSen = timSenIt.next();
				if(!availableTimSen.contains(curTimSen)) {
				    this.getAvailableTimSen().add(curTimSen);
			    }
			}
		}	
	}

	@Override
	public void setAvailableTimGr() {
		if(outPins == null) {
			return;
		}
		Iterator<OutputPin> outPinIt = outPins.iterator();
		
		while(outPinIt.hasNext()) {
			OutputPin curOutPin = outPinIt.next();
			Iterator<TimingGroup> timGrIt = curOutPin.getAvailableTimGr()
					.iterator();
			
			while(timGrIt.hasNext()) {
				TimingGroup curTimGr = timGrIt.next();
				if(!availableTimGr.contains(curTimGr)) {
				    this.getAvailableTimGr().add(curTimGr);
			    }
			}
		}	
	}

	@Override
	public void setAvailableTimType() {
		if(outPins == null) {
			return;
		}
		Iterator<OutputPin> outPinIt = outPins.iterator();
		
		while(outPinIt.hasNext()) {
			OutputPin curOutPin = outPinIt.next();
			Iterator<TimingType> timTypeIt = curOutPin.getAvailableTimType()
					.iterator();
			
			while(timTypeIt.hasNext()) {
				TimingType curTimType = timTypeIt.next();
				if(!availableTimType.contains(curTimType)) {
				    this.getAvailableTimType().add(curTimType);
			    }
			}
		}		
	}

	@Override
	public void setAvailableOutputPower() {
		if(outPins == null) {
			return;
		}
		Iterator<OutputPin> outPinIt = outPins.iterator();
		
		while(outPinIt.hasNext()) {
			OutputPin curOutPin = outPinIt.next();
			Iterator<PowerGroup> outPowIt = curOutPin.getAvailablePower()
					.iterator();
			
			while(outPowIt.hasNext()) {
				PowerGroup curPowGr = outPowIt.next();
				if(!availableOutputPower.contains(curPowGr)) {
				    this.getAvailableOutputPower().add(curPowGr);
			    }
			}
		}
	}

	@Override
	public void setAvailableInputPower() {
		if(inPins == null) {
			return;
		}
		Iterator<InputPin> inPinIt = inPins.iterator();
		
		while(inPinIt.hasNext()) {
			InputPin curInPin = inPinIt.next();
			Iterator<PowerGroup> inPowIt = curInPin.getAvailablePower()
					.iterator();
			
			while(inPowIt.hasNext()) {
			    PowerGroup curPowGr = inPowIt.next();
				if(!availableInputPower.contains(curPowGr)) {
				    this.getAvailableInputPower().add(curPowGr);
			    }
			}
		}
	}

}

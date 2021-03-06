package gelf.model.elements;

import java.util.ArrayList;
import java.util.Iterator;

import gelf.model.elements.attributes.OutputPower;
import gelf.model.elements.attributes.Timing;
import gelf.model.elements.attributes.TimingGroup;
import gelf.model.elements.attributes.TimingSense;
import gelf.model.elements.attributes.TimingType;
/**
 * Keeps and calculates data of output pins.
 * @author Kerem Kara
 */
public class OutputPin extends Pin {
	private ArrayList<TimingSense> availableTimSen = 
			new ArrayList<TimingSense>();
    private ArrayList<TimingGroup> availableTimGr =
    		new ArrayList<TimingGroup>();
    private ArrayList<TimingType> availableTimType =
    		new ArrayList<TimingType>();
    private ArrayList<OutputPower> outputPowers = 
    		new ArrayList<OutputPower>();
    private ArrayList<Timing> timings = 
    		new ArrayList<Timing>();
    private float minCapacitance;
    private float maxCapacitance;
    private String outputFunction;	
    
    public OutputPin(String name, Cell parentCell, ArrayList<OutputPower> outputPowers, 
    		ArrayList<Timing> timings) {
    	super.setName(name);
    	super.setParent(parentCell);
    	this.outputPowers = outputPowers;
    	this.timings = timings;
    	
    	this.setAvailablePower();
    	this.setAvailableTimGr();
    	this.setAvailableTimSen();
    	this.setAvailableTimType();
    	calculate();
    }
    
	/**
	 * Returns a deep copy of the output Pin object
	 * @return the deep copy of the output Pin object
	 * @author Xhulio Pernoca
	 */
	@Override
    public OutputPin clone() {
		ArrayList<Timing> clonedTimings = new ArrayList<Timing>();
		ArrayList<OutputPower> clonedPowers = new ArrayList<OutputPower>(); 
		Iterator<Timing> timingsIt = timings.iterator();
		Iterator<OutputPower> powersIt = outputPowers.iterator();
		while(timingsIt.hasNext()) {
			Timing curTiming = timingsIt.next();
			clonedTimings.add(curTiming.clone());
		}
		while(powersIt.hasNext()) {
			OutputPower curPower = powersIt.next();
			clonedPowers.add(curPower.clone());
		}
		OutputPin clonedPin = new OutputPin(name, parentCell, clonedPowers, clonedTimings);
		clonedPin.setOutputFunction(outputFunction);
		clonedPin.setMaxCapacitance(maxCapacitance);
		clonedPin.setMinCapacitance(minCapacitance);
		timingsIt = clonedTimings.iterator();
		while(timingsIt.hasNext()) {
			Timing curTiming = timingsIt.next();
			curTiming.setParentOutPin(clonedPin);
		}
		powersIt = clonedPowers.iterator();
		while(powersIt.hasNext()) {
			OutputPower curPower = powersIt.next();
			curPower.setParentOutPin(clonedPin);
		}
		return clonedPin;
	}
    
	/**
	 * Replaces all the attributes that change while reparsing, so that there is no need
	 * to change reference from the object in the view
	 * @param dataPin the output pin object with the necessary data
	 * @author Xhulio Pernoca
	 */
	public void replaceData(OutputPin originDataPin) {
		OutputPin dataPin = originDataPin.clone();
		setName(dataPin.getName());
		setSearched(dataPin.getSearched());
		setOutputPowers(dataPin.getOutputPowers());
		setTimings(dataPin.getTimings());
		for (Timing timing: dataPin.getTimings()) {
			timing.setParentOutPin(this);
		}
		for (OutputPower pow: dataPin.getOutputPowers()) {
			pow.setParentOutPin(this);
		}
		setMaxCapacitance(dataPin.getMaxCapacitance());
		setMinCapacitance(dataPin.getMinCapacitance());
		setOutputFunction(dataPin.getOutputFunction());
	}

    public ArrayList<Timing> getTimings() {
		return timings;
	}
    
    public void setTimings(ArrayList<Timing> timings) {
    	this.timings = timings;
    }
    
    public ArrayList<OutputPower> getOutputPowers() {
    	return outputPowers;
    }
    
    public void setOutputPowers(ArrayList<OutputPower> outputPowers) {
    	this.outputPowers = outputPowers;
    }
    
    public ArrayList<TimingSense> getAvailableTimSen() {
    	return availableTimSen;
    }
    
    public void setAvailableTimSen() {
    	if(timings == null) {
    		return;
    	}
    	Iterator<Timing> timIt = timings.iterator();
		while(timIt.hasNext()) {
			Timing curTim = timIt.next();
			if(!availableTimSen.contains(curTim.getTimSense())) {
				availableTimSen.add(curTim.getTimSense());
			}
		}
    }
    
    public ArrayList<TimingGroup> getAvailableTimGr(){
    	return availableTimGr;
    }
    
    public void setAvailableTimGr() {
    	if(timings == null) {
    		return;
    	}
    	Iterator<Timing> timIt = timings.iterator();
		while(timIt.hasNext()) {
			Timing curTim = timIt.next();
			if(!availableTimGr.contains(curTim.getTimGroup())) {
				availableTimGr.add(curTim.getTimGroup());
			}
		}
    }
    
    public ArrayList<TimingType> getAvailableTimType() {
    	return availableTimType;
    }
    
    public void setAvailableTimType() {
    	if(timings == null) {
    		return;
    	}
    	Iterator<Timing> timIt = timings.iterator();
		while(timIt.hasNext()) {
			Timing curTim = timIt.next();
			if(!availableTimType.contains(curTim.getTimType())) {
				availableTimType.add(curTim.getTimType());
			}
		}
    }
    
    public String getOutputFunction() {
    	return outputFunction;
    }
    
    public void setOutputFunction(String outputFunction) {
    	this.outputFunction = outputFunction;
    }
    
    public void calculate() {
    	calculatePower();
    	calculateTiming();
    }
    
    public void calculatePower() {
    	if(outputPowers == null) {
    		return;
    	}
    	Iterator<OutputPower> i = outputPowers.iterator();
    	
    	while(i.hasNext()) {
    		i.next().calculate();
    	} 
    }
    
    public void calculateTiming() {
    	if (timings == null) {
    		return;
    	}
    	Iterator<Timing> i = timings.iterator();
    	
    	while(i.hasNext()) {
    		i.next().calculate();
    	} 
    }

	public float getMinCapacitance() {
		return minCapacitance;
	}

	public void setMinCapacitance(float minCapacitance) {
		this.minCapacitance = minCapacitance;
	}

	public float getMaxCapacitance() {
		return maxCapacitance;
	}

	public void setMaxCapacitance(float maxCapacitance) {
		this.maxCapacitance = maxCapacitance;
	}
	
	public void scaleOutputPower(float scaleValue) {
    	for (OutputPower i : outputPowers) {
    		i.scale(scaleValue);
    	}
    	calculate();
    }
	
	public void scaleTiming(float scaleValue) {
    	for (Timing i : timings) {
    		i.scale(scaleValue);
    	}
    	calculate();
    }

	@Override
	public void setAvailablePower() {
		if(outputPowers == null) {
			return;
		}
		Iterator<OutputPower> outPowIt = outputPowers.iterator();
		while(outPowIt.hasNext()) {
			OutputPower curOutPow = outPowIt.next();
			if(!availablePower.contains(curOutPow.getPowGroup())) {
				availablePower.add(curOutPow.getPowGroup());
			}
		}
	}
}

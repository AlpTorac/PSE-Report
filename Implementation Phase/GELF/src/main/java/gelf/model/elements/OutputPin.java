package gelf.model.elements;

import java.util.ArrayList;
import java.util.Iterator;

import gelf.model.elements.attributes.OutputPower;
import gelf.model.elements.attributes.Timing;
import gelf.model.elements.attributes.TimingGroup;
import gelf.model.elements.attributes.TimingSense;
import gelf.model.elements.attributes.TimingType;

public class OutputPin extends Pin {
	private ArrayList<TimingSense> availableTimSen;
    private ArrayList<TimingGroup> availableTimGr;
    private ArrayList<TimingType> availableTimType;
    private ArrayList<OutputPower> outputPowers;
    private ArrayList<Timing> timings;
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
    	Iterator<Timing> timIt = timings.iterator();
		while(timIt.hasNext()) {
			Timing curTim = timIt.next();
			availableTimSen.add(curTim.getTimSense());
		}
    }
    
    public ArrayList<TimingGroup> getAvailableTimGr(){
    	return availableTimGr;
    }
    
    public void setAvailableTimGr() {
    	Iterator<Timing> timIt = timings.iterator();
		while(timIt.hasNext()) {
			Timing curTim = timIt.next();
			availableTimGr.add(curTim.getTimGroup());
		}
    }
    
    public ArrayList<TimingType> getAvailableTimType() {
    	return availableTimType;
    }
    
    public void setAvailableTimType() {
    	Iterator<Timing> timIt = timings.iterator();
		while(timIt.hasNext()) {
			Timing curTim = timIt.next();
			availableTimType.add(curTim.getTimType());
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
    	Iterator<OutputPower> i = outputPowers.iterator();
    	
    	while(i.hasNext()) {
    		i.next().calculate();
    	} 
    }
    
    public void calculateTiming() {
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

	@Override
	public void setAvailablePower() {
		Iterator<OutputPower> outPowIt = outputPowers.iterator();
		while(outPowIt.hasNext()) {
			OutputPower curOutPow = outPowIt.next();
			availablePower.add(curOutPow.getPowGroup());
		}
	}
}

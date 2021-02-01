package main.java.gelf.model.elements;

import java.util.ArrayList;
import java.util.Iterator;

import main.java.gelf.model.elements.attributes.OutputPower;
import main.java.gelf.model.elements.attributes.Timing;
import main.java.gelf.model.elements.attributes.TimingGroup;
import main.java.gelf.model.elements.attributes.TimingSense;
import main.java.gelf.model.elements.attributes.TimingType;

public class OutputPin extends Pin {
	private ArrayList<TimingSense> availableTimSen;
    private ArrayList<TimingGroup> availableTimGr;
    private ArrayList<TimingType> availableTimType;
    private ArrayList<OutputPower> outputPowers;
    private ArrayList<Timing> timings;
    private String outputFunction;
    
    public OutputPin(String name, Cell parentCell, ArrayList<OutputPower> outputPowers, 
    		ArrayList<Timing> timings) {
    	super.setName(name);
    	super.setParent(parentCell);
    	this.outputPowers = outputPowers;
    	this.timings = timings;
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
    
    public void setAvailableTimSen(ArrayList<TimingSense> availableTimSen) {
    	this.availableTimSen = availableTimSen;
    }
    
    public ArrayList<TimingGroup> getAvailableTimGr(){
    	return availableTimGr;
    }
    
    public void setAvailableTimGr(ArrayList<TimingGroup> availableTimGr) {
    	this.availableTimGr = availableTimGr;
    }
    
    public ArrayList<TimingType> getAvailableTimType() {
    	return availableTimType;
    }
    
    public void setAvailableTimType(ArrayList<TimingType> availableTimType) {
    	this.availableTimType = availableTimType;
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
}

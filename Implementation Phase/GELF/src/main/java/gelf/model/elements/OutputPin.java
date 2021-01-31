package main.java.gelf.model.elements;

import java.util.ArrayList;

import main.java.gelf.model.elements.attributes.OutputPower;
import main.java.gelf.model.elements.attributes.Timing;
import main.java.gelf.model.elements.attributes.TimingGroup;
import main.java.gelf.model.elements.attributes.TimingSense;
import main.java.gelf.model.elements.attributes.TimingType;

public class OutputPin {
	private ArrayList<TimingSense> availableTimSen;
    private ArrayList<TimingGroup> availableTimGr;
    private ArrayList<TimingType> availableTimType;
    private ArrayList<OutputPower> outputPowers;
    private ArrayList<Timing> timings;
    private String outputFunction;
    
    public OutputPin(String name, Cell parentCell, ArrayList<OutputPower> outputPowers, 
    		ArrayList<Timing> timings) {
    	
    }
    public ArrayList<Timing> getTimings() {
		return timings;
	}
    public void setTimings(ArrayList<Timing> timings) {
    	
    }
    
    public ArrayList<OutputPower> getOutputPowers() {
    	return outputPowers;
    }
    
    public void setOutputPowers(ArrayList<OutputPower> outputPowers) {
    	
    }
    
    public ArrayList<TimingSense> getAvailableTimSen() {
    	return availableTimSen;
    }
    
    public void setAvailableTimSen(ArrayList<TimingSense> availableTimSen) {
    	
    }
    
    public ArrayList<TimingGroup> getAvailableTimGr(){
    	return availableTimGr;
    }
    
    public void setAvailableTimGr(ArrayList<TimingGroup> availableTimGr) {
    	
    }
    
    public ArrayList<TimingType> getAvailableTimType() {
    	return availableTimType;
    }
    
    public void setAvailableTimType(ArrayList<TimingType> availableTimType) {
    	
    }
    public String getOutputFunction() {
    	return outputFunction;
    }
    public void setOutputFunction(String outputFunction) {
    	
    }
    
    public void calculate() {
    	
    }
    public void calculatePower() {
    	
    }
    public void calculateTiming() {
    	
    }
}

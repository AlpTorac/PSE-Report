package gelf.model.elements;

import java.util.ArrayList;
import java.util.Iterator;

import gelf.model.elements.attributes.InputPower;

public class InputPin extends Pin {
	private ArrayList<InputPower> inputPowers;
	private float capacitance;
	
    public InputPin(String name, Cell parentCell, ArrayList<InputPower> inputPowers) {
    	super.setName(name);
    	super.setParent(parentCell);
    	this.inputPowers = inputPowers;
    	/*
    	this.setAvailablePower();
    	calculate();
    	*/
    }
    
    public float getCapacitance() {
   	    return capacitance;
    }
    
    public void setCapacitance(float capacitance) {
   	    this.capacitance = capacitance;
    }
    
    public void calculate() {
    	calculatePower();
    }
    
    public void calculatePower() {
    	Iterator<InputPower> i = inputPowers.iterator();
    	
    	while(i.hasNext()) {
    		i.next().calculate();
    	}
    }
    public ArrayList<InputPower> getInputPowers()  {
    	return inputPowers;
    }
    
    public void setInputPowers(ArrayList<InputPower> inputPowers) {
    	this.inputPowers = inputPowers;
    }

	@Override
	public void setAvailablePower() {
		Iterator<InputPower> inPowIt = inputPowers.iterator();
		while(inPowIt.hasNext()) {
			InputPower curInPow = inPowIt.next();
			availablePower.add(curInPow.getPowGroup());
		}
	}
}

package main.java.gelf.model.elements;

import java.util.ArrayList;
import java.util.Iterator;

import main.java.gelf.model.elements.attributes.InputPower;

public class InputPin extends Pin {
	private ArrayList<InputPower> inputPowers;
	
    public InputPin(String name, Cell parentCell, ArrayList<InputPower> inputPowers) {
    	super.setName(name);
    	super.setParent(parentCell);
    	this.inputPowers = inputPowers;
    	calculate();
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
}

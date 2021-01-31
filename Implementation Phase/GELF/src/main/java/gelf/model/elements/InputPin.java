package main.java.gelf.model.elements;

import java.util.ArrayList;

import main.java.gelf.model.elements.attributes.InputPower;

public class InputPin {
	private ArrayList<InputPower> inputPowers;
	
    public InputPin(String name, Cell parentCell, ArrayList<InputPower> inputPowers) {
    	
    }
    
    public void calculate() {
    	
    }
    
    public void calculatePower() {
    	
    }
    public ArrayList<InputPower> getInputPowers()  {
    	return inputPowers;
    }
    
    public void setInputPowers(ArrayList<InputPower> inputPowers) {
    	this.inputPowers = inputPowers;
    }
}

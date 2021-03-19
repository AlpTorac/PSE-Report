package gelf.model.elements;

import java.util.ArrayList;
import java.util.Iterator;

import gelf.model.elements.attributes.InputPower;
/**
 * Keeps and calculates data of Input Pins.
 * @author Kerem Kara
 */
public class InputPin extends Pin {
	private ArrayList<InputPower> inputPowers = new ArrayList<InputPower>();
	private float capacitance;
	
    public InputPin(String name, Cell parentCell, ArrayList<InputPower> inputPowers) {
    	super.setName(name);
    	super.setParent(parentCell);
    	this.inputPowers = inputPowers;
    	this.setAvailablePower();
    	calculate();
    }
    
	/**
	 * Returns a deep copy of the input Pin object
	 * @return the deep copy of the input Pin object
	 * @author Xhulio Pernoca
	 */
	@Override
    public InputPin clone() {
		ArrayList<InputPower> clonedPowers = new ArrayList<InputPower>();
		Iterator<InputPower> powersIt = inputPowers.iterator();
		while(powersIt.hasNext()) {
			InputPower curPower = powersIt.next();
			clonedPowers.add(curPower.clone());
		}
		InputPin clonedPin = new InputPin(name, parentCell, clonedPowers);
		clonedPin.setCapacitance(capacitance);
		powersIt = clonedPowers.iterator();
		while(powersIt.hasNext()) {
			InputPower curPower = powersIt.next();
			curPower.setParentInPin(clonedPin);
		}
		return clonedPin;
	}

	/**
	 * Replaces all the attributes that change while reparsing, so that there is no need
	 * to change reference from the object in the view
	 * @param dataPin the input pin object with the necessary data
	 * @author Xhulio Pernoca
	 */
	public void replaceData(InputPin originDataPin) {
		InputPin dataPin = originDataPin.clone();
		setName(dataPin.getName());
		setSearched(dataPin.getSearched());
		setInputPowers(dataPin.getInputPowers());
		for (InputPower pow: dataPin.getInputPowers()) {
			pow.setParentInPin(this);
		}
		setCapacitance(dataPin.getCapacitance());
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
    	if (inputPowers == null) {
    		return;
    	}
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
    
    public void scale(float scaleValue) {
    	for (InputPower i : inputPowers) {
    		i.scale(scaleValue);
    	}
    	calculate();
    }

	@Override
	public void setAvailablePower() {
		if(inputPowers == null) {
			return;
		}
		Iterator<InputPower> inPowIt = inputPowers.iterator();
		while(inPowIt.hasNext()) {
			InputPower curInPow = inPowIt.next();
			if(!availablePower.contains(curInPow.getPowGroup())) {
				availablePower.add(curInPow.getPowGroup());
			}
		}
	}
}

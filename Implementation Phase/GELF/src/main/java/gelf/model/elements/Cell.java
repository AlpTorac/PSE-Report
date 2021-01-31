package main.java.gelf.model.elements;

import java.util.ArrayList;

import main.java.gelf.model.elements.attributes.Leakage;

public class Cell extends HigherElement {
	private float[] index1;
    private float[] index2;
    private Library parentLibrary;
    private ArrayList<InputPin> inPins;
    private ArrayList<OutputPin> outPins;
    private Leakage[] leakages;
    private float defaultLeakage;
    
    public Cell(String name, float[] index1, float[] index2, Library parentLibrary,
    		ArrayList<InputPin> inPins, ArrayList<OutputPin> outPins,
    		Leakage[] leakages, float leakage) {
    	super.setName(name);
    	this.index1 = index1;
    	this.index2 = index2;
    	this.parentLibrary = parentLibrary;
    	this.inPins = inPins;
    	this.outPins = outPins;
    	this.leakages = leakages;
    	this.defaultLeakage = leakage;
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

	public Leakage[] getLeakages() {
		return leakages;
	}

	public void setLeakages(Leakage[] leakages) {
		this.leakages = leakages;
	}

	public float getDefaultLeakage() {
		return defaultLeakage;
	}

	public void setDefaultLeakage(float defaultLeakage) {
		this.defaultLeakage = defaultLeakage;
	}
	
	@Override
	public void calculate() {
		// TODO Auto-generated method stub
		
	}
	
	public void calculateInPow() {
		
	}
	
	public void calculateOutPow() {
		
	}
	
	public void calculateTiming() {
		
	}
	
	public void calculateLeakage() {
		
	}

}

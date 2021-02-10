package main.java.gelf.model.elements.attributes;

import main.java.gelf.model.elements.Stat;

public class Attribute {
	protected Stat stats;
	
	public Attribute() {}
	public void scale(float scaleValue) {}
	protected void calculate() {}
	
	public Stat getStats() {
		return stats;
	}
}

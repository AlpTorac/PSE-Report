package gelf.model.elements.attributes;

import gelf.model.elements.Stat;

public abstract class Attribute {
	protected Stat stats;
	
	public Attribute() {	
	}
	
	public abstract void scale(float scaleValue);
	
	protected abstract void calculate();
	
	public Stat getStats() {
		return stats;
	}
}

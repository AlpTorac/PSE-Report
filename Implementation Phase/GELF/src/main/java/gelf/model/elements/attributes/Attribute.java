package gelf.model.elements.attributes;

import gelf.model.elements.Stat;

/**
 * Provides abstraction for all attributes.
 * 
 * @author Kerem Kara
 *
 */
public abstract class Attribute {
	protected Stat stats;

	public Attribute() {
	}

	public abstract void scale(float scaleValue);

	protected abstract Attribute clone();

	protected abstract void calculate();

	public Stat getStats() {
		return stats;
	}
}

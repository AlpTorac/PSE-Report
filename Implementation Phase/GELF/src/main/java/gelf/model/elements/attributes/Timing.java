package gelf.model.elements.attributes;

import java.util.Arrays;

import gelf.model.project.Interpolator;

public class Timing extends OutAttribute{
	private TimingSense timSense;
	private TimingType timType;
	private TimingGroup timGroup;
	
	public Timing(TimingSense timSense, TimingType timType, 
			TimingGroup timGroup, float[][] values) {
		setTimingSense(timSense);
		setTimType(timType);
		setTimGroup(timGroup);
		this.values = values;
	}

	public TimingSense getTimSense() {
		return timSense;
	}

	public void setTimingSense(TimingSense timSense) {
		this.timSense = timSense;
	}

	public TimingType getTimType() {
		return timType;
	}

	public void setTimType(TimingType timType) {
		this.timType = timType;
	}

	public TimingGroup getTimGroup() {
		return timGroup;
	}

	public void setTimGroup(TimingGroup timGroup) {
		this.timGroup = timGroup;
	}
	
	@Override
	public Timing clone() {
		Timing clonedTiming = new Timing(timSense, timType, timGroup, values);
		clonedTiming.setIndex1(index1);
		clonedTiming.setIndex2(index2);
		clonedTiming.setParentOutPin(parentOutPin);
		clonedTiming.setRelatedPin(relatedPin);
		return clonedTiming;
	}

	public Timing createComparedAttribute(Timing attribute) {
		if (Arrays.equals(this.index1, attribute.index1) && 
				Arrays.equals(this.index2, attribute.index2 )) {
			return attribute;
		}
		Interpolator interpolator = new Interpolator();
		float[][] newValues = interpolator.bicubicInterpolate(attribute.index1, attribute.index2, 
				attribute.values, this.index1, this.index2);
		Timing newAttr = new Timing(attribute.getTimSense(), attribute.getTimType(),
				attribute.getTimGroup(), newValues);
		newAttr.setIndex1(this.index1);
		newAttr.setIndex2(this.index2);
		return newAttr;
	}
	
	

}

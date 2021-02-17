package gelf.model.elements.attributes;

import java.util.Arrays;

import gelf.model.project.Interpolator;

public class OutputPower extends OutAttribute{
	private PowerGroup powGroup;
	
	public OutputPower(PowerGroup powGroup, float[][] values) {
		this.powGroup = powGroup;
		this.values = values;
	}

	public PowerGroup getPowGroup() {
		return powGroup;
	}

	public void setPowGroup(PowerGroup powGroup) {
		this.powGroup = powGroup;
	}

	@Override
	public OutputPower clone() {
		OutputPower clonedOutputPower = new OutputPower(powGroup, values);
		clonedOutputPower.setIndex1(index1);
		clonedOutputPower.setIndex2(index2);
		clonedOutputPower.setParentOutPin(parentOutPin);
		clonedOutputPower.setRelatedPin(relatedPin);
		return clonedOutputPower;
	}
	
	public OutputPower createComparedAttribute(OutputPower attribute) {
		if (Arrays.equals(this.index1, attribute.index1) && 
				Arrays.equals(this.index2, attribute.index2 )) {
			return attribute;
		}
		Interpolator interpolator = new Interpolator();
		
		float[][] newValues = interpolator.bicubicInterpolate(attribute.index1, attribute.index2, 
				attribute.values, this.index1, this.index2);
		OutputPower newAttr = new OutputPower(attribute.getPowGroup(), newValues);
		newAttr.setIndex1(this.index1);
		newAttr.setIndex2(this.index2);
		return newAttr;
	}
}


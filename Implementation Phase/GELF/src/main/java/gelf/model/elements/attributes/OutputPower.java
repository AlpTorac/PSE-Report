package gelf.model.elements.attributes;

import java.util.Arrays;

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


package gelf.model.elements.attributes;

import java.util.ArrayList;

import gelf.model.project.Interpolator;

/**
 * Keeps data of input internal power.
 * @author Kerem Kara
 */
public class InputPower extends InAttribute {
	private PowerGroup powGroup;
	
	public InputPower(PowerGroup powGroup, float[] values) {
		this.powGroup = powGroup;
		this.values = values;
	}
	
	@Override
	public InputPower clone() {
		InputPower clonedInputPower = new InputPower(powGroup, values);
		clonedInputPower.setIndex1(index1);
		clonedInputPower.setParentInPin(parentInPin);
		return clonedInputPower;
	}

	public PowerGroup getPowGroup() {
		return powGroup;
	}

	public void setPowGroup(PowerGroup powGroup) {
		this.powGroup = powGroup;
	}
	/**
	 * 
	 * @param secIndex Index of the attribute to compare 
	 * @param secValues Values of the attribute to compare
	 * @return First attributes values for the second attributes index as [0], second attributes values for first attributes index as [1]
	 */
	public float[][] createComparedAttribute(float[] secIndex, float[] secValues) {
		Interpolator interpolator = new Interpolator();
		float[][] data = new float[2][];
		data[0] = interpolator.interpolate(this.index1, this.values, secIndex);
		data[1] = interpolator.interpolate(secIndex, secValues, this.index1);
		return data;
	}
}

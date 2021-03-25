package gelf.model.elements.attributes;

import java.util.ArrayList;
import java.util.Collections;

import gelf.model.project.Interpolator;

/**
 * Keeps data of input internal power.
 * 
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
	 * @param secIndex  Index of the attribute to compare
	 * @param secValues Values of the attribute to compare
	 * @return data[0] are the first attributes values according to the unified indexes,
	 *         data[1] are the second attributes values according to the unified indexes,
	 *         data[2] are the unified indexes
	 */
	public float[][] createComparedAttribute(float[] secIndex, float[] secValues) {
		ArrayList<Float> indexes = new ArrayList<Float>();
		float[] unifIndex = new float[this.index1.length + secIndex.length];
		for (int i = 0; i < this.index1.length; i++) {
			indexes.add(this.index1[i]);
		}
		for (int i = 0; i < secIndex.length; i++) {
			indexes.add(secIndex[i]);
		}
		Collections.sort(indexes);
		for (int i = 0; i < unifIndex.length; i++) {
			unifIndex[i] = indexes.get(i);
		}
		Interpolator interpolator = new Interpolator();
		float[][] data = new float[2][];
		data[0] = interpolator.interpolate(this.index1, this.values, unifIndex);
		data[1] = interpolator.interpolate(secIndex, secValues, unifIndex);
		data[2] = unifIndex;
		return data;
	}
}

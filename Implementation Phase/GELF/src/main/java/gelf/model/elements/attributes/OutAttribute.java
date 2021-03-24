package gelf.model.elements.attributes;

import java.util.ArrayList;
import java.util.Arrays;

import gelf.model.elements.InputPin;
import gelf.model.elements.OutputPin;
import gelf.model.elements.Stat;

/**
 * Keeps and calculates data of output pin attributes.
 * 
 * @author Kerem Kara
 */
public abstract class OutAttribute extends Attribute {
	protected float[] index1;
	protected float[] index2;
	protected float[][] values;
	protected OutputPin parentOutPin;
	protected InputPin relatedPin;

	public OutAttribute() {
	}

	@Override
	public void calculate() {
		float min = values[0][0];
		float max = values[0][0];
		float sum = 0;
		float avg = 0;
		float med = 0;

		// calculates minimum of the values
		for (int i = 0; i < index1.length; i++) {
			for (int j = 0; j < index2.length; j++) {
				min = Math.min(min, values[i][j]);
			}
		}

		// calculates maximum of the values
		for (int i = 0; i < index1.length; i++) {
			for (int j = 0; j < index2.length; j++) {
				max = Math.max(max, values[i][j]);
			}
		}

		// calculates sum of the values
		for (int i = 0; i < index1.length; i++) {
			for (int j = 0; j < index2.length; j++) {
				sum += values[i][j];
			}
		}

		// calculates average of the values
		avg = sum / (index1.length * index2.length);

		// put 2d matrix of values in an array
		ArrayList<Float> tempList = new ArrayList<Float>();
		for (int i = 0; i < index1.length; i++) {
			for (int j = 0; j < index2.length; j++) {
				tempList.add(values[i][j]);
			}
		}

		float[] tempArray = new float[tempList.size()];
		for (int i = 0; i < tempArray.length; i++) {
			tempArray[i] = tempList.get(i);
		}

		// sorts array of the values to find median
		Arrays.sort(tempArray);

		// calculates median according to the number of values being odd or even
		if (values.length % 2 == 1) {
			med = tempArray[tempArray.length / 2];
		} else {
			float medSum = tempArray[tempArray.length / 2 - 1] + tempArray[tempArray.length / 2];
			med = medSum / (float) 2;
		}

		// creates new Stat or changes the existing one
		if (stats != null) {
			stats.setAvg(avg);
			stats.setMax(max);
			stats.setMin(min);
			stats.setMed(med);
		} else {
			stats = new Stat(min, max, avg, med);
		}
	}

	public void scale(float scaleValue) {
		for (int i = 0; i < index1.length; i++) {
			for (int j = 0; j < index2.length; j++) {
				values[i][j] *= scaleValue;
			}
		}
	}

	public OutputPin getParentOutPin() {
		return parentOutPin;
	}

	public void setParentOutPin(OutputPin parentOutPin) {
		this.parentOutPin = parentOutPin;
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

	public float[][] getValues() {
		return values;
	}

	public void setValues(float[][] values) {
		this.values = values;
	}

	public InputPin getRelatedPin() {
		return relatedPin;
	}

	public void setRelatedPin(InputPin relatedPin) {
		this.relatedPin = relatedPin;
	}

	@Override
	public Stat getStats() {
		return super.getStats();
	}
}

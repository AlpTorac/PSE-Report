package main.java.gelf.model.elements.attributes;

import java.util.Arrays;

import main.java.gelf.model.elements.InputPin;
import main.java.gelf.model.elements.Stat;

public abstract class InAttribute extends Attribute {
	protected float[] index1;
	protected float[] values;
	protected InputPin parentInPin;
	
	public InAttribute() {}
	
	@Override
	public void scale(float scaleValue) {
		for (int i = 0; i < values.length; i++) {
			values[i] *= scaleValue;
		}
	}
	
	@Override
	public void calculate() {
		float min = 0;
		float max = 0;
		float sum = 0;
		float avg = 0;
		float med = 0;
		
		// calculates minimum of the values
		for (int i = 0; i < values.length; i++) {
			min = Math.min(min, values[i]);		
		}
		
		// calculates maximum of the values
		for (int i = 0; i < values.length; i++) {
			max = Math.max(max, values[i]);		
		}
		
		// calculates sum of the values
		for (int i = 0; i < values.length; i++) {
			sum += values[i];
		}
		
		// calculates average of the values
		avg = sum / values.length;
		
		// sorts array of the values to find median
		float[] temp = new float[values.length];
		for (int i = 0; i < values.length; i++) {
			temp[i] = values[i];
		}
		Arrays.sort(temp);
		
		// calculates median according to the number of values being odd or even
		if (values.length % 2 == 1) {
			med = temp[temp.length / 2];
		}
		else {
			float medSum = temp[temp.length / 2] + temp[temp.length / 2 + 1];
			med = medSum / (float)2;
		}
		
		// creates new Stat or changes the existing one
		if (stats != null) {
			stats.setAvg(avg);
			stats.setMax(max);
			stats.setMin(min);
			stats.setMed(med);
		}
		else {
			stats = new Stat(min, max, avg, med);
		}
	}

	public float[] getIndex1() {
		return index1;
	}

	public void setIndex1(float[] index1) {
		this.index1 = index1;
	}

	public float[] getValues() {
		return values;
	}

	public void setValues(float[] values) {
		this.values = values;
	}

	public InputPin getParentInPin() {
		return parentInPin;
	}

	public void setParentInPin(InputPin parentInPin) {
		this.parentInPin = parentInPin;
	}
	
	@Override
	public Stat getStats() {
		return super.getStats();
	}
	
}

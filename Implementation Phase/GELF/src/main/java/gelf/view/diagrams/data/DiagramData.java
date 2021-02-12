package gelf.view.diagrams.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class DiagramData {
	private Collection<?> data;
	private DiagramDataExtractionStrategy extractor;
	private int numberOfIndices;

	public DiagramData(Collection<?> data, int numberOfIndices) {
		this.data = data;
		this.numberOfIndices = numberOfIndices;
		this.setExtractor(this.numberOfIndices);
	}
	
	private void setExtractor(int numberOfIndices) {
		if (this.data.iterator().next() instanceof float[]) {
			this.extractor = new ValueIndexExtractor(numberOfIndices);
		}
	}
	
	public ArrayList<float[]> extractValues() {
		return this.extractor.extractValues(this.data);
	}
	
	public ArrayList<float[]> extractIndices() {
		return this.extractor.extractIndices(this.data);
	}
	
	public float getMaximumValue() {
		float maxVal = Float.MIN_VALUE;
		
		for (float[] values : this.extractor.extractValues(this.data)) {
			for (int i = 0; i < values.length; i++) {
				float currentMaxCandidate = values[i];
				if (maxVal < currentMaxCandidate) {
					maxVal = currentMaxCandidate;
				}
			}
		}
		
		return maxVal;
	}
	
	public float getMinimumValue() {
		float minVal = Float.MAX_VALUE;
		
		for (float[] values : this.extractor.extractValues(this.data)) {
			for (int i = 0; i < values.length; i++) {
				float currentMinCandidate = values[i];
				if (minVal > currentMinCandidate) {
					minVal = currentMinCandidate;
				}
			}
		}
		
		return minVal;
	}
	
	public float getMaximumValueAt(int index) {
		float maxVal = Float.MIN_VALUE;
		
		float[] values = this.extractor.extractValues(this.data).get(index);
		
		for (int i = 0; i < values.length; i++) {
			float currentMaxCandidate = values[i];
			if (maxVal < currentMaxCandidate) {
				maxVal = currentMaxCandidate;
			}
		}
		
		return maxVal;
	}
	
	public float getMinimumValueAt(int index) {
		float minVal = Float.MAX_VALUE;
		
		float[] values = this.extractor.extractValues(this.data).get(index);
		
		for (int i = 0; i < values.length; i++) {
			float currentMinCandidate = values[i];
			if (minVal > currentMinCandidate) {
				minVal = currentMinCandidate;
			}
		}
		
		return minVal;
	}
	
	public float getMaximumIndex() {
		float maxVal = Float.MIN_VALUE;
		
		for (float[] values : this.extractor.extractIndices(this.data)) {
			for (int i = 0; i < values.length; i++) {
				float currentMaxCandidate = values[i];
				if (maxVal < currentMaxCandidate) {
					maxVal = currentMaxCandidate;
				}
			}
		}
		
		return maxVal;
	}
	
	public float getMinimumIndex() {
		float minVal = Float.MAX_VALUE;
		
		for (float[] values : this.extractor.extractIndices(this.data)) {
			for (int i = 0; i < values.length; i++) {
				float currentMinCandidate = values[i];
				if (minVal > currentMinCandidate) {
					minVal = currentMinCandidate;
				}
			}
		}
		
		return minVal;
	}
	
	public float getMaximumIndexAt(int index) {
		float maxVal = Float.MIN_VALUE;
		
		float[] indices = this.extractor.extractIndices(this.data).get(index);
		
		for (int i = 0; i < indices.length; i++) {
			float currentMaxCandidate = indices[i];
			if (maxVal < currentMaxCandidate) {
				maxVal = currentMaxCandidate;
			}
		}
		
		return maxVal;
	}
	
	public float getMinimumIndexAt(int index) {
		float minVal = Float.MAX_VALUE;
		
		float[] indices = this.extractor.extractIndices(this.data).get(index);
		
		for (int i = 0; i < indices.length; i++) {
			float currentMinCandidate = indices[i];
			if (minVal > currentMinCandidate) {
				minVal = currentMinCandidate;
			}
		}
		
		return minVal;
	}
	
	@Override
	public DiagramData clone() {
		ArrayList<float[]> indices = this.extractor.extractIndices(this.data);
		ArrayList<float[]> values = this.extractor.extractValues(this.data);
		
		ArrayList<float[]> clonedData = new ArrayList<float[]>();
		
		clonedData.addAll(indices);
		clonedData.addAll(values);
		
		DiagramData clone = new DiagramData(clonedData, indices.size());
		return clone;
	}
	
	public int getNumberOfIndices() {
		return numberOfIndices;
	}
}

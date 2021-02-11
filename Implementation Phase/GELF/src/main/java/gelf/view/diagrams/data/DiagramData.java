package gelf.view.diagrams.data;

import java.util.ArrayList;
import java.util.Collection;

public class DiagramData {
	private Collection<?> data;
	private DiagramDataExtractionStrategy extractor;
	
	public DiagramData(Collection<?> data, int numberOfIndices) {
		this.data = data;
		this.setExtractor(numberOfIndices);
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
}

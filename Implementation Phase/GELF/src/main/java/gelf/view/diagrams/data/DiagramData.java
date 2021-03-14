package gelf.view.diagrams.data;

import java.util.ArrayList;
import java.util.Collection;

public class DiagramData {
	private Collection<?> data;
	private Collection<?> descriptions;
	private DiagramDataExtractionStrategy extractor;
	private int numberOfIndices;

	public DiagramData(Collection<?> data, int numberOfIndices) {
		this.data = data;
		this.numberOfIndices = numberOfIndices;
		this.setExtractor(this.numberOfIndices);
	}
	
	public DiagramData(Collection<?> data, Collection<?> descriptions, int numberOfIndices) {
		this.data = data;
		this.descriptions = descriptions;
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
	
	public ArrayList<String[]> extractValueDescriptions() {
		return this.extractor.extractValueDescriptions(this.descriptions);
	}
	
	public ArrayList<String[]> extractIndexDescriptions() {
		return this.extractor.extractIndexDescriptions(this.descriptions);
	}
	
	private float getMinOfArrays(float[][] arrays) {
		float minVal = Float.MAX_VALUE;
		
		for (float[] values : arrays) {
			float currentMinCandidate = this.getMinOfArray(values);
			if (minVal > currentMinCandidate) {
				minVal = currentMinCandidate;
			}
		}
		
		return minVal;
	}
	
	private float getMinOfArray(float[] array) {
		float minVal = Float.MAX_VALUE;
		
		for (int i = 0; i < array.length; i++) {
			float currentMinCandidate = array[i];
			if (minVal > currentMinCandidate) {
				minVal = currentMinCandidate;
			}
		}
		
		return minVal;
	}
	private float getMaxOfArrays(float[][] arrays) {
		float maxVal = -Float.MAX_VALUE;
		
		for (float[] values : arrays) {
			float currentMaxCandidate = this.getMaxOfArray(values);
			if (maxVal < currentMaxCandidate) {
				maxVal = currentMaxCandidate;
			}
		}
		
		return maxVal;
	}
	
	private float getMaxOfArray(float[] array) {
		float maxVal = -Float.MAX_VALUE;
		
		for (int i = 0; i < array.length; i++) {
			float currentMaxCandidate = array[i];
			if (maxVal < currentMaxCandidate) {
				maxVal = currentMaxCandidate;
			}
		}
		
		return maxVal;
	}
	
	public float getMaximumValue() {
		ArrayList<float[]> valueList = this.extractValues();
		
		float[][] indices = new float[valueList.size()][valueList.get(0).length];
		valueList.toArray(indices);
		
		return this.getMaxOfArrays(indices);
	}
	
	public float getMinimumValue() {
		ArrayList<float[]> valueList = this.extractValues();
		
		float[][] indices = new float[valueList.size()][valueList.get(0).length];
		valueList.toArray(indices);
		
		return this.getMinOfArrays(indices);
	}
	
	public float getMaximumIndex() {
		ArrayList<float[]> indexList = this.extractIndices();
		
		float[][] indices = new float[indexList.size()][indexList.get(0).length];
		indexList.toArray(indices);
		
		return this.getMaxOfArrays(indices);
	}
	
	public float getMinimumIndex() {
		ArrayList<float[]> indexList = this.extractIndices();
		
		float[][] indices = new float[indexList.size()][indexList.get(0).length];
		indexList.toArray(indices);
		
		return this.getMinOfArrays(indices);
	}
	
	public float getMaximumValueAt(int index) {
		return this.getMaxOfArray(this.extractValues().get(index));
	}
	
	public float getMinimumValueAt(int index) {
		return this.getMinOfArray(this.extractValues().get(index));
	}
	public float getMaximumIndexAt(int index) {
		return this.getMaxOfArray(this.extractIndices().get(index));
	}
	public float getMinimumIndexAt(int index) {
		return this.getMinOfArray(this.extractIndices().get(index));
	}
	
	public float getAverageValue() {
		float result = 0;
		int valueCount = 0;
		
		for (float[] arr : this.extractValues()) {
			for (float value : arr) {
				result += value;
				valueCount++;
			}
		}
		
		result = result / ((float) valueCount);
		return result;
	}
	
	public float getValueMedian() {
		ArrayList<float[]> values = this.extractValues();
		ArrayList<Float> arrayOfValues = new ArrayList<Float>();
		
		for (float[] valueArr : values) {
			for (float value : valueArr) {
				arrayOfValues.add(value);
			}
		}
		
		arrayOfValues.sort(null);
		
		int length = arrayOfValues.size();
		float median;
		if (length % 2 == 1) {
			median = arrayOfValues.get(length / 2);
		} else {
			median = (arrayOfValues.get(length / 2 - 1) + arrayOfValues.get(length / 2)) / 2f;
		}
		
		return median;
	}
	
	@Override
	public DiagramData clone() {
		ArrayList<float[]> indices = this.extractor.extractIndices(this.data);
		ArrayList<float[]> values = this.extractor.extractValues(this.data);
		
		ArrayList<float[]> clonedData = new ArrayList<float[]>();
		
		if (indices != null) {
			clonedData.addAll(indices);
		}
		if (values != null) {
			clonedData.addAll(values);
		}
		
		DiagramData clone;
		if (this.descriptions == null) {
			clone = new DiagramData(clonedData, this.getNumberOfIndices());
		} else {
			ArrayList<String[]> indexDescs = this.extractIndexDescriptions();
			ArrayList<String[]> valueDescs = this.extractValueDescriptions();
			
			ArrayList<String[]> clonedDescs = new ArrayList<String[]>();
			
			if (indexDescs != null) {
				clonedDescs.addAll(indexDescs);
			}
			if (valueDescs != null) {
				clonedDescs.addAll(valueDescs);
			}
			
			clone = new DiagramData(clonedData, clonedDescs, this.getNumberOfIndices());
		}
		return clone;
	}
	
	public int getNumberOfIndices() {
		return numberOfIndices;
	}
}

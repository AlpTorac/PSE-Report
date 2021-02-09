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
}
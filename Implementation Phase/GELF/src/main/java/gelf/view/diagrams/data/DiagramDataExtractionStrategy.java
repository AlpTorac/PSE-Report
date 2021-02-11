package gelf.view.diagrams.data;

import java.util.ArrayList;
import java.util.Collection;

abstract class DiagramDataExtractionStrategy {
	
	protected int numberOfIndices;
	
	protected DiagramDataExtractionStrategy(int numberOfIndices) {
		this.numberOfIndices = numberOfIndices;
	}
	
	protected abstract ArrayList<float[]> extractValues(Collection<?> data);
	protected abstract ArrayList<float[]> extractIndices(Collection<?> data);
}

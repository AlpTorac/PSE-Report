package gelf.view.diagrams.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class ValueIndexExtractor extends DiagramDataExtractionStrategy {

	protected ValueIndexExtractor(int numberOfIndices) {
		super(numberOfIndices);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected ArrayList<float[]> extractValues(Collection<?> data) {
		ArrayList<float[]> listOfValues = new ArrayList<float[]>();
		
		Iterator<float[]> it = (Iterator<float[]>) data.iterator();
		
		for (int i = 0; i < this.numberOfIndices; i++) {
			it.next();
		}
		
		while (it.hasNext()) {
			listOfValues.add(it.next());
		}
		
		return listOfValues;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected ArrayList<float[]> extractIndices(Collection<?> data) {
		ArrayList<float[]> listOfIndices = new ArrayList<float[]>();
		
		Iterator<float[]> it = (Iterator<float[]>) data.iterator();
		
		for (int i = 0; i < this.numberOfIndices; i++) {
			listOfIndices.add(it.next());
		}
		
		return listOfIndices;
	}

}

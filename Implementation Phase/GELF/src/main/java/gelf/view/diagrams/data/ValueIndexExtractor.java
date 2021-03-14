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
		
		if (this.numberOfIndices == 0 && data != null) {
			listOfValues.addAll((Collection<? extends float[]>) data);
			return listOfValues;
		}
		
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
		if (this.numberOfIndices > 0) {
			ArrayList<float[]> listOfIndices = new ArrayList<float[]>();
			
			Iterator<float[]> it = (Iterator<float[]>) data.iterator();
			
			for (int i = 0; i < this.numberOfIndices; i++) {
				listOfIndices.add(it.next());
			}
			
			return listOfIndices;
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected ArrayList<String[]> extractValueDescriptions(Collection<?> descriptions) {
		if (descriptions != null) {
			ArrayList<String[]> listOfValues = new ArrayList<String[]>();
			
			if (this.numberOfIndices == 0 && descriptions != null) {
				listOfValues.addAll((Collection<? extends String[]>) descriptions);
				return listOfValues;
			}
			
			Iterator<String[]> it = (Iterator<String[]>) descriptions.iterator();
			
			for (int i = 0; i < this.numberOfIndices; i++) {
				it.next();
			}
			
			while (it.hasNext()) {
				listOfValues.add(it.next());
			}
			
			if (this.numberOfIndices == 0) {
				listOfValues.add(it.next());
			}
			
			return listOfValues;
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected ArrayList<String[]> extractIndexDescriptions(Collection<?> descriptions) {
		if (descriptions != null && this.numberOfIndices > 0) {
			ArrayList<String[]> listOfIndices = new ArrayList<String[]>();
			
			Iterator<String[]> it = (Iterator<String[]>) descriptions.iterator();
			
			for (int i = 0; i < this.numberOfIndices; i++) {
				listOfIndices.add(it.next());
			}
			
			return listOfIndices;
		} else {
			return null;
		}
	}

}

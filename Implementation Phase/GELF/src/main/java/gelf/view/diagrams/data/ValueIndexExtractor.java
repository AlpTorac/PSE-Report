package gelf.view.diagrams.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * The class responsible for extracting values and indices in the format below:
 * <p>
 * Each different index dimension as an array.
 * <p>
 * The values as single arrays according to the indices just like the
 * way one would store them in an ordinary array. In this format for values,
 * there will be {@code index1.length * ... * indexN-1.length} arrays of dimension
 * 1, which contain values for index tuples, where (index1, index2, ..., indexN)
 * stands for the value of the index tuple, in form:
	<ul>
	 <li>{(index1[0], index2[0], ..., indexN[0]), (index1[0], index2[0], ..., indexN[1]) , ..., (index1[0], index2[0], ..., indexN[indexN.length - 1])}
     <li>{(index1[0], ..., indexN-1[1], indexN[0]), (index1[0], ..., indexN-1[1], indexN[1]) , ..., (index1[0], ..., indexN-1[1], indexN[indexN.length - 1])}
     <li>...
     <li>{(index1[0], ..., indexN-1[indexN-1.length - 1], indexN[0]), (index1[0], ..., indexN-1[indexN-1.length - 1], indexN[1]) , ..., (index1[0], ..., indexN-1[indexN-1.length - 1], indexN[indexN.length - 1])}
     <li>...
     <li>{(index1[index1.length - 1], index2[index2.length - 1], ..., indexN[0]), ((index1[index1.length - 1], index2[index2.length - 1], ..., indexN[1]) , ..., (index1[index1.length - 1], index2[index2.length - 1], ..., indexN[indexN.length - 1])}
	</ul>
 * For example, if the data is 3 dimensional (x, y, z), there will be 3 arrays,
 * in which the indices for these dimensions will be:
 * <table>
 * <tr><td>{1, 2} // index1 = x</td></tr>
 * <tr><td>{0.1, 0.2} // index2 = y</td></tr>
 * <tr><td>{0.5, 0.7} // index3 = z</td></tr>
 * </table>
 * This means, there are {@code index1.length * index2.length = 4}
 * values in total, for the index triplets:
 * <p>
 * <ul>
 * <li> {(1, 0.1, 0.5), (1, 0.1, 0.7)} = {v_000, v_001}
 * <li> {(1, 0.2, 0.5), (1, 0.2, 0.7)} = {v_010, v_011}
 * <li> {(2, 0.1, 0.5), (2, 0.1, 0.7)} = {v_100, v_101}
 * <li> {(2, 0.2, 0.5), (2, 0.2, 0.7)} = {v_110, v_111}
 * </ul>
 * For this example, the {@link DiagramData#data data} would look like:
 * <table>
 * <tr><td>{1, 2} // index1 = x</td></tr>
 * <tr><td>{0.1, 0.2} // index2 = y</td></tr>
 * <tr><td>{0.5, 0.7} // index3 = z</td></tr>
 * <tr><td>{v_000, v_001} // values for (1, 0.1, 0.5), (1, 0.1, 0.7)</td></tr>
 * <tr><td>{v_010, v_011} // values for (1, 0.2, 0.5), (1, 0.2, 0.7)</td></tr>
 * <tr><td>{v_100, v_101} // values for (2, 0.1, 0.5), (2, 0.1, 0.7)</td></tr>
 * <tr><td>{v_110, v_111} // values for (2, 0.2, 0.5), (2, 0.2, 0.7)</td></tr>
 * </table>
 * The {@link DiagramData#descriptions descriptions} would have the same format.
 * @author Alp Torac Genc
 *
 */
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

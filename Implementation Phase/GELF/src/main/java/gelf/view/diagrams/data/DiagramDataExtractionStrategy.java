package gelf.view.diagrams.data;

import java.util.ArrayList;
import java.util.Collection;

/**
 * The super class of classes responsible for extracting information from {@link DiagramData#data data} and
 * {@link DiagramData#descriptions descriptions}.
 * @author Alp Torac Genc
 */
abstract class DiagramDataExtractionStrategy {
	/**
	 * {@link DiagramData#numberOfIndices numberOfIndices}
	 */
	protected int numberOfIndices;
	/**
	 * Constructs and initializes with the given number of indices.
	 * @param numberOfIndices the dimension of indices used.
	 */
	protected DiagramDataExtractionStrategy(int numberOfIndices) {
		this.numberOfIndices = numberOfIndices;
	}
	/**
	 * @param data {@link DiagramData#data data}
	 * @return The extracted values. Returns null, if there are no values to be extracted.
	 */
	protected abstract ArrayList<float[]> extractValues(Collection<?> data);
	/**
	 * @param data {@link DiagramData#data data}
	 * @return The extracted indices. Returns null, if there are no values to be extracted.
	 */
	protected abstract ArrayList<float[]> extractIndices(Collection<?> data);
	/**
	 * @param data {@link DiagramData#descriptions descriptions}
	 * @return The extracted value descriptions. Returns null, if there are no value descriptions to be extracted.
	 */
	protected abstract ArrayList<String[]> extractValueDescriptions(Collection<?> descriptions);
	/**
	 * @param data {@link DiagramData#descriptions descriptions}
	 * @return The extracted index descriptions. Returns null, if there are no index descriptions to be extracted.
	 */
	protected abstract ArrayList<String[]> extractIndexDescriptions(Collection<?> descriptions);
}

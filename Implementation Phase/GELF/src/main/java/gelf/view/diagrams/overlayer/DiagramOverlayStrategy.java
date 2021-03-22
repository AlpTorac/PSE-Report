package gelf.view.diagrams.overlayer;

import java.util.ArrayList;
import java.util.TreeSet;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.SettingsProvider;
import gelf.view.diagrams.builder.IDiagramBuilder;
import gelf.view.diagrams.components.DiagramAxis;
import gelf.view.diagrams.components.DiagramComponent;
import gelf.view.diagrams.components.DiagramComponentFactory;
import gelf.view.diagrams.components.DiagramValueDisplayComponent;
import gelf.view.diagrams.data.DiagramData;

/**
 * The super class of classes, which encapsulate algorithms for overlaying {@link gelf.view.diagrams.type.Diagram Diagrams}.
 * @author Alp Torac Genc
 */
public abstract class DiagramOverlayStrategy implements IDiagramBuilder {
	protected static DiagramComponentFactory factory = DiagramComponentFactory.getDiagramComponentFactory();
	protected static SettingsProvider settingsProvider = SettingsProvider.getInstance();
	/**
	 * @return Clones of {@link gelf.view.diagrams.data.DiagramData DiagramData} stored in the
	 * {@link gelf.view.diagrams.type.Diagram Diagrams} to be overlaid.
	 */
	public DiagramData[] cloneData() {
		IDiagram[] allDiagrams = this.getAllDiagrams();
		
		DiagramData[] clonedData = new DiagramData[allDiagrams.length];
		
		for (int i = 0; i < clonedData.length; i++) {
			clonedData[i] = allDiagrams[i].cloneDiagramData();
		}
		
		return clonedData;
	}
	/**
	 * Unifies the indices of all the given data.
	 * @param diagramData the given data array.
	 * @return The unified, (ascending) sorted indices of all the given data. Each tree
	 * set contains all possible values of an index dimension.
	 */
	protected ArrayList<TreeSet<Float>> unifyIndices(DiagramData[] diagramData) {
		ArrayList<ArrayList<float[]>> allIndices = new ArrayList<ArrayList<float[]>>();
		
		for (DiagramData data : diagramData) {
			ArrayList<float[]> currentIndices = data.extractIndices();
			allIndices.add(currentIndices);
		}
		
		ArrayList<TreeSet<Float>> indexList = new ArrayList<TreeSet<Float>>();
		
		// put all indices at row k together
		for (int k = 0; k < allIndices.get(0).size(); k++) {
			
			TreeSet<Float> allIndicesOfSameRow = new TreeSet<Float>();
			
			// iterate through all the diagramData and get all the indices at row k
			for (int i = 0; i < allIndices.size(); i++) {
				for (float index : allIndices.get(i).get(k)) { // indices at row k of diagram i
					allIndicesOfSameRow.add(index);
				}
			}
			
			indexList.add(allIndicesOfSameRow);
		}
		
		return indexList;
	}
	/**
	 * @param newIndices the given unified indices.
	 * @return The unified indices converted to the format specified in {@link gelf.view.diagrams.data.ValueIndexExtractor ValueIndexExtractor}.
	 */
	protected ArrayList<float[]> getNewIndexOfData(ArrayList<TreeSet<Float>> newIndices) {
		ArrayList<float[]> newIndexOfData = new ArrayList<float[]>();
		for (int i = 0; i < newIndices.size(); i++) {
			Float[] newIndexObjects = new Float[newIndices.get(i).size()];
			newIndices.get(i).toArray(newIndexObjects);
			float[] newIndexValues = new float[newIndexObjects.length];
			
			for (int j = 0; j < newIndexObjects.length; j++) {
				newIndexValues[j] = newIndexObjects[j];
			}
			newIndexOfData.add(newIndexValues);
		}
		return newIndexOfData;
	}
	/**
	 * @param diagramData a given array of data to be unified.
	 * @param newIndices the unified indices for the data given.
	 * @return The data with the new indices and their filled values.
	 * @see #fillEmptyValues
	 */
	protected DiagramData[] unifyData(DiagramData[] diagramData, ArrayList<TreeSet<Float>> newIndices) {
		ArrayList<float[]> newIndexOfData = this.getNewIndexOfData(newIndices);
		
		DiagramData[] unifiedData = new DiagramData[diagramData.length];
		
		for (int i = 0; i < unifiedData.length; i++) {
			DiagramData data = diagramData[i];
			ArrayList<float[]> oldIndices = data.extractIndices();
			ArrayList<float[]> oldValues = data.extractValues();
			
			float[] newValues = this.fillEmptyValues(newIndexOfData.get(0), oldIndices.get(0), oldValues.get(0));
			
			ArrayList<float[]> newData = new ArrayList<float[]>();
			
			newData.addAll(newIndexOfData);
			newData.add(newValues);
			
			unifiedData[i] = new DiagramData(newData, newIndices.size());
		}
		
		return unifiedData;
	}
	/**
	 * @param diagramData a given data array.
	 * @return All the {@link gelf.view.diagrams.data.DiagramData#extractValues values} stored in the given data in the format
	 * specified in {@link gelf.view.diagrams.data.ValueIndexExtractor ValueIndexExtractor}, but the value part being
	 * {@code diagramData.length} times larger, since the values are being combined.
	 */
	protected ArrayList<float[]> combineValues(DiagramData[] diagramData) {
		ArrayList<float[]> allValues = new ArrayList<float[]>();
		
		for (DiagramData data : diagramData) {
			ArrayList<float[]> values = data.extractValues();
			allValues.addAll(values);
		}
		
		return allValues;
	}
	/**
	 * @param diagramData a given data array.
	 * @return All the {@link gelf.view.diagrams.data.DiagramData#descriptions descriptions} stored in the given data in the format
	 * specified in {@link gelf.view.diagrams.data.ValueIndexExtractor ValueIndexExtractor}, where for each entry the longest description
	 * is taken.
	 */
	protected ArrayList<String[]> combineDescs(DiagramData[] diagramData) {
		ArrayList<float[]> indices = diagramData[0].extractIndices();
		ArrayList<String[]> indexDescs = new ArrayList<String[]>();
		
		for (int i = 0; i < indices.size(); i++) {
			float[] currentIndices = indices.get(i);
			String[] currentIndexDescs = new String[currentIndices.length];
			for (int j = 0; j < currentIndices.length; j++) {
				
				String currentDesc = "";

				for (int k = 0; k < diagramData.length; k++) {
					ArrayList<String[]> indexDescList = diagramData[k].extractIndexDescriptions();
					if (indexDescList != null && indexDescList.get(i)[j] != null &&
							currentDesc.length() < indexDescList.get(i)[j].length()) {
						currentDesc = indexDescList.get(i)[j];
					}
				}
				
				currentIndexDescs[j] = currentDesc;
			}
			indexDescs.add(currentIndexDescs);
		}
		
		return indexDescs;
	}
	
	/**
	 * Combines the values of the given data. The data must have the same indices.
	 * Only index descriptions are combined.
	 * 
	 * @param diagramData data to combine
	 * @return The combined {@link gelf.view.diagrams.data.DiagramData DiagramData}.
	 */
	protected DiagramData combineData(DiagramData[] diagramData) {
		ArrayList<float[]> indices = diagramData[0].extractIndices();
		ArrayList<float[]> dataArrays = new ArrayList<float[]>();
		if (indices != null) {
			dataArrays.addAll(indices);
		}
		dataArrays.addAll(this.combineValues(diagramData));
		
		if (this.containsDescriptions(diagramData)) {
			return new DiagramData(dataArrays, this.combineDescs(diagramData), diagramData[0].getNumberOfIndices());
		} else {
			return new DiagramData(dataArrays, diagramData[0].getNumberOfIndices());
		}
	}
	/**
	 * @param diagramData a given array of data
	 * @return True, if all the given data contains descriptions. Otherwise, false.
	 */
	private boolean containsDescriptions(DiagramData[] diagramData) {
		boolean containsDescs = false;
		
		int k = 0;
		while (!containsDescs && k < diagramData.length) {
			containsDescs = containsDescs || (diagramData[k].extractIndexDescriptions() != null)
					|| (diagramData[k].extractValueDescriptions() != null);
			k++;
		}
		
		return containsDescs;
	}
	/**
	 * Fills the values missing for indices by copying the smallest larger index for
	 * the position of the missing index. If the new indices contain larger index entries
	 * than original indices, the last index from original indices is duplicated till the
	 * array is filled.
	 * @param newIndices unified indices
	 * @param oldIndices original indices
	 * @param oldValues original values
	 * @return The value array for the new indices.
	 */
	private float[] fillEmptyValues(float[] newIndices, float[] oldIndices, float[] oldValues) {
		float[] newValues = new float[newIndices.length];
		int newLength = newValues.length;
		int oldLength = oldIndices.length;
		
		for (int i = 0, j = 0; i < newLength; i++) {
			if (j < oldLength && (Math.abs(newIndices[i] - oldIndices[j]) <= SettingsProvider.getTolerance())) {
				newValues[i] = oldValues[j];
				j++;
			} else if (j >= oldLength) {
				newValues[i] = oldValues[oldLength - 1];
			} else {
				newValues[i] = oldValues[j];
			}
		}
		
		return newValues;
	}
	/**
	 * @return All the {@link gelf.view.diagrams.IDiagram IDiagrams} stored in the concrete strategy classes inheriting from this class.
	 */
	protected abstract IDiagram[] getAllDiagrams();
	/**
	 * @return All the {@link gelf.view.diagrams.components.DiagramAxis DiagramAxes} in {@link gelf.view.diagrams.IDiagram IDiagrams}
	 * stored in the concrete strategy classes inheriting from this class.
	 */
	protected DiagramAxis[][] getAllAxes() {
		IDiagram[] diagrams = this.getAllDiagrams();
		
		int axesPerDiagram = diagrams[0].getDiagramAxisPrototypes().length;
		int lengthOfDiagrams = diagrams.length;
		
		DiagramAxis[][] allAxes = new DiagramAxis[axesPerDiagram][lengthOfDiagrams];
		for (int i = 0; i < lengthOfDiagrams; i++) {
			DiagramAxis[] axes = diagrams[i].getDiagramAxisPrototypes();
			
			for (int j = 0; j < axesPerDiagram; j++) {
				allAxes[j][i] = axes[j];
			}
		}
		
		return allAxes;
	}
	/**
	 * @see {@link gelf.view.diagrams.builder.IDiagramBuilder#buildValueDisplayComponentsForOneDiagram(DiagramData, int, DiagramAxis[], DiagramComponent[]) buildValueDisplayComponentsForOneDiagram}
	 */
	protected abstract DiagramValueDisplayComponent[] makeValueDisplayComponentsForOneDiagram(DiagramData diagramData, int orderInSameDiagram, DiagramAxis[] axes, DiagramComponent[] nonValueDisplayComponents);
	/**
	 * Builds all the {@link gelf.view.diagrams.components.DiagramValueDisplayComponent DiagramValueDisplayComponents} for
	 * the overlaid diagrams and overlaps them accordingly.
	 * @param axes the axes the result diagram will have
	 * @param nonValueDisplayComponents the components of the resulting diagram that are not {@link gelf.view.diagrams.components.DiagramValueDisplayComponent DiagramValueDisplayComponents}
	 * or {@link gelf.view.diagrams.components.DiagramAxis DiagramAxes}.
	 * @param diagramData the data of all the diagrams to be overlaid
	 * @return All the {@link gelf.view.diagrams.components.DiagramValueDisplayComponent DiagramValueDisplayComponents} of the
	 * diagram, which is the result of overlaying the diagrams specified in the given data.
	 */
	protected DiagramValueDisplayComponent[] makeValueDisplayComponents(DiagramAxis[] axes, DiagramComponent[] nonValueDisplayComponents, DiagramData[] diagramData) {
		int dataCount = diagramData.length;
		DiagramValueDisplayComponent[][] dvdcArray = new DiagramValueDisplayComponent[dataCount][];
		
		int totalValueDisplayComponentCount = 0;
		
		for (int index = 0; index < dataCount; index++) {
			DiagramValueDisplayComponent[] dvdcs = this.makeValueDisplayComponentsForOneDiagram(diagramData[index], index, axes, nonValueDisplayComponents);
			totalValueDisplayComponentCount += dvdcs.length;
			
			dvdcArray[index] = dvdcs;
		}
		
		this.setVisibilityAndColor(dvdcArray);
		
		DiagramValueDisplayComponent[] allDvdcs = new DiagramValueDisplayComponent[totalValueDisplayComponentCount];
		
		int currentIndex = 0;
		
		for (int i = 0; i < dvdcArray.length; i++) {
			for (int j = 0; j < dvdcArray[i].length; j++) {
				allDvdcs[currentIndex] = dvdcArray[i][j];
				currentIndex++;
			}
		}
		
		return allDvdcs;
	}
	/**
	 * @param currentDvdc a given {@link gelf.view.diagrams.components.DiagramValueDisplayComponent DiagramValueDisplayComponent}
	 * @param dvdcToCompareTo another given {@link gelf.view.diagrams.components.DiagramValueDisplayComponent DiagramValueDisplayComponent}
	 * @return True, if the currentDvdc would be covered by dvdcToCompareTo without any action taken. Otherwise, false.
	 */
	protected abstract boolean covers(DiagramValueDisplayComponent currentDvdc, DiagramValueDisplayComponent dvdcToCompareTo);
	/**
	 * Overlaps the both components by letting the covered component be visible and mixes the colors of both components, so that
	 * it is understandable that the both components have been overlapped.
	 * @param currentDvdc a given {@link gelf.view.diagrams.components.DiagramValueDisplayComponent DiagramValueDisplayComponent}
	 * @param dvdcToCompareTo another given {@link gelf.view.diagrams.components.DiagramValueDisplayComponent DiagramValueDisplayComponent}
	 */
	protected void overlapCovered(DiagramValueDisplayComponent currentDvdc, DiagramValueDisplayComponent dvdcToCompareTo) {
		if (this.covers(currentDvdc, dvdcToCompareTo)) {
			currentDvdc.decrementLayer();
			dvdcToCompareTo.setColor(SettingsProvider.getMixedColor(currentDvdc.getColor(), dvdcToCompareTo.getColor()));
		} else {
			dvdcToCompareTo.decrementLayer();
			currentDvdc.setColor(SettingsProvider.getMixedColor(currentDvdc.getColor(), dvdcToCompareTo.getColor()));
		}
	}
	/**
	 * Applies {@link #overlapCovered(DiagramValueDisplayComponent, DiagramValueDisplayComponent) overlapCovered} to the components
	 * that are on top of each other.
	 * @param dvdcArray all the DiagramValueDisplayComponents from the diagrams to be overlaid.
	 */
	protected void setVisibilityAndColor(DiagramValueDisplayComponent[][] dvdcArray) {
		for (int j = 0, i = 0; j < dvdcArray[i].length; j++) {
			for (;i < dvdcArray.length - 1; i++) {
				DiagramValueDisplayComponent currentDvdc = dvdcArray[i][j];
				
				for (int k = i + 1; k < dvdcArray.length && j < dvdcArray[k].length; k++) {
					DiagramValueDisplayComponent dvdcToCompareTo = dvdcArray[k][j];
					this.overlapCovered(currentDvdc, dvdcToCompareTo);
				}
			}
			i = 0;
		}
	}
	/**
	 * Overridden in sub-classes, if necessary.
	 * @param diagramData data from a diagram
	 * @return The components of the resulting diagram that are not {@link gelf.view.diagrams.components.DiagramValueDisplayComponent DiagramValueDisplayComponents}
	 * or {@link gelf.view.diagrams.components.DiagramAxis DiagramAxes}.
	 */
	protected DiagramComponent[] makeDiagramSpecificComponents(DiagramData[] diagramData) {
		return null;
	}
	/**
	 * @param data result of {@link #combineData}
	 * @param axes result of {@link #buildAxes}
	 * @param valueDisplayComponents result of {@link #makeValueDisplayComponents}
	 * @param nonValueDisplayComponents result of {@link #makeDiagramSpecificComponents}
	 * @return The result of overlaying all the diagrams in {@link #getAllDiagrams}.
	 */
	protected abstract IDiagram buildDiagram(DiagramData data, DiagramAxis[] axes, DiagramValueDisplayComponent[] valueDisplayComponents, DiagramComponent[] nonValueDisplayComponents);
	/**
	 * @return The result of overlaying all the diagrams in {@link #getAllDiagrams}.
	 */
	public IDiagram overlay() {
		DiagramData[] clonedData = this.cloneData();
		
		DiagramAxis[] axes = this.buildAxes();
		if (clonedData[0].getNumberOfIndices() > 0) {
			clonedData = this.unifyData(clonedData, this.unifyIndices(clonedData));
		}
		
		DiagramComponent[] dcs = this.makeDiagramSpecificComponents(clonedData);
		DiagramValueDisplayComponent[] dvdcs = this.makeValueDisplayComponents(axes, dcs, clonedData);
		
		return this.buildDiagram(this.combineData(clonedData), axes, dvdcs, dcs);
	}
	
	@Override
	public float getYAxisMinValue() {
		float minVal = Float.MAX_VALUE;
		
		DiagramAxis[][] axes = this.getAllAxes();
		
		for (int i = 0; i < axes[1].length; i++) {
			float currentMinCandidate = axes[1][i].getMin();
			if (minVal > currentMinCandidate) {
				minVal = currentMinCandidate;
			}
		}
		
		return minVal;
	}

	@Override
	public float getXAxisMaxValue() {
		float maxVal = -Float.MAX_VALUE;
		
		DiagramAxis[][] axes = this.getAllAxes();
		
		for (int i = 0; i < axes[0].length; i++) {
			float currentMaxCandidate = axes[0][i].getMax();
			if (maxVal < currentMaxCandidate) {
				maxVal = currentMaxCandidate;
			}
		}
		
		return maxVal;
	}

	@Override
	public float getYAxisMaxValue() {
		float maxVal = -Float.MAX_VALUE;
		
		DiagramAxis[][] axes = this.getAllAxes();
		
		for (int i = 0; i < axes[1].length; i++) {
			float currentMaxCandidate = axes[1][i].getMax();
			if (maxVal < currentMaxCandidate) {
				maxVal = currentMaxCandidate;
			}
		}
		
		return maxVal;
	}
}

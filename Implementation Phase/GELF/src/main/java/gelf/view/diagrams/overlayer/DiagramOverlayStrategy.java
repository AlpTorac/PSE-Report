package gelf.view.diagrams.overlayer;

import java.util.ArrayList;
import java.util.TreeSet;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.SettingsProvider;
import gelf.view.diagrams.components.DiagramAxis;
import gelf.view.diagrams.components.DiagramComponent;
import gelf.view.diagrams.components.DiagramComponentFactory;
import gelf.view.diagrams.components.DiagramValueDisplayComponent;
import gelf.view.diagrams.data.DiagramData;

public abstract class DiagramOverlayStrategy {
	protected static DiagramComponentFactory factory = DiagramComponentFactory.getDiagramComponentFactory();
	protected static SettingsProvider settingsProvider = SettingsProvider.getInstance();
	
	public abstract void setDiagrams(IDiagram[] diagrams);
	
	public DiagramData[] cloneData() {
		IDiagram[] allDiagrams = this.getAllDiagrams();
		
		DiagramData[] clonedData = new DiagramData[allDiagrams.length];
		
		for (int i = 0; i < clonedData.length; i++) {
			clonedData[i] = allDiagrams[i].cloneDiagramData();
		}
		
		return clonedData;
	}

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
	
	protected DiagramData[] unifyData(DiagramData[] diagramData, ArrayList<TreeSet<Float>> newIndices) {
		ArrayList<float[]> newIndexOfData = new ArrayList<float[]>();
		for (int i = 0; i < newIndices.size(); i++) {
			Float[] newIndexObjects = (Float[]) newIndices.get(i).toArray();
			float[] newIndexValues = new float[newIndexObjects.length];
			
			for (int j = 0; j < newIndexObjects.length; j++) {
				newIndexValues[j] = newIndexObjects[j];
			}
			newIndexOfData.add(newIndexValues);
		}
		
		DiagramData[] unifiedData = new DiagramData[diagramData.length];
		
		for (int i = 0; i < unifiedData.length; i++) {
			DiagramData data = diagramData[i];
			ArrayList<float[]> oldIndices = data.extractIndices();
			ArrayList<float[]> oldValues = data.extractValues();
			
			ArrayList<float[]> newValues = new ArrayList<float[]>();
			
			int a = 0;
			int b = 0;
			
			float[] newValueRow = null;
			if (newIndexOfData.size() < 2) {
				newValueRow = new float[newIndexOfData.get(newIndexOfData.size() - 1).length];
			}
			
			traverseIndicesAndSetNewValues(newValues, oldIndices, newIndexOfData, a, b, oldValues.get(a)[b], true, newValueRow);

			ArrayList<float[]> newData = new ArrayList<float[]>();
			
			newData.addAll(newIndexOfData);
			newData.addAll(newValues);
		}
		
		return unifiedData;
	}
	
	private void traverseIndicesAndSetNewValues(ArrayList<float[]> newValues, ArrayList<float[]> oldIndices,
			ArrayList<float[]> newIndexOfData, int row, int column, float lastOldValue,
			boolean isOldIndex, float[] newValueRow) {
			for (int b = column; b < newIndexOfData.get(row).length; b++) {
				if (row + 2 == newIndexOfData.size()) {
					if (newValueRow != null) {
						newValues.add(newValueRow);
					}
					newValueRow = new float[newIndexOfData.get(row + 1).length];
				}
				
				float oldIndex = oldIndices.get(row)[column];
				float newIndex = newIndexOfData.get(row)[column];
				
				if (oldIndex < newIndex) {
					break;
				}
				
				if (row + 1 < newIndexOfData.size()) {
					if (isOldIndex && (column < oldIndices.get(row).length) && oldIndex == newIndex) {
						traverseIndicesAndSetNewValues(newValues, oldIndices, newIndexOfData, row + 1, b, oldIndex, true, newValueRow);
					} else {
						traverseIndicesAndSetNewValues(newValues, oldIndices, newIndexOfData, row + 1, b, lastOldValue, false, newValueRow);
					}
				} else {
					newValueRow[column] = lastOldValue;
				}
			}
	}
	
	protected DiagramData getAverage(DiagramData[] diagramData) {
		ArrayList<float[]> values = diagramData[0].extractValues();
		
		float[][] allValues = new float[values.size()][];
		values.toArray(allValues);
		
		for (int i = 1; i < diagramData.length; i++) {
			ArrayList<float[]> iteratorValues = diagramData[i].extractValues();
			
			float[][] currentValues = new float[iteratorValues.size()][];
			iteratorValues.toArray(currentValues);
			
			for (int j = 0; j < allValues.length; j++) {
				float[] currentValueRow = currentValues[j];
				
				for (int k = 0; k < allValues[0].length; k++) {
					allValues[j][k] += currentValueRow[k];
				}
			}
		}
		
		ArrayList<float[]> averageValues = new ArrayList<float[]>();
		
		for (int j = 0; j < allValues.length; j++) {
			for (int k = 0; k < allValues.length; k++) {
				allValues[j][k] = allValues[j][k] / ((float) diagramData.length);
			}
			averageValues.add(allValues[j]);
		}
		
		ArrayList<float[]> indices = diagramData[0].extractIndices();
		
		ArrayList<float[]> averageDataCollection = new ArrayList<float[]>();
		
		averageDataCollection.addAll(indices);
		averageDataCollection.addAll(averageValues);
		
		DiagramData averageData = new DiagramData(averageDataCollection, diagramData[0].getNumberOfIndices());
		return averageData;
	}
	
	protected abstract IDiagram[] getAllDiagrams();
	
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
	
	protected DiagramAxis[] unifyAxes(DiagramAxis[][] allAxes) {
		int axesPerDiagram = allAxes.length;
		int diagramCount = allAxes[0].length;
		
		DiagramAxis[] visuallyShortestAxis = new DiagramAxis[axesPerDiagram];
		DiagramAxis[] axisWithMinimumValue = new DiagramAxis[axesPerDiagram];
		DiagramAxis[] axisWithMaximumValue = new DiagramAxis[axesPerDiagram];
		
		for (int i = 0; i < axesPerDiagram; i++) {
			visuallyShortestAxis[i] = allAxes[i][0];
			axisWithMinimumValue[i] = allAxes[i][0];
			axisWithMaximumValue[i] = allAxes[i][0];
		}
		
		for (int i = 0; i < axesPerDiagram; i++) {
			for (int j = 0; j < diagramCount; j++) {
				DiagramAxis currentAxis = allAxes[i][j];
				if (visuallyShortestAxis[i].getLineLength() > currentAxis.getLineLength()) {
					visuallyShortestAxis[i] = currentAxis;
				}
				if (axisWithMinimumValue[i].getMin() > currentAxis.getMin()) {
					axisWithMinimumValue[i] = currentAxis;
				}
				if (axisWithMaximumValue[i].getMax() < currentAxis.getMax()) {
					axisWithMaximumValue[i] = currentAxis;
				}
			}
		}
		
		DiagramAxis[] axes = new DiagramAxis[axesPerDiagram];
		
		for (int index = 0; index < axesPerDiagram; index++) {
			axes[index] = factory.createSolidAxis(visuallyShortestAxis[index].getLineStart(), visuallyShortestAxis[index].getLineEnd(),
					axisWithMinimumValue[index].getMin(), axisWithMaximumValue[index].getMax(),
					visuallyShortestAxis[index].getSteps(), visuallyShortestAxis[index].getColor(), visuallyShortestAxis[index].getLineThickness());
		}
		
		return axes;
	}
	
	protected abstract DiagramValueDisplayComponent[] makeValueDisplayComponentsForOneDiagram(DiagramData diagramData, int orderInSameDiagram, DiagramAxis[] axes, DiagramComponent[] nonValueDisplayComponents);
	
	protected DiagramValueDisplayComponent[] makeValueDisplayComponents(DiagramAxis[] axes, DiagramComponent[] nonValueDisplayComponents, DiagramData[] diagramData) {
//		ArrayList<DiagramValueDisplayComponent> dvdcList = new ArrayList<DiagramValueDisplayComponent>();
		int dataCount = diagramData.length;
		DiagramValueDisplayComponent[][] dvdcArray = new DiagramValueDisplayComponent[dataCount][];
		
		int totalValueDisplayComponentCount = 0;
		
		for (int index = 0; index < dataCount; index++) {
			DiagramValueDisplayComponent[] dvdcs = this.makeValueDisplayComponentsForOneDiagram(diagramData[index], index, axes, nonValueDisplayComponents);
			totalValueDisplayComponentCount += dvdcs.length;
			
			dvdcArray[index] = dvdcs;
		}
		
		this.configureVisibilityAndColor(dvdcArray);
		
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
	
	protected abstract void configureVisibilityAndColor(DiagramValueDisplayComponent[][] dvdcArray);
	
	protected DiagramComponent[] makeDiagramSpecificComponents(DiagramData[] diagramData) {
		return null;
	}
	
	protected abstract IDiagram buildDiagram(DiagramData data, DiagramAxis[] axes, DiagramValueDisplayComponent[] valueDisplayComponents, DiagramComponent[] nonValueDisplayComponents);
	
	public IDiagram overlay() {
		DiagramData[] clonedData = this.cloneData();
		
		DiagramData data = clonedData[0];
		
		DiagramAxis[] axes = this.unifyAxes(this.getAllAxes());
		if (clonedData[0].extractIndices().size() > 0) {
			clonedData = this.unifyData(clonedData, this.unifyIndices(clonedData));
		}
		
		DiagramComponent[] dcs = this.makeDiagramSpecificComponents(clonedData);
		DiagramValueDisplayComponent[] dvdcs = this.makeValueDisplayComponents(axes, dcs, clonedData);
		
		return this.buildDiagram(data, axes, dvdcs, dcs);
	}
}

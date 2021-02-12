package gelf.view.diagrams.overlayer;

import java.util.ArrayList;

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

	protected void unifyIndices(DiagramData[] diagramData) {
		ArrayList<ArrayList<float[]>> allIndices = new ArrayList<ArrayList<float[]>>();
		
		for (DiagramData data : diagramData) {
			ArrayList<float[]> currentIndices = data.extractIndices();
			allIndices.add(currentIndices);
		}
		ArrayList<ArrayList<Float>> indexList = new ArrayList<ArrayList<Float>>();
		
		// put all indices at row k together
		for (int k = 0; k < allIndices.get(0).size(); k++) {
			
			ArrayList<Float> allIndicesOfSameRow = new ArrayList<Float>();
			
			// iterate through all the diagramData and get all the indices at row k
			for (int i = 0; i < allIndices.size(); i++) {
				for (float index : allIndices.get(i).get(k)) { // indices at row k of diagram i
					allIndicesOfSameRow.add(index);
				}
			}
			
			indexList.add(allIndicesOfSameRow);
		}
		
		// sort all the indices
		for (ArrayList<Float> rowOfIndices : indexList) {
			rowOfIndices.sort(null);
		}
	}
	
	protected DiagramData getAverage(DiagramData[] diagramData) {
		float[][] allValues = (float[][]) diagramData[0].extractValues().toArray();
		
		for (int i = 1; i < diagramData.length; i++) {
			float[][] currentValues = (float[][]) diagramData[i].extractValues().toArray();
			
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
		DiagramAxis visuallyShortestAxis = allAxes[0][0];
		DiagramAxis axisWithMinimumValue = allAxes[0][0];
		DiagramAxis axisWithMaximumValue = allAxes[0][0];
		
		int axesPerDiagram = allAxes.length;
		int diagramCount = allAxes[0].length;
		
		for (int i = 0; i < axesPerDiagram; i++) {
			for (int j = 0; j < diagramCount; j++) {
				DiagramAxis currentAxis = allAxes[i][j];
				if (visuallyShortestAxis.getLineLength() > currentAxis.getLineLength()) {
					visuallyShortestAxis = currentAxis;
				}
				if (axisWithMinimumValue.getMin() > currentAxis.getMin()) {
					axisWithMinimumValue = currentAxis;
				}
				if (axisWithMinimumValue.getMax() < currentAxis.getMax()) {
					axisWithMinimumValue = currentAxis;
				}
			}
		}
		
		DiagramAxis[] axes = new DiagramAxis[axesPerDiagram];
		
		for (int index = 0; index < axesPerDiagram; index++) {
			axes[index] = factory.createSolidAxis(visuallyShortestAxis.getLineStart(), visuallyShortestAxis.getLineEnd(),
					axisWithMinimumValue.getMin(), axisWithMaximumValue.getMax(),
					visuallyShortestAxis.getSteps(), visuallyShortestAxis.getColor(), visuallyShortestAxis.getLineThickness());
		}
		
		return axes;
	}
	
	protected abstract DiagramValueDisplayComponent[] makeValueDisplayComponents(DiagramData[] diagramData);
	protected abstract DiagramComponent[] makeNonValueDisplayComponents(DiagramData[] diagramData);
	protected abstract IDiagram buildDiagram(DiagramData data, DiagramAxis[] axes, DiagramValueDisplayComponent[] valueDisplayComponents, DiagramComponent[] nonValueDisplayComponents);
	
	public IDiagram overlay() {
		DiagramData[] clonedData = this.cloneData();
		
		DiagramData averages = this.getAverage(clonedData);
		
		DiagramAxis[] axes = this.unifyAxes(this.getAllAxes());
		this.unifyIndices(clonedData);
		DiagramValueDisplayComponent[] dvdcs = this.makeValueDisplayComponents(clonedData);
		DiagramComponent[] dcs = this.makeNonValueDisplayComponents(clonedData);
		
		return this.buildDiagram(averages, axes, dvdcs, dcs);
	}
}

package gelf.view.diagrams.overlayer;

import java.util.ArrayList;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.builder.IBarChartBuilder;
import gelf.view.diagrams.components.DiagramAxis;
import gelf.view.diagrams.components.DiagramComponent;
import gelf.view.diagrams.components.DiagramValueDisplayComponent;
import gelf.view.diagrams.data.DiagramData;
import gelf.view.diagrams.type.BarChart;

public class BarChartOverlayStrategy extends DiagramOverlayStrategy implements IBarChartBuilder {

	private BarChart[] barCharts;
	
	public BarChartOverlayStrategy() {
		
	}
	
	public BarChartOverlayStrategy(BarChart[] barCharts) {
		this.barCharts = barCharts;
	}

	@Override
	public void setDiagrams(IDiagram[] diagrams) {
		BarChart[] barCharts = new BarChart[diagrams.length];
		
		for (int i = 0; i < barCharts.length; i++) {
			barCharts[i] = (BarChart) diagrams[i];
		}
		
		this.barCharts = barCharts;
	}

	@Override
	public BarChart buildDiagram(DiagramData data, DiagramAxis[] axes, DiagramValueDisplayComponent[] valueDisplayComponents,
			DiagramComponent[] nonValueDisplayComponents) {
		if (data.extractValueDescriptions() != null) {
			String[] descs = data.extractValueDescriptions().get(0);
			
			//Add empty description to the start and end
			String[] adaptedDescs = new String[descs.length + 2];
			adaptedDescs[0] = "";
			adaptedDescs[adaptedDescs.length - 1] = "";
			
			for (int i = 1; i < adaptedDescs.length - 1; i++) {
				adaptedDescs[i] = descs[i - 1];
			}
			
			axes[0].setStepDisplays(adaptedDescs);
		}
		return new BarChart(data, axes, valueDisplayComponents, nonValueDisplayComponents);
	}

	@Override
	protected IDiagram[] getAllDiagrams() {
		return this.barCharts;
	}

	@Override
	protected DiagramValueDisplayComponent[] makeValueDisplayComponentsForOneDiagram(DiagramData diagramData, int orderInSameDiagram,
			DiagramAxis[] axes, DiagramComponent[] nonValueDisplayComponents) {
		return this.buildValueDisplayComponentsForOneDiagram(diagramData, orderInSameDiagram, axes, nonValueDisplayComponents);
	}

	@Override
	protected boolean covers(DiagramValueDisplayComponent currentDvdc,
			DiagramValueDisplayComponent dvdcToCompareTo) {
		float currentVal = Math.abs(currentDvdc.getValue());
		float nextVal = Math.abs(dvdcToCompareTo.getValue());
		return (currentVal >= nextVal);
	}
	
	@Override
	protected ArrayList<String[]> combineDescs(DiagramData[] diagramData) {
		float[] longestValArr = new float[0];

		for (DiagramData data : diagramData) {
			float[] longestValArrCandidate = data.extractValues().get(0);
			if (longestValArr.length < longestValArrCandidate.length) {
				longestValArr = longestValArrCandidate;
			}
		}

		ArrayList<String[]> descs = new ArrayList<String[]>();
		String[] descArr = new String[longestValArr.length];

		for (int j = 0; j < longestValArr.length; j++) {
			String currentDesc = "";

			for (int k = 0; k < diagramData.length; k++) {
				String[] currentDescs = diagramData[k].extractValueDescriptions().get(0);
				if (currentDescs != null && j < currentDescs.length && currentDescs[j] != null &&
						currentDesc.length() < currentDescs[j].length()) {
					currentDesc = currentDescs[j];
				}
			}

			descArr[j] = currentDesc;
		}

		descs.add(descArr);

		return descs;
	}
}

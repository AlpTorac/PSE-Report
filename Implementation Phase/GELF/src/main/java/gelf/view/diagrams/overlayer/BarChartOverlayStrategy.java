package gelf.view.diagrams.overlayer;

import java.awt.Container;
import java.util.ArrayList;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.builder.IBarChartBuilder;
import gelf.view.diagrams.components.DiagramAxis;
import gelf.view.diagrams.components.DiagramComponent;
import gelf.view.diagrams.components.DiagramValueDisplayComponent;
import gelf.view.diagrams.data.DiagramData;
import gelf.view.diagrams.type.BarChart;

/**
 * The implementation of the strategy responsible for overlaying {@link gelf.view.diagrams.type.BarChart BarChart}.
 * @author Alp Torac Genc
 */
public class BarChartOverlayStrategy extends DiagramOverlayStrategy implements IBarChartBuilder {
	private BarChart[] barCharts;
	private Container container;
	
	public BarChartOverlayStrategy(Container container, BarChart[] barCharts) {
		this.container = container;
		this.barCharts = barCharts;
	}

	@Override
	public BarChart buildDiagram(DiagramData data, DiagramAxis[] axes, DiagramValueDisplayComponent[] valueDisplayComponents,
			DiagramComponent[] nonValueDisplayComponents) {
		if (data.extractValueDescriptions() != null) {
			int j = 0;
			int stepDisplayLength = axes[0].getStepDisplays().length;
			int descLength = stepDisplayLength - 2;
			ArrayList<String[]> descriptions = data.extractValueDescriptions();
			while (j < descriptions.size() && descriptions.get(j) != null &&
					(descriptions.get(j).length < descLength)) {
				j++;
			}
			String[] descs = descriptions.get(j);
			
			
			//Add empty description to the start and end
			String[] adaptedDescs = new String[stepDisplayLength];
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
		ArrayList<String[]> descs = new ArrayList<String[]>();

		for (int k = 0; k < diagramData.length; k++) {
			String[] currentDescs = diagramData[k].extractValueDescriptions().get(0);
			descs.add(currentDescs);
		}

		return descs;
	}

	@Override
	public Container getContainer() {
		return this.container;
	}
}

package gelf.view.diagrams.overlayer;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.components.DiagramAxis;
import gelf.view.diagrams.components.DiagramComponent;
import gelf.view.diagrams.components.DiagramValueDisplayComponent;
import gelf.view.diagrams.data.DiagramData;
import gelf.view.diagrams.type.BarChart;

public class BarChartOverlayStrategy extends DiagramOverlayStrategy {

	private BarChart[] barCharts;
	
	public BarChartOverlayStrategy() {
		
	}
	
	public BarChartOverlayStrategy(BarChart[] barCharts) {
		this.barCharts = barCharts;
	}

	@Override
	public void setDiagrams(IDiagram[] diagrams) {
		this.barCharts = (BarChart[]) diagrams;
	}

	@Override
	public DiagramValueDisplayComponent[] makeValueDisplayComponents(DiagramData[] diagramData) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DiagramComponent[] makeNonValueDisplayComponents(DiagramData[] diagramData) {
		return null;
	}

	@Override
	public BarChart buildDiagram(DiagramData data, DiagramAxis[] axes, DiagramValueDisplayComponent[] valueDisplayComponents,
			DiagramComponent[] nonValueDisplayComponents) {
		return new BarChart(data, axes, valueDisplayComponents, nonValueDisplayComponents);
	}

	@Override
	protected IDiagram[] getAllDiagrams() {
		return this.barCharts;
	}
}

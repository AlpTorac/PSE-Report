package gelf.view.diagrams.overlayer;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.type.BarChart;

public class BarChartOverlayStrategy implements IDiagramOverlayStrategy {

	private BarChart[] barCharts;
	
	public BarChartOverlayStrategy() {
		
	}
	
	public BarChartOverlayStrategy(BarChart[] barCharts) {
		this.barCharts = barCharts;
	}
	
	@Override
	public BarChart overlay() {
		return null;
	}

	@Override
	public void setDiagrams(IDiagram[] diagrams) {
		this.barCharts = (BarChart[]) diagrams;
	}
}

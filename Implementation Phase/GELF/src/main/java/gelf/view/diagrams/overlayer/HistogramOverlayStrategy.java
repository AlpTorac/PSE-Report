package gelf.view.diagrams.overlayer;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.type.BarChart;
import gelf.view.diagrams.type.Histogram;

public class HistogramOverlayStrategy implements IDiagramOverlayStrategy {

	private Histogram[] histograms;
	
	public HistogramOverlayStrategy() {
		
	}
	
	public HistogramOverlayStrategy(Histogram[] histograms) {
		this.histograms = histograms;
	}
	
	@Override
	public Histogram overlay() {
		return null;
	}

	@Override
	public void setDiagrams(IDiagram[] diagrams) {
		this.histograms = (Histogram[]) diagrams;
	}
}

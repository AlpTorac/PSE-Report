package gelf.view.diagrams.overlayer;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.components.DiagramAxis;
import gelf.view.diagrams.components.DiagramComponent;
import gelf.view.diagrams.components.DiagramValueDisplayComponent;
import gelf.view.diagrams.data.DiagramData;
import gelf.view.diagrams.type.Histogram;

public class HistogramOverlayStrategy extends DiagramOverlayStrategy {

	private Histogram[] histograms;
	
	public HistogramOverlayStrategy() {
		
	}
	
	public HistogramOverlayStrategy(Histogram[] histograms) {
		this.histograms = histograms;
	}

	@Override
	public void setDiagrams(IDiagram[] diagrams) {
		this.histograms = (Histogram[]) diagrams;
	}

	@Override
	protected IDiagram[] getAllDiagrams() {
		return this.histograms;
	}

	@Override
	protected DiagramValueDisplayComponent[] makeValueDisplayComponents(DiagramData[] diagramData) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected DiagramComponent[] makeNonValueDisplayComponents(DiagramData[] diagramData) {
		return null;
	}

	@Override
	protected IDiagram buildDiagram(DiagramData data, DiagramAxis[] axes,
			DiagramValueDisplayComponent[] valueDisplayComponents, DiagramComponent[] nonValueDisplayComponents) {
		return new Histogram(data, axes, valueDisplayComponents, nonValueDisplayComponents);
	}
}

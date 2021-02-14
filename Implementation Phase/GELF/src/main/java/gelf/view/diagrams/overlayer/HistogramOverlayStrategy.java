package gelf.view.diagrams.overlayer;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.builder.IHistogramBuilder;
import gelf.view.diagrams.components.DiagramAxis;
import gelf.view.diagrams.components.DiagramComponent;
import gelf.view.diagrams.components.DiagramValueDisplayComponent;
import gelf.view.diagrams.data.DiagramData;
import gelf.view.diagrams.type.Histogram;

public class HistogramOverlayStrategy extends DiagramOverlayStrategy implements IHistogramBuilder {

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
	protected IDiagram buildDiagram(DiagramData data, DiagramAxis[] axes,
			DiagramValueDisplayComponent[] valueDisplayComponents, DiagramComponent[] nonValueDisplayComponents) {
		return new Histogram(data, axes, valueDisplayComponents, nonValueDisplayComponents);
	}

	@Override
	protected DiagramValueDisplayComponent[] makeValueDisplayComponentsForOneDiagram(DiagramData diagramData, int orderInSameDiagram,
			DiagramAxis[] axes, DiagramComponent[] nonValueDisplayComponents) {
		return this.buildValueDisplayComponentsForOneDiagram(diagramData, orderInSameDiagram, axes, nonValueDisplayComponents);
	}
}

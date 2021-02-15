package gelf.view.diagrams.overlayer;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.builder.IHistogramBuilder;
import gelf.view.diagrams.components.DiagramAxis;
import gelf.view.diagrams.components.DiagramComponent;
import gelf.view.diagrams.components.DiagramValueDisplayComponent;
import gelf.view.diagrams.components.HistogramBar;
import gelf.view.diagrams.data.DiagramData;
import gelf.view.diagrams.type.BarChart;
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
		Histogram[] histograms = new Histogram[diagrams.length];
		
		for (int i = 0; i < histograms.length; i++) {
			histograms[i] = (Histogram) diagrams[i];
		}
		
		this.histograms = histograms;
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

	@Override
	protected boolean covers(DiagramValueDisplayComponent currentDvdc,
			DiagramValueDisplayComponent dvdcToCompareTo) {
		if (!(currentDvdc instanceof HistogramBar && dvdcToCompareTo instanceof HistogramBar)) {
			return false;
		}
		
		HistogramBar cDvdc = (HistogramBar) currentDvdc;
		HistogramBar nDvdc = (HistogramBar) dvdcToCompareTo;
		
		double minXCurrent = cDvdc.getTopLeftInDiagram().getXCoordinate();
		float valueCurrent = cDvdc.getValue();
		double maxYCurrent = cDvdc.getBottomRightInDiagram().getYCoordinate();
		
		double minXNext = nDvdc.getTopLeftInDiagram().getXCoordinate();
		float valueNext = nDvdc.getValue();
		double maxYNext = nDvdc.getBottomRightInDiagram().getYCoordinate();
		
		return (minXCurrent <= minXNext) && (valueCurrent >= valueNext) && (maxYCurrent >= maxYNext);
	}
}

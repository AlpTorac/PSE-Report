package gelf.view.diagrams.overlayer;

import java.awt.Container;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.builder.IHistogramBuilder;
import gelf.view.diagrams.components.DiagramAxis;
import gelf.view.diagrams.components.DiagramComponent;
import gelf.view.diagrams.components.DiagramValueDisplayComponent;
import gelf.view.diagrams.components.HistogramBar;
import gelf.view.diagrams.data.DiagramData;
import gelf.view.diagrams.type.Histogram;
/**
 * The implementation of the strategy responsible for overlaying {@link gelf.view.diagrams.type.Histogram Histograms}.
 * @author Alp Torac Genc
 */
public class HistogramOverlayStrategy extends DiagramOverlayStrategy implements IHistogramBuilder {

	private Container container;
	private Histogram[] histograms;
	
	public HistogramOverlayStrategy(Container container, Histogram[] histograms) {
		this.container = container;
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
		double valueCurrent = cDvdc.getHeight();
		double maxYCurrent = cDvdc.getBottomRightInDiagram().getYCoordinate();
		
		double minXNext = nDvdc.getTopLeftInDiagram().getXCoordinate();
		double valueNext = nDvdc.getHeight();
		double maxYNext = nDvdc.getBottomRightInDiagram().getYCoordinate();
		
		return (minXCurrent <= minXNext) && (valueCurrent >= valueNext) && (maxYCurrent >= maxYNext);
	}
	
	@Override
	public Container getContainer() {
		return this.container;
	}
}

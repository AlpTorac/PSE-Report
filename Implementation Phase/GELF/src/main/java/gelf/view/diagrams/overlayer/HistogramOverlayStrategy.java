package gelf.view.diagrams.overlayer;

import java.awt.Color;
import java.util.ArrayList;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.components.DiagramAxis;
import gelf.view.diagrams.components.DiagramComponent;
import gelf.view.diagrams.components.DiagramValueDisplayComponent;
import gelf.view.diagrams.components.HistogramBar;
import gelf.view.diagrams.components.PositionIn2DDiagram;
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
	public DiagramValueDisplayComponent[] makeValueDisplayComponents(DiagramAxis[] axes, DiagramComponent[] nonValueDisplayComponents, DiagramData[] diagramData) {
		ArrayList<DiagramValueDisplayComponent> dvdcList = new ArrayList<DiagramValueDisplayComponent>();
		
		for (int index = 0; index < diagramData.length; index++) {
			DiagramData data = diagramData[index];

			float[] indices = data.extractIndices().get(0);
			float[] values = data.extractValues().get(0);
			int dvdcCount = values.length;
			
			DiagramValueDisplayComponent[] dvdc = new DiagramValueDisplayComponent[dvdcCount];
			
			Color barColor = settingsProvider.getValueDisplayComponentColorAt(index);
			int thickness = settingsProvider.getBarBorderThickness();
			
			for (int i = 0; i < indices.length - 1; i++) {
				dvdc[i] = this.makeHistogramBar(axes, indices, values, i, barColor, thickness);
			}
			
			float additionalMaxIndex = indices[indices.length - 1] * settingsProvider.getHistogramIndexEndIndexFactor();

			dvdc[indices.length - 1] = this.makeLastHistogramBar(axes, indices, values, additionalMaxIndex, barColor, thickness);
		
		}
		
		return (DiagramValueDisplayComponent[]) dvdcList.toArray();
	}
	
	private HistogramBar makeHistogramBar(DiagramAxis[] axes, float[] indices, float[] values, int i, Color barColor, int thickness) {
		PositionIn2DDiagram topLeft = factory.makePositionInDiagram(axes[0], indices[i], axes[1], values[i]);
		PositionIn2DDiagram bottomRight = factory.makePositionInDiagram(axes[0], indices[i + 1], axes[1], 0);
		
		return factory.createHistogramBar(barColor, values[i], topLeft, bottomRight, thickness);
	}
	
	private HistogramBar makeLastHistogramBar(DiagramAxis[] axes, float[] indices, float[] values, double lastBottomRightX, Color barColor, int thickness) {
		PositionIn2DDiagram topLeft = factory.makePositionInDiagram(axes[0], indices[indices.length - 1], axes[1], values[indices.length - 1]);
		PositionIn2DDiagram bottomRight = factory.makePositionInDiagram(axes[0], lastBottomRightX, axes[1], 0);
		
		return factory.createHistogramBar(barColor, values[indices.length - 1], topLeft, bottomRight, thickness);
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

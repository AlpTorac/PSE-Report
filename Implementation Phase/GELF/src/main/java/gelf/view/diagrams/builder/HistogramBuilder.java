package gelf.view.diagrams.builder;

import java.awt.Color;
import java.awt.Container;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.components.DiagramAxis;
import gelf.view.diagrams.components.DiagramComponent;
import gelf.view.diagrams.components.DiagramComponentFactory;
import gelf.view.diagrams.components.DiagramValueDisplayComponent;
import gelf.view.diagrams.components.HistogramBar;
import gelf.view.diagrams.components.PositionIn2DDiagram;
import gelf.view.diagrams.components.PositionInFrame;
import gelf.view.diagrams.type.Histogram;

public class HistogramBuilder extends DiagramBuilder {

	public HistogramBuilder(Container container) {
		super(container);
	}

	@Override
	protected DiagramValueDisplayComponent[] buildValueDisplayComponents(DiagramAxis[] axes,
			DiagramComponent[] diagramSpecificComponent) {
		float[] indices = this.data.extractIndices().get(0);
		float[] values = this.data.extractValues().get(0);
		int dvdcCount = values.length;
		
		DiagramValueDisplayComponent[] dvdc = new DiagramValueDisplayComponent[dvdcCount];
		
		Color barColor = settingsProvider.getValueDisplayComponentColorAt(0);
		int thickness = settingsProvider.getBarBorderThickness();
		
		for (int i = 0; i < indices.length - 1; i++) {
			dvdc[i] = this.makeHistogramBar(axes, indices, values, i, barColor, thickness);
		}
		
		float additionalMaxIndex = indices[indices.length - 1] * settingsProvider.getHistogramIndexEndIndexFactor();

		dvdc[indices.length - 1] = this.makeLastHistogramBar(axes, indices, values, additionalMaxIndex, barColor, thickness);
		
		return dvdc;
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
	public IDiagram buildDiagram() {
		DiagramAxis[] axes = this.buildAxes();
		DiagramValueDisplayComponent[] dvdc = this.buildValueDisplayComponents(axes, null);
		return new Histogram(this.data, axes, dvdc, null);
	}

	@Override
	protected float getXAxisMaxValue() {
		return this.data.getMaximumIndex() * settingsProvider.getHistogramIndexEndIndexFactor();
	}

	@Override
	protected float getYAxisMaxValue() {
		return this.data.getMaximumValue();
	}
}

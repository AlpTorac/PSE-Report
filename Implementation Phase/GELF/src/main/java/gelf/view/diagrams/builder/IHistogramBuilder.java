package gelf.view.diagrams.builder;

import java.awt.Color;

import gelf.view.diagrams.components.DiagramAxis;
import gelf.view.diagrams.components.DiagramComponent;
import gelf.view.diagrams.components.DiagramValueDisplayComponent;
import gelf.view.diagrams.components.HistogramBar;
import gelf.view.diagrams.components.PositionIn2DDiagram;
import gelf.view.diagrams.data.DiagramData;

public interface IHistogramBuilder extends IDiagramBuilder {
	@Override
	public default DiagramValueDisplayComponent[] buildValueDisplayComponentsForOneDiagram(DiagramData data, DiagramAxis[] axes,
			DiagramComponent[] diagramSpecificComponent) {
		float[] indices = data.extractIndices().get(0);
		float[] values = data.extractValues().get(0);
		int dvdcCount = values.length;
		
		DiagramValueDisplayComponent[] dvdc = new DiagramValueDisplayComponent[dvdcCount];
		
		Color barColor = this.getSettingsProvider().getValueDisplayComponentColorAt(0);
		int thickness = this.getSettingsProvider().getBarBorderThickness();
		
		for (int i = 0; i < indices.length - 1; i++) {
			dvdc[i] = this.makeHistogramBar(axes, indices, values, i, barColor, thickness);
		}
		
		float additionalMaxIndex = indices[indices.length - 1] * this.getSettingsProvider().getHistogramIndexEndIndexFactor();

		dvdc[indices.length - 1] = this.makeLastHistogramBar(axes, indices, values, additionalMaxIndex, barColor, thickness);
		
		return dvdc;
	}
	
	@Override
	public default DiagramComponent[] buildDiagramSpecificComponentForOneDiagram(DiagramData data) {
		return null;
	}
	
	default HistogramBar makeHistogramBar(DiagramAxis[] axes, float[] indices, float[] values, int i, Color barColor, int thickness) {
		PositionIn2DDiagram topLeft = this.getDiagramComponentFactory().makePositionInDiagram(axes[0], indices[i], axes[1], values[i]);
		PositionIn2DDiagram bottomRight = this.getDiagramComponentFactory().makePositionInDiagram(axes[0], indices[i + 1], axes[1], 0);
		
		return this.getDiagramComponentFactory().createHistogramBar(barColor, values[i], topLeft, bottomRight, thickness);
	}
	
	default HistogramBar makeLastHistogramBar(DiagramAxis[] axes, float[] indices, float[] values, double lastBottomRightX, Color barColor, int thickness) {
		PositionIn2DDiagram topLeft = this.getDiagramComponentFactory().makePositionInDiagram(axes[0], indices[indices.length - 1], axes[1], values[indices.length - 1]);
		PositionIn2DDiagram bottomRight = this.getDiagramComponentFactory().makePositionInDiagram(axes[0], lastBottomRightX, axes[1], 0);
		
		return this.getDiagramComponentFactory().createHistogramBar(barColor, values[indices.length - 1], topLeft, bottomRight, thickness);
	}
}

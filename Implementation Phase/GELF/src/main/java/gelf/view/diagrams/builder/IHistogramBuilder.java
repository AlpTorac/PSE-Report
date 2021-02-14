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
	public default DiagramValueDisplayComponent[] buildValueDisplayComponentsForOneDiagram(DiagramData data, int orderInSameDiagram,
			DiagramAxis[] axes, DiagramComponent[] diagramSpecificComponent) {
		float[] indices = data.extractIndices().get(0);
		float[] values = data.extractValues().get(0);
		int dvdcCount = values.length;
		
		DiagramValueDisplayComponent[] dvdc = new DiagramValueDisplayComponent[dvdcCount];
		
		Color barColor = this.getColorForDiagram(orderInSameDiagram);
		int thickness = this.getSettingsProvider().getBarBorderThickness();
		
		dvdc[0] = this.makeFirstHistogramBar(axes, indices, values, barColor, thickness);
		
		for (int i = 1; i < indices.length - 1; i++) {
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
	
	default HistogramBar makeFirstHistogramBar(DiagramAxis[] axes, float[] indices, float[] values, Color barColor, int thickness) {
		PositionIn2DDiagram topLeft = this.getDiagramComponentFactory().makePositionInDiagram(axes[0], 0, axes[1], values[0]);
		
		PositionIn2DDiagram bottomRight = this.getDiagramComponentFactory().makePositionInDiagram(axes[0], this.getBarWidth(indices, 0), axes[1], 0);
		
		return this.getDiagramComponentFactory().createHistogramBar(barColor, values[0], topLeft, bottomRight, thickness);
	}
	
	default HistogramBar makeHistogramBar(DiagramAxis[] axes, float[] indices, float[] values, int i, Color barColor, int thickness) {
		PositionIn2DDiagram topLeft = this.getDiagramComponentFactory().makePositionInDiagram(axes[0], this.getBarWidth(indices, i - 1), axes[1], values[i]);
		PositionIn2DDiagram bottomRight = this.getDiagramComponentFactory().makePositionInDiagram(axes[0], this.getBarWidth(indices, i), axes[1], 0);
		
		return this.getDiagramComponentFactory().createHistogramBar(barColor, values[i], topLeft, bottomRight, thickness);
	}
	
	default HistogramBar makeLastHistogramBar(DiagramAxis[] axes, float[] indices, float[] values, double lastBottomRightX, Color barColor, int thickness) {
		PositionIn2DDiagram topLeft = this.getDiagramComponentFactory().makePositionInDiagram(axes[0], this.getBarWidth(indices, indices.length - 2), axes[1], values[indices.length - 1]);
		PositionIn2DDiagram bottomRight = this.getDiagramComponentFactory().makePositionInDiagram(axes[0], lastBottomRightX, axes[1], 0);
		
		return this.getDiagramComponentFactory().createHistogramBar(barColor, values[indices.length - 1], topLeft, bottomRight, thickness);
	}
	
	default float getBarWidth(float[] indices, int index) {
		return (indices[index] + indices[index + 1]) / 2f;
	}
}

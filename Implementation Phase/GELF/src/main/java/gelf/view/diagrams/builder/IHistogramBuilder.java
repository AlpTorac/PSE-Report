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
		
		dvdc[0] = this.makeFirstHistogramBar(axes, indices[0], values[0], barColor, thickness);
		
		for (int i = 1; i < indices.length; i++) {
			dvdc[i] = this.makeHistogramBar(axes, indices[i-1], indices[i], values[i], barColor, thickness);
		}
		
		return dvdc;
	}
	
	@Override
	public default DiagramComponent[] buildDiagramSpecificComponentForOneDiagram(DiagramData data) {
		return null;
	}
	
	default HistogramBar makeFirstHistogramBar(DiagramAxis[] axes, float endIndex, float value, Color barColor, int thickness) {
		PositionIn2DDiagram topLeft = this.getDiagramComponentFactory().makePositionInDiagram(axes[0], 0, axes[1], value);
		
		PositionIn2DDiagram bottomRight = this.getDiagramComponentFactory().makePositionInDiagram(axes[0], endIndex, axes[1], 0);
		
		return this.getDiagramComponentFactory().createHistogramBar(barColor, value, topLeft, bottomRight, thickness);
	}
	
	default HistogramBar makeHistogramBar(DiagramAxis[] axes, float startIndex, float endIndex, float value, Color barColor, int thickness) {
		PositionIn2DDiagram topLeft = this.getDiagramComponentFactory().makePositionInDiagram(axes[0], startIndex, axes[1], value);
		PositionIn2DDiagram bottomRight = this.getDiagramComponentFactory().makePositionInDiagram(axes[0], endIndex, axes[1], 0);
		
		return this.getDiagramComponentFactory().createHistogramBar(barColor, value, topLeft, bottomRight, thickness);
	}
}

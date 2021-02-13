package gelf.view.diagrams.builder;

import java.awt.Color;

import gelf.view.diagrams.components.DiagramAxis;
import gelf.view.diagrams.components.DiagramComponent;
import gelf.view.diagrams.components.DiagramValueDisplayComponent;
import gelf.view.diagrams.components.PositionIn2DDiagram;
import gelf.view.diagrams.data.DiagramData;

public interface IBarChartBuilder extends IDiagramBuilder {
	@Override
	public default DiagramValueDisplayComponent[] buildValueDisplayComponentsForOneDiagram(DiagramData data, DiagramAxis[] axes,
			DiagramComponent[] diagramSpecificComponent) {
		float[] values = data.extractValues().get(0);
		int dvdcCount = values.length;
		
		DiagramValueDisplayComponent[] dvdc = new DiagramValueDisplayComponent[dvdcCount];
		
		Color barColor = this.getSettingsProvider().getValueDisplayComponentColorAt(0);
		int thickness = this.getSettingsProvider().getBarBorderThickness();
		
		for (int i = 0; i < dvdc.length; i++) {
			PositionIn2DDiagram topLeft = this.getDiagramComponentFactory().makePositionInDiagram(axes[0], i, axes[1], values[i]);
			PositionIn2DDiagram bottomRight = this.getDiagramComponentFactory().makePositionInDiagram(axes[0], i + 1, axes[1], 0);
			
			dvdc[i] = this.getDiagramComponentFactory().createBarChartBar(barColor, values[i], topLeft, bottomRight, thickness);
		}
		
		return dvdc;
	}
	
	@Override
	public default DiagramComponent[] buildDiagramSpecificComponentForOneDiagram(DiagramData data) {
		return null;
	}
}

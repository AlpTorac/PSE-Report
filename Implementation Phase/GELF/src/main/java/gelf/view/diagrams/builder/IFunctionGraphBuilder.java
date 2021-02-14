package gelf.view.diagrams.builder;

import java.awt.Color;

import gelf.view.diagrams.components.DiagramAxis;
import gelf.view.diagrams.components.DiagramComponent;
import gelf.view.diagrams.components.DiagramValueDisplayComponent;
import gelf.view.diagrams.components.PositionIn2DDiagram;
import gelf.view.diagrams.data.DiagramData;

public interface IFunctionGraphBuilder extends IDiagramBuilder {
	@Override
	public default DiagramValueDisplayComponent[] buildValueDisplayComponentsForOneDiagram(DiagramData data, int orderInSameDiagram, DiagramAxis[] axes,
			DiagramComponent[] diagramSpecificComponent) {
		float[] indices = data.extractIndices().get(0);
		float[] values = data.extractValues().get(0);
		int dvdcCount = values.length;
		
		DiagramValueDisplayComponent[] dvdc = new DiagramValueDisplayComponent[dvdcCount];
		
		Color pointColor = this.getColorForDiagram(orderInSameDiagram);
		float size = this.getSettingsProvider().getFunctionGraphPointSize();
		
		for (int i = 0; i < dvdc.length; i++) {
			PositionIn2DDiagram position = this.getDiagramComponentFactory().makePositionInDiagram(axes[0], indices[i], axes[1], values[i]);
			
			dvdc[i] = this.getDiagramComponentFactory().createValueDisplayPoint(pointColor, values[i], size, position);
		}
		
		return dvdc;
	}
	
	@Override
	public default DiagramComponent[] buildDiagramSpecificComponentForOneDiagram(DiagramData data) {
		return null;
	}
}

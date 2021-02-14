package gelf.view.diagrams.builder;

import java.awt.Color;

import gelf.view.diagrams.SettingsProvider;
import gelf.view.diagrams.components.DiagramAxis;
import gelf.view.diagrams.components.DiagramComponent;
import gelf.view.diagrams.components.DiagramComponentFactory;
import gelf.view.diagrams.components.DiagramValueDisplayComponent;
import gelf.view.diagrams.data.DiagramData;

public interface IDiagramBuilder {
	public default SettingsProvider getSettingsProvider() {
		return SettingsProvider.getInstance();
	}
	
	public default DiagramComponentFactory getDiagramComponentFactory() {
		return DiagramComponentFactory.getDiagramComponentFactory();
	}
	
	public default DiagramValueDisplayComponent[] buildValueDisplayComponentsForOneDiagram(DiagramData data,
			DiagramAxis[] axes, DiagramComponent[] diagramSpecificComponent) {
		return this.buildValueDisplayComponentsForOneDiagram(data, 0, axes, diagramSpecificComponent);
	}
	
	public default DiagramValueDisplayComponent[] buildValueDisplayComponentsForOneDiagram(DiagramData data, int orderInSameDiagram,
			DiagramAxis[] axes, DiagramComponent[] diagramSpecificComponent) throws UnsupportedOperationException {
		throw new UnsupportedOperationException("This type of diagram cannot be overlaid.");
	}
	
	public DiagramComponent[] buildDiagramSpecificComponentForOneDiagram(DiagramData data);
	
	public default Color getColorForDiagram() {
		return this.getSettingsProvider().getValueDisplayComponentColorAt(0);
	}
	
	public default Color getColorForDiagram(int orderInSameDiagram) {
		return this.getSettingsProvider().getValueDisplayComponentColorAt(orderInSameDiagram);
	}
}

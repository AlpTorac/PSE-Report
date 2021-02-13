package gelf.view.diagrams.builder;

import java.awt.Container;

import gelf.view.diagrams.SettingsProvider;
import gelf.view.diagrams.components.DiagramAxis;
import gelf.view.diagrams.components.DiagramComponent;
import gelf.view.diagrams.components.DiagramComponentFactory;
import gelf.view.diagrams.components.DiagramValueDisplayComponent;
import gelf.view.diagrams.data.DiagramData;

public interface IDiagramBuilder {
	public DiagramData getDiagramData();
	
	public Container getContainer();
	
	public default SettingsProvider getSettingsProvider() {
		return SettingsProvider.getInstance();
	}
	
	public default DiagramComponentFactory getDiagramComponentFactory() {
		return DiagramComponentFactory.getDiagramComponentFactory();
	}
	
	public DiagramValueDisplayComponent[] buildValueDisplayComponentsForOneDiagram(DiagramData data, DiagramAxis[] axes,
			DiagramComponent[] diagramSpecificComponent);
	
	public DiagramComponent[] buildDiagramSpecificComponentForOneDiagram(DiagramData data);
}

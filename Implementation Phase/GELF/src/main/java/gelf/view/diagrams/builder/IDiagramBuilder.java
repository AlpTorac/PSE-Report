package gelf.view.diagrams.builder;

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
	
	public DiagramValueDisplayComponent[] buildValueDisplayComponentsForOneDiagram(DiagramData data, DiagramAxis[] axes,
			DiagramComponent[] diagramSpecificComponent);
	
	public DiagramComponent[] buildDiagramSpecificComponentForOneDiagram(DiagramData data);
}

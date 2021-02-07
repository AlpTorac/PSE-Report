package gelf.view.diagrams.builder;

import java.util.Collection;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.components.DiagramAxis;
import gelf.view.diagrams.components.DiagramComponent;
import gelf.view.diagrams.components.DiagramValueDisplayComponent;
import gelf.view.diagrams.data.DiagramData;

public abstract class DiagramBuilder {
	protected DiagramData data;
	
	public DiagramBuilder() {

	}
	
	private void setDiagramData(DiagramData data) {
		this.data = data;
	}
	
	protected abstract DiagramAxis buildAxes();
	
	protected abstract DiagramValueDisplayComponent[] buildValueDisplayComponents();
	
	protected abstract DiagramComponent[] buildDiagramSpecificComponent();
	
	public abstract IDiagram buildDiagram();
	
	public void receiveDiagramData(Collection<?> data, int numberOfIndices) {
		DiagramData diagramData = new DiagramData(data, numberOfIndices);
		this.setDiagramData(diagramData);
	}
}

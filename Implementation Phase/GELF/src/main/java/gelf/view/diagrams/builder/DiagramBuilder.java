package gelf.view.diagrams.builder;

import java.awt.Container;
import java.util.Collection;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.SettingsProvider;
import gelf.view.diagrams.components.DiagramAxis;
import gelf.view.diagrams.components.DiagramComponent;
import gelf.view.diagrams.components.DiagramComponentFactory;
import gelf.view.diagrams.components.DiagramValueDisplayComponent;
import gelf.view.diagrams.data.DiagramData;

public abstract class DiagramBuilder implements IDiagramBuilder, ContainerAccessPoint {
	public static final SettingsProvider settingsProvider = SettingsProvider.getInstance();
	public static final DiagramComponentFactory factory = DiagramComponentFactory.getDiagramComponentFactory();
	
	protected DiagramData data;
	protected Container container;
	
	public DiagramBuilder(Container container) {
		this.container = container;
	}
	
	private void setDiagramData(DiagramData data) {
		this.data = data;
	}
	
	protected abstract IDiagram makeDiagram(DiagramAxis[] axes, DiagramValueDisplayComponent[] valueDisplayComponents,
			DiagramComponent[] diagramSpecificComponent);
	
	public IDiagram buildDiagram() {
		DiagramAxis[] axes = this.buildAxes();
		DiagramComponent[] dc = this.buildDiagramSpecificComponent();
		DiagramValueDisplayComponent[] dvdc = this.buildValueDisplayComponents(axes, dc);
		return this.makeDiagram(axes, dvdc, dc);
	}
	
	protected DiagramComponent[] buildDiagramSpecificComponent() {
		return this.buildDiagramSpecificComponentForOneDiagram(this.getDiagramData());
	}
	
	protected DiagramValueDisplayComponent[] buildValueDisplayComponents(DiagramAxis[] axes,
			DiagramComponent[] diagramSpecificComponent) {
		return this.buildValueDisplayComponentsForOneDiagram(this.getDiagramData(),
				axes, diagramSpecificComponent);
	}
	
	public void receiveDiagramData(Collection<?> data, int numberOfIndices) {
		DiagramData diagramData = new DiagramData(data, numberOfIndices);
		this.setDiagramData(diagramData);
	}
	
	public void receiveDiagramData(Collection<?> data, Collection<?> descriptions, int numberOfIndices) {
		DiagramData diagramData = new DiagramData(data, descriptions, numberOfIndices);
		this.setDiagramData(diagramData);
	}
	
	protected DiagramData getDiagramData() {
		return this.data;
	}
	
	@Override
	public Container getContainer() {
		return this.container;
	}
}

package gelf.view.diagrams.indicator;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.components.DiagramComponent;

public class ViewHelperComponent {
	private DiagramComponent dc;
	private DiagramViewHelper dvh;
	private IDiagram diagram;
	
	protected ViewHelperComponent(DiagramComponent dc, DiagramViewHelper dvh) {
		this.dc = dc;
		this.dvh = dvh;
	}
	
	public void show() {
		this.dc.show();
	}
	
	public void hide() {
		this.dc.hide();
	}
	
	public void attachToDiagram(IDiagram diagram) {
		this.removeFromDiagram();
		this.diagram = diagram;
		this.diagram.addComponent(this.dc.getVisualElement(), this.dvh.getLayerNumber());
	}
	
	public void removeFromDiagram() {
		if (this.diagram != null) {
			this.diagram.removeComponent(this.dc.getVisualElement());
			this.diagram = null;
		}
	}
}

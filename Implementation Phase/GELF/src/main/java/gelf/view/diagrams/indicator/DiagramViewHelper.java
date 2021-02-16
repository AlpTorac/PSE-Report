package gelf.view.diagrams.indicator;

import java.util.ArrayList;
import java.util.Collection;

import gelf.view.diagrams.IDiagram;

public abstract class DiagramViewHelper {
	private int layer;
	private Collection<ViewHelperComponent> helperComponents;
	private IndicatorIdentifier id;
	protected IDiagram diagram;
	
	protected DiagramViewHelper(IDiagram diagram, int layer,
			IndicatorIdentifier id) {
		this.diagram = diagram;
		this.layer = layer;
		this.id = id;
		
		this.helperComponents = new ArrayList<ViewHelperComponent>();
		
		this.attachToDiagram();
	}
	
	public void attachToDiagram() {
		this.diagram.addDiagramViewHelper(this);
	}
	
	public int getLayerNumber() {
		return this.layer;
	}
	
	public void remove() {
		this.diagram.removeDiagramViewHelper(this.getID());
		this.diagram = null;
	}
	
	public void show() {
		this.diagram.showDiagramViewHelper(this.getID());
	}
	
	public void hide() {
		this.diagram.hideDiagramViewHelper(this.getID());
	}
	
	public void update() {
		for (ViewHelperComponent vhc : this.helperComponents) {
			vhc.hide();
			vhc.show();
		}
	}
	
	public boolean addViewHelperComponent(ViewHelperComponent vhc) {
		return this.helperComponents.add(vhc);
	}
	
	public boolean removeViewHelperComponent(ViewHelperComponent vhc) {
		return this.helperComponents.remove(vhc);
	}
	
	public boolean clearViewHelperComponents() {
		this.helperComponents.clear();
		return (this.helperComponents.size() == 0);
	}
	
	public IndicatorIdentifier getID() {
		return this.id;
	}
}

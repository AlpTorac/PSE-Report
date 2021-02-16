package gelf.view.diagrams.indicator;

import java.util.Collection;

import gelf.view.diagrams.IDiagram;

public abstract class DiagramViewHelper {
	private int layer;
	private Collection<ViewHelperComponent> helperComponents;
	private IndicatorIdentifier id;
	private IDiagram diagram;
	
	protected DiagramViewHelper(IDiagram diagram, int layer,
			IndicatorIdentifier id) {
		this.diagram = diagram;
		this.layer = layer;
		this.id = id;
	}
	
	public int getLayerNumber() {
		return this.layer;
	}
	
	public void remove() {
		
	}
	
	public void show() {
		
	}
	
	public void hide() {
		
	}
	
	public void update() {
		
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

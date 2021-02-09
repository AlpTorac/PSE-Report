package gelf.view.diagrams.type;

import java.awt.Container;
import java.util.Collection;
import java.util.EnumMap;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.components.DiagramAxis;
import gelf.view.diagrams.components.DiagramComponent;
import gelf.view.diagrams.components.DiagramValueDisplayComponent;
import gelf.view.diagrams.data.DiagramData;
import gelf.view.diagrams.indicator.DiagramViewHelper;
import gelf.view.diagrams.indicator.IndicatorIdentifier;

public abstract class Diagram implements IDiagram {
	private DiagramData data;
	private DiagramAxis[] axes;
	private DiagramValueDisplayComponent[] valueDisplayComponents;
	private DiagramComponent[] nonValueDisplayComponents;
	private EnumMap<IndicatorIdentifier, DiagramViewHelper> viewHelpers;
	
	private Container containingElement;
	
	public Diagram(DiagramData data, DiagramAxis[] axes,
			DiagramValueDisplayComponent[] valueDisplayComponents,
			DiagramComponent[] nonValueDisplayComponents,
			Container containingElement) {
	}
	
	public Collection<?> cloneData() {
		return null;
	}
	
	public void refresh() {
		
	}
	
	public void update(Collection<?> data) {
		
	}
	
	public boolean addDiagramViewHelper(DiagramViewHelper dvh) {
		return false;
	}
	
	public boolean removeDiagramViewHelper(IndicatorIdentifier id) {
		return false;
	}
	
	public boolean showDiagramViewHelper(IndicatorIdentifier id) {
		return false;
	}
	
	public boolean hideDiagramViewHelper(IndicatorIdentifier id) {
		return false;
	}
	
	public DiagramComponent[] getNonValueDisplayDiagramComponentPrototype() {
		return null;
	}
	
	public DiagramValueDisplayComponent[] getDiagramValueDisplayComponentPrototypes() {
		return null;
	}
}
package gelf.view.diagrams;

import java.util.Collection;

import gelf.view.diagrams.components.DiagramComponent;
import gelf.view.diagrams.components.DiagramValueDisplayComponent;
import gelf.view.diagrams.indicator.DiagramViewHelper;
import gelf.view.diagrams.indicator.IndicatorIdentifier;

public interface IDiagram {
	public Collection<?> cloneData();
	
	public void refresh();
		
	public boolean addDiagramViewHelper(DiagramViewHelper dvh);
	
	public boolean removeDiagramViewHelper(IndicatorIdentifier id);
	
	public boolean showDiagramViewHelper(IndicatorIdentifier id);
	
	public boolean hideDiagramViewHelper(IndicatorIdentifier id);
	
	public DiagramComponent[] getNonValueDisplayDiagramComponentPrototype();
	
	public DiagramValueDisplayComponent[] getDiagramValueDisplayComponentPrototypes();
}

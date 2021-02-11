package gelf.view.diagrams.components;

import gelf.view.diagrams.IDiagram;

public interface HasAttachablePart {
	public void attachToDiagram(IDiagram diagram);
	
	public void removeFromDiagram();
}

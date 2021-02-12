package gelf.view.diagrams.overlayer;

import gelf.view.diagrams.IDiagram;

public interface IDiagramOverlayStrategy {
	public void setDiagrams(IDiagram[] diagrams);
	
	public IDiagram overlay();
}

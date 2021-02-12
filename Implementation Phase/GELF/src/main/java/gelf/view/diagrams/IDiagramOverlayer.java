package gelf.view.diagrams;

import gelf.view.diagrams.overlayer.DiagramOverlayStrategy;

public interface IDiagramOverlayer {
	public IDiagram getDiagram(int index);
	public void setDiagram(int index, IDiagram diagram);
	public boolean addDiagram(IDiagram diagram);
	public boolean removeDiagram(int index);
	public IDiagram overlay(int[] indices);
	public IDiagram overlay(IDiagram[] diagrams);
	public IDiagram overlay();
	public void setOverlayStrategy(DiagramOverlayStrategy overlayStrategy);
}

package gelf.view.diagrams.overlayer;

import java.util.ArrayList;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.IDiagramOverlayer;

public class DiagramOverlayer implements IDiagramOverlayer {

	private ArrayList<IDiagram> diagrams;
	private DiagramOverlayStrategy overlayStrategy;
	
	public DiagramOverlayer() {
		this.diagrams = new ArrayList<IDiagram>();
	}
	
	public DiagramOverlayer(IDiagram[] diagrams) {
		this.diagrams = new ArrayList<IDiagram>();
		this.addDiagramsToCollection(diagrams);
	}
	
	private void addDiagramsToCollection(IDiagram[] diagrams) {
		for (int i = 0; i < diagrams.length; i++) {
			this.diagrams.add(diagrams[i]);
		}
	}

	public void setOverlayStrategy(DiagramOverlayStrategy overlayStrategy) {
		this.overlayStrategy = overlayStrategy;
	}

	@Override
	public IDiagram getDiagram(int index) {
		return this.diagrams.get(index);
	}

	@Override
	public void setDiagram(int index, IDiagram diagram) {
		this.diagrams.set(index, diagram);
	}

	@Override
	public boolean addDiagram(IDiagram diagram) {
		return this.diagrams.add(diagram);
	}

	@Override
	public boolean removeDiagram(int index) {
		return (this.diagrams.remove(index) != null);
	}

	@Override
	public IDiagram overlay(int[] indices) {
		IDiagram[] diagrams = new IDiagram[indices.length];
		
		int lastDiagramIndex = 0;
		
		for (int i : indices) {
			diagrams[lastDiagramIndex] = this.getDiagram(i);
			lastDiagramIndex++;
		}
		
		this.overlayStrategy.setDiagrams(diagrams);
		return this.overlayStrategy.overlay();
	}

	@Override
	public IDiagram overlay(IDiagram[] diagrams) {
		this.overlayStrategy.setDiagrams(diagrams);
		return this.overlayStrategy.overlay();
	}

	@Override
	public IDiagram overlay() {
		IDiagram[] diagramArray = new IDiagram[this.diagrams.size()];
		this.diagrams.toArray(diagramArray);
		this.overlayStrategy.setDiagrams(diagramArray);
		return this.overlayStrategy.overlay();
	}
}

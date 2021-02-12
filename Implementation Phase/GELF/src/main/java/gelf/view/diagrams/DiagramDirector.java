package gelf.view.diagrams;

import gelf.view.diagrams.builder.DiagramBuilder;

public class DiagramDirector {
	private DiagramBuilder builder;
	private static DiagramDirector instance;
	
	private DiagramDirector() {
		
	}
	
	public static DiagramDirector getDiagramDirector() {
		if (instance == null) {
			instance = new DiagramDirector();
		}
		return instance;
	}
	
	public void setBuilder(DiagramBuilder builder) {
		this.builder = builder;
	}
	
	public IDiagram build() {
		return this.builder.buildDiagram();
	}
}

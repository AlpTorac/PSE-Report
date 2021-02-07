package gelf.view.diagrams;

import java.util.Collection;

import gelf.view.diagrams.builder.DiagramBuilder;
import gelf.view.diagrams.data.DiagramData;

public class DiagramDirector {
	private DiagramBuilder builder;
	private static DiagramDirector instance;
	
	private DiagramDirector() {
		
	}
	
	public DiagramDirector getDiagramDirector() {
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

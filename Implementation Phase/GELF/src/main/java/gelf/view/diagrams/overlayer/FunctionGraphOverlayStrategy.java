package gelf.view.diagrams.overlayer;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.type.FunctionGraph;

public class FunctionGraphOverlayStrategy implements IDiagramOverlayStrategy {

	private FunctionGraph[] functionGraphs;
	
	public FunctionGraphOverlayStrategy() {
		
	}
	
	public FunctionGraphOverlayStrategy(FunctionGraph[] functionGraphs) {
		this.functionGraphs = functionGraphs;
	}
	
	@Override
	public FunctionGraph overlay() {
		return null;
	}

	@Override
	public void setDiagrams(IDiagram[] diagrams) {
		this.functionGraphs = (FunctionGraph[]) diagrams;
	}
}

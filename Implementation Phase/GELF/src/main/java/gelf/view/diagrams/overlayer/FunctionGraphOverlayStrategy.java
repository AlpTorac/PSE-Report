package gelf.view.diagrams.overlayer;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.components.DiagramAxis;
import gelf.view.diagrams.components.DiagramComponent;
import gelf.view.diagrams.components.DiagramValueDisplayComponent;
import gelf.view.diagrams.data.DiagramData;
import gelf.view.diagrams.type.FunctionGraph;

public class FunctionGraphOverlayStrategy extends DiagramOverlayStrategy {

	private FunctionGraph[] functionGraphs;
	
	public FunctionGraphOverlayStrategy() {
		
	}
	
	public FunctionGraphOverlayStrategy(FunctionGraph[] functionGraphs) {
		this.functionGraphs = functionGraphs;
	}

	@Override
	public void setDiagrams(IDiagram[] diagrams) {
		this.functionGraphs = (FunctionGraph[]) diagrams;
	}

	@Override
	protected IDiagram[] getAllDiagrams() {
		return this.functionGraphs;
	}

	@Override
	protected DiagramValueDisplayComponent[] makeValueDisplayComponents(DiagramData[] diagramData) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected DiagramComponent[] makeNonValueDisplayComponents(DiagramData[] diagramData) {
		return null;
	}

	@Override
	protected IDiagram buildDiagram(DiagramData data, DiagramAxis[] axes,
			DiagramValueDisplayComponent[] valueDisplayComponents, DiagramComponent[] nonValueDisplayComponents) {
		return new FunctionGraph(data, axes, valueDisplayComponents, nonValueDisplayComponents);
	}
}

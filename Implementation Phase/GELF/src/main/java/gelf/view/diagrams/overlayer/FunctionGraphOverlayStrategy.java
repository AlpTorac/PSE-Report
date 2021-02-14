package gelf.view.diagrams.overlayer;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.builder.IFunctionGraphBuilder;
import gelf.view.diagrams.components.DiagramAxis;
import gelf.view.diagrams.components.DiagramComponent;
import gelf.view.diagrams.components.DiagramValueDisplayComponent;
import gelf.view.diagrams.data.DiagramData;
import gelf.view.diagrams.type.FunctionGraph;

public class FunctionGraphOverlayStrategy extends DiagramOverlayStrategy implements IFunctionGraphBuilder {

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
	protected IDiagram buildDiagram(DiagramData data, DiagramAxis[] axes,
			DiagramValueDisplayComponent[] valueDisplayComponents, DiagramComponent[] nonValueDisplayComponents) {
		return new FunctionGraph(data, axes, valueDisplayComponents, nonValueDisplayComponents);
	}

	@Override
	protected DiagramValueDisplayComponent[] makeValueDisplayComponentsForOneDiagram(DiagramData diagramData, int orderInSameDiagram,
			DiagramAxis[] axes, DiagramComponent[] nonValueDisplayComponents) {
		return this.buildValueDisplayComponentsForOneDiagram(diagramData, orderInSameDiagram, axes, nonValueDisplayComponents);
	}
}

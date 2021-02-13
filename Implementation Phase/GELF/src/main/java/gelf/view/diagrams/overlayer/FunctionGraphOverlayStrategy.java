package gelf.view.diagrams.overlayer;

import java.awt.Color;
import java.util.ArrayList;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.components.BarChartBar;
import gelf.view.diagrams.components.DiagramAxis;
import gelf.view.diagrams.components.DiagramComponent;
import gelf.view.diagrams.components.DiagramValueDisplayComponent;
import gelf.view.diagrams.components.PositionIn2DDiagram;
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
	public DiagramValueDisplayComponent[] makeValueDisplayComponents(DiagramAxis[] axes, DiagramComponent[] nonValueDisplayComponents, DiagramData[] diagramData) {
		ArrayList<DiagramValueDisplayComponent> dvdcList = new ArrayList<DiagramValueDisplayComponent>();
		
		for (int index = 0; index < diagramData.length; index++) {
			DiagramData data = diagramData[index];

			float[] indices = data.extractIndices().get(0);
			float[] values = data.extractValues().get(0);
			int dvdcCount = values.length;
			
			DiagramValueDisplayComponent[] dvdc = new DiagramValueDisplayComponent[dvdcCount];
			
			Color pointColor = settingsProvider.getValueDisplayComponentColorAt(index);
			float size = settingsProvider.getFunctionGraphPointSize();
			
			for (int i = 0; i < dvdc.length; i++) {
				PositionIn2DDiagram position = factory.makePositionInDiagram(axes[0], indices[i], axes[1], values[i]);
				
				dvdc[i] = factory.createValueDisplayPoint(pointColor, values[i], size, position);
			}
		}
		
		return (DiagramValueDisplayComponent[]) dvdcList.toArray();
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

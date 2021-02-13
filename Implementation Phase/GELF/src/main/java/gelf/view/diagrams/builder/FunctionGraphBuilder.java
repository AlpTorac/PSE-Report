package gelf.view.diagrams.builder;

import java.awt.Color;
import java.awt.Container;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.components.DiagramAxis;
import gelf.view.diagrams.components.DiagramComponent;
import gelf.view.diagrams.components.DiagramValueDisplayComponent;
import gelf.view.diagrams.components.PositionIn2DDiagram;
import gelf.view.diagrams.components.PositionInFrame;
import gelf.view.diagrams.type.FunctionGraph;

public class FunctionGraphBuilder extends DiagramBuilder {

	public FunctionGraphBuilder(Container container) {
		super(container);
	}

	@Override
	protected DiagramValueDisplayComponent[] buildValueDisplayComponents(DiagramAxis[] axes,
			DiagramComponent[] diagramSpecificComponent) {
		float[] indices = this.data.extractIndices().get(0);
		float[] values = this.data.extractValues().get(0);
		int dvdcCount = values.length;
		
		DiagramValueDisplayComponent[] dvdc = new DiagramValueDisplayComponent[dvdcCount];
		
		Color pointColor = settingsProvider.getValueDisplayComponentColorAt(0);
		float size = settingsProvider.getFunctionGraphPointSize();
		
		for (int i = 0; i < dvdc.length; i++) {
			PositionIn2DDiagram position = factory.makePositionInDiagram(axes[0], indices[i], axes[1], values[i]);
			
			dvdc[i] = factory.createValueDisplayPoint(pointColor, values[i], size, position);
		}
		
		return dvdc;
	}

	@Override
	public IDiagram buildDiagram() {
		DiagramAxis[] axes = this.buildAxes();
		DiagramValueDisplayComponent[] dvdc = this.buildValueDisplayComponents(axes, null);
		return new FunctionGraph(this.data, axes, dvdc, null);
	}

	@Override
	protected float getXAxisMinValue() {
		return this.data.extractIndices().get(0)[0];
	}

	@Override
	protected float getYAxisMinValue() {
		return this.data.extractValues().get(0)[0];
	}

	@Override
	protected float getXAxisMaxValue() {
		return this.data.extractIndices().get(0)[this.data.extractIndices().get(0).length - 1];
	}

	@Override
	protected float getYAxisMaxValue() {
		return this.data.extractValues().get(0)[this.data.extractValues().get(0).length - 1];
	}
}

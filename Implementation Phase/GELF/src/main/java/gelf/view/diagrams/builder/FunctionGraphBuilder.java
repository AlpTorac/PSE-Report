package gelf.view.diagrams.builder;

import java.awt.Color;
import java.awt.Container;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.components.DiagramAxis;
import gelf.view.diagrams.components.DiagramComponent;
import gelf.view.diagrams.components.DiagramComponentFactory;
import gelf.view.diagrams.components.DiagramValueDisplayComponent;
import gelf.view.diagrams.components.PositionIn2DDiagram;
import gelf.view.diagrams.components.PositionInFrame;
import gelf.view.diagrams.type.FunctionGraph;

public class FunctionGraphBuilder extends DiagramBuilder {

	public FunctionGraphBuilder(Container container) {
		super(container);
	}

	@Override
	protected DiagramAxis[] buildAxes() {
		DiagramComponentFactory factory = DiagramComponentFactory.getDiagramComponentFactory();
		
		int containerWidth = this.container.getWidth();
		int containerHeight = this.container.getHeight();
		
		int spaceForAxisValues = 10;
		
		int xAxisYpos = containerHeight - spaceForAxisValues;
		int yAxisXpos = spaceForAxisValues;
		
		PositionInFrame axisOrigin = factory.makePositionInFrame(yAxisXpos, xAxisYpos);
		PositionInFrame endX = factory.makePositionInFrame(containerWidth, xAxisYpos);
		PositionInFrame endY = factory.makePositionInFrame(yAxisXpos, 0);
		
		float[] indices = this.data.extractIndices().get(0);
		int sizeOfIndices = indices.length;
		float[] values = this.data.extractValues().get(0);
		
		float minVal = values[0];
		float maxVal = values[sizeOfIndices - 1];
		
		float minIndex = indices[0];
		float maxIndex = indices[sizeOfIndices - 1];
		
		int stepsInXAxis = 10;
		int stepsInYAxis = 10;
		
		Color axisLine = Color.BLACK;
		int thickness = 1;
		
		DiagramAxis xAxis = DiagramComponentFactory.getDiagramComponentFactory()
				.createSolidAxis(axisOrigin, endX, minIndex, maxIndex, stepsInXAxis, axisLine, thickness, this.container);
		
		DiagramAxis yAxis = DiagramComponentFactory.getDiagramComponentFactory()
				.createSolidAxis(axisOrigin, endY, minVal, maxVal, stepsInYAxis, axisLine, thickness, this.container);
		
		return new DiagramAxis[] {xAxis, yAxis};
	}

	@Override
	protected DiagramValueDisplayComponent[] buildValueDisplayComponents(DiagramAxis[] axes,
			DiagramComponent[] diagramSpecificComponent) {
		DiagramComponentFactory factory = DiagramComponentFactory.getDiagramComponentFactory();
		
		float[] indices = this.data.extractIndices().get(0);
		float[] values = this.data.extractValues().get(0);
		int dvdcCount = values.length;
		
		DiagramValueDisplayComponent[] dvdc = new DiagramValueDisplayComponent[dvdcCount];
		
		Color barColor = Color.RED;
		float size = 5;
		
		for (int i = 0; i < dvdc.length; i++) {
			PositionIn2DDiagram position = factory.makePositionInDiagram(axes[0], indices[i], axes[1], values[i]);
			
			dvdc[i] = factory.createValueDisplayPoint(barColor, values[i], size, position, this.container);
		}
		
		return dvdc;
	}

	@Override
	protected DiagramComponent[] buildDiagramSpecificComponent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IDiagram buildDiagram() {
		DiagramAxis[] axes = this.buildAxes();
		DiagramValueDisplayComponent[] dvdc = this.buildValueDisplayComponents(axes, null);
		return new FunctionGraph(this.data, axes, dvdc, null, this.container);
	}

}
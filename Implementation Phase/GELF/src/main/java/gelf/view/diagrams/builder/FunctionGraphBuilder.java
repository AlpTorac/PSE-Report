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
		int containerWidth = this.container.getWidth();
		int containerHeight = this.container.getHeight();
		
		int xAxisYpos = Math.round(containerHeight * (1 - settingsProvider.getDiagramBottomMariginFactor()));
		int yAxisXpos = Math.round(containerWidth * settingsProvider.getDiagramLeftMariginFactor());
		
		PositionInFrame axisOrigin = factory.makePositionInFrame(yAxisXpos, xAxisYpos);
		PositionInFrame endX = factory.makePositionInFrame(containerWidth * (1 - settingsProvider.getDiagramRightMariginFactor()), xAxisYpos);
		PositionInFrame endY = factory.makePositionInFrame(yAxisXpos, containerHeight * settingsProvider.getDiagramTopMariginFactor());
		
		float[] indices = this.data.extractIndices().get(0);
		int sizeOfIndices = indices.length;
		float[] values = this.data.extractValues().get(0);
		
		float minVal = values[0];
		float maxVal = values[sizeOfIndices - 1];
		
		float minIndex = indices[0];
		float maxIndex = indices[sizeOfIndices - 1];
		
		int stepsInXAxis = settingsProvider.getStepsInXAxis();
		int stepsInYAxis = settingsProvider.getStepsInYAxis();
		
		Color axisLine = settingsProvider.getAxisColor();
		int thickness = settingsProvider.getAxisThickness();
		
		DiagramAxis xAxis = factory
				.createSolidAxis(axisOrigin, endX, minIndex, maxIndex, stepsInXAxis, axisLine, thickness);
		xAxis.showValuesUnderAxis();
		
		DiagramAxis yAxis = factory
				.createSolidAxis(axisOrigin, endY, minVal, maxVal, stepsInYAxis, axisLine, thickness);
		yAxis.showValuesAboveAxis();
		
		return new DiagramAxis[] {xAxis, yAxis};
	}

	@Override
	protected DiagramValueDisplayComponent[] buildValueDisplayComponents(DiagramAxis[] axes,
			DiagramComponent[] diagramSpecificComponent) {
		float[] indices = this.data.extractIndices().get(0);
		float[] values = this.data.extractValues().get(0);
		int dvdcCount = values.length;
		
		DiagramValueDisplayComponent[] dvdc = new DiagramValueDisplayComponent[dvdcCount];
		
		Color pointColor = settingsProvider.getFunctionGraphPointColor();
		float size = settingsProvider.getFunctionGraphPointSize();
		
		for (int i = 0; i < dvdc.length; i++) {
			PositionIn2DDiagram position = factory.makePositionInDiagram(axes[0], indices[i], axes[1], values[i]);
			
			dvdc[i] = factory.createValueDisplayPoint(pointColor, values[i], size, position);
		}
		
		return dvdc;
	}

	@Override
	protected DiagramComponent[] buildDiagramSpecificComponent() {
		return null;
	}

	@Override
	public IDiagram buildDiagram() {
		DiagramAxis[] axes = this.buildAxes();
		DiagramValueDisplayComponent[] dvdc = this.buildValueDisplayComponents(axes, null);
		return new FunctionGraph(this.data, axes, dvdc, null);
	}

}

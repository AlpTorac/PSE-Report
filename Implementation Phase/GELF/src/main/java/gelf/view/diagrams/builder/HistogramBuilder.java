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
import gelf.view.diagrams.type.Histogram;

public class HistogramBuilder extends DiagramBuilder {

	public HistogramBuilder(Container container) {
		super(container);
	}

	@Override
	protected DiagramAxis[] buildAxes() {
		DiagramComponentFactory factory = DiagramComponentFactory.getDiagramComponentFactory();
		
		int containerWidth = this.container.getWidth();
		int containerHeight = this.container.getHeight();
		
		int xSpaceForAxisValues = settingsProvider.getxSpaceForAxisValues();
		int ySpaceForAxisValues = settingsProvider.getySpaceForAxisValues();
		
		int xAxisYpos = containerHeight - ySpaceForAxisValues;
		int yAxisXpos = xSpaceForAxisValues;
		
		PositionInFrame axisOrigin = factory.makePositionInFrame(yAxisXpos, xAxisYpos);
		PositionInFrame endX = factory.makePositionInFrame(containerWidth, xAxisYpos);
		PositionInFrame endY = factory.makePositionInFrame(yAxisXpos, 0);
		
		float minVal = 0;
		float maxVal = this.data.getMaximumValue();
		
		float minIndex = 0;
		float maxIndex = this.data.getMaximumIndex();
		
		int stepsInXAxis = settingsProvider.getStepsInXAxis();
		int stepsInYAxis = settingsProvider.getStepsInYAxis();
		
		Color axisLine = settingsProvider.getAxisColor();
		int thickness = settingsProvider.getAxisThickness();
		float additionalMaxIndex = maxIndex + settingsProvider.getHistogramIndexEndIndexSpace();
		
		DiagramAxis xAxis = DiagramComponentFactory.getDiagramComponentFactory()
				.createSolidAxis(axisOrigin, endX, minIndex, additionalMaxIndex, stepsInXAxis, axisLine, thickness, this.container);
		
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
		
		Color barColor = settingsProvider.getBarColor();
		int thickness = settingsProvider.getBarBorderThickness();
		
		for (int i = 0; i < indices.length - 1; i++) {
			PositionIn2DDiagram topLeft = factory.makePositionInDiagram(axes[0], indices[i], axes[1], values[i]);
			PositionIn2DDiagram bottomRight = factory.makePositionInDiagram(axes[0], indices[i + 1], axes[1], 0);
			
			dvdc[i] = factory.createHistogramBar(barColor, values[i], topLeft, bottomRight, thickness, this.container);
		}
		
		float additionalMaxIndex = indices[indices.length - 1] + settingsProvider.getHistogramIndexEndIndexSpace();
		
		PositionIn2DDiagram topLeft = factory.makePositionInDiagram(axes[0], indices[indices.length - 1], axes[1], values[indices.length - 1]);
		PositionIn2DDiagram bottomRight = factory.makePositionInDiagram(axes[0], additionalMaxIndex, axes[1], 0);
		
		dvdc[indices.length - 1] = factory.createHistogramBar(barColor, values[indices.length - 1], topLeft, bottomRight, thickness, this.container);
		
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
		return new Histogram(this.data, axes, dvdc, null, this.container);
	}

}

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
import gelf.view.diagrams.data.DiagramData;
import gelf.view.diagrams.type.BarChart;

public class BarChartBuilder extends DiagramBuilder {

	public BarChartBuilder(Container container) {
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
		
		int numberOfBars = this.data.extractIndices().size();
		float[] values = this.data.extractValues().get(0);
		
		int stepsInYAxis = values.length;
		
		float maxVal = values[stepsInYAxis - 1];
		
		Color axisLine = Color.BLACK;
		int thickness = 1;
		
		DiagramAxis xAxis = DiagramComponentFactory.getDiagramComponentFactory()
				.createSolidAxis(axisOrigin, endX, 0, numberOfBars, numberOfBars + 1, axisLine, thickness, this.container);
		
		DiagramAxis yAxis = DiagramComponentFactory.getDiagramComponentFactory()
				.createSolidAxis(axisOrigin, endY, 0, maxVal, stepsInYAxis, axisLine, thickness, this.container);
		
		return new DiagramAxis[] {xAxis, yAxis};
	}

	@Override
	protected DiagramValueDisplayComponent[] buildValueDisplayComponents(DiagramAxis[] axes,
			DiagramComponent[] diagramSpecificComponent) {
		DiagramComponentFactory factory = DiagramComponentFactory.getDiagramComponentFactory();
		
		float[] values = this.data.extractValues().get(0);
		int dvdcCount = values.length;
		
		DiagramValueDisplayComponent[] dvdc = new DiagramValueDisplayComponent[dvdcCount];
		
		Color barColor = Color.RED;
		int thickness = 1;
		
		for (int i = 0; i < dvdc.length; i++) {
			PositionIn2DDiagram topLeft = factory.makePositionInDiagram(axes[0], i, axes[1], values[i]);
			PositionIn2DDiagram bottomRight = factory.makePositionInDiagram(axes[0], i + 1, axes[1], 0);
			
			dvdc[i] = factory.createBarChartBar(barColor, values[i], topLeft, bottomRight, thickness, this.container);
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
		return new BarChart(this.data, axes, dvdc, null, this.container);
	}

}
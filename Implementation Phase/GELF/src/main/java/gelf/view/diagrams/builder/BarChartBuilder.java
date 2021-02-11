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
		int containerWidth = this.container.getWidth();
		int containerHeight = this.container.getHeight();
		
		int xAxisYpos = Math.round(containerHeight * (1 - settingsProvider.getDiagramBottomMariginFactor()));
		int yAxisXpos = Math.round(containerWidth * settingsProvider.getDiagramLeftMariginFactor());
		
		PositionInFrame axisOrigin = factory.makePositionInFrame(yAxisXpos, xAxisYpos);
		PositionInFrame endX = factory.makePositionInFrame(containerWidth * (1 - settingsProvider.getDiagramRightMariginFactor()), xAxisYpos);
		PositionInFrame endY = factory.makePositionInFrame(yAxisXpos, containerHeight * settingsProvider.getDiagramTopMariginFactor());
		
		int numberOfBars = this.data.extractValues().get(0).length;
		float[] values = this.data.extractValues().get(0);
		
		int stepsInYAxis = values.length;
		
		float minVal = 0;
		float maxVal = this.data.getMaximumValue();
		
		Color axisLineColor = settingsProvider.getAxisColor();
		int thickness = settingsProvider.getAxisThickness();
		
		DiagramAxis xAxis = factory
				.createSolidAxis(axisOrigin, endX, 0, numberOfBars, numberOfBars, axisLineColor, thickness);
		xAxis.showValuesUnderAxis();
		
		DiagramAxis yAxis = factory
				.createSolidAxis(axisOrigin, endY, minVal, maxVal, stepsInYAxis, axisLineColor, thickness);
		yAxis.showValuesAboveAxis();
		
		return new DiagramAxis[] {xAxis, yAxis};
	}

	@Override
	protected DiagramValueDisplayComponent[] buildValueDisplayComponents(DiagramAxis[] axes,
			DiagramComponent[] diagramSpecificComponent) {
		float[] values = this.data.extractValues().get(0);
		int dvdcCount = values.length;
		
		DiagramValueDisplayComponent[] dvdc = new DiagramValueDisplayComponent[dvdcCount];
		
		Color barColor = settingsProvider.getBarColor();
		int thickness = settingsProvider.getBarBorderThickness();
		
		for (int i = 0; i < dvdc.length; i++) {
			PositionIn2DDiagram topLeft = factory.makePositionInDiagram(axes[0], i, axes[1], values[i]);
			PositionIn2DDiagram bottomRight = factory.makePositionInDiagram(axes[0], i + 1, axes[1], 0);
			
			dvdc[i] = factory.createBarChartBar(barColor, values[i], topLeft, bottomRight, thickness);
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
		return new BarChart(this.data, axes, dvdc, null);
	}

}

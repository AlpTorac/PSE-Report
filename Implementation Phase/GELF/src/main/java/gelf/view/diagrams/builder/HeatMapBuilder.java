package gelf.view.diagrams.builder;

import java.awt.Color;
import java.awt.Container;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.components.DiagramAxis;
import gelf.view.diagrams.components.DiagramColorScale;
import gelf.view.diagrams.components.DiagramComponent;
import gelf.view.diagrams.components.DiagramComponentFactory;
import gelf.view.diagrams.components.DiagramValueDisplayComponent;
import gelf.view.diagrams.components.PositionIn2DDiagram;
import gelf.view.diagrams.components.PositionInFrame;
import gelf.view.diagrams.data.DiagramData;

public class HeatMapBuilder extends DiagramBuilder {

	public HeatMapBuilder(Container container) {
		super(container);
	}

	@Override
	protected DiagramAxis[] buildAxes() {
		DiagramComponentFactory factory = DiagramComponentFactory.getDiagramComponentFactory();
		
		int containerWidth = this.container.getWidth();
		int containerHeight = this.container.getHeight();
		
		int xSpaceForAxisValues = settingsProvider.getxSpaceForAxisValues();
		int ySpaceForAxisValues = settingsProvider.getySpaceForAxisValues() + settingsProvider.getHeatMapColorScaleVerticalSpace();
		
		int xAxisYpos = containerHeight - ySpaceForAxisValues;
		int yAxisXpos = xSpaceForAxisValues;
		
		PositionInFrame axisOrigin = factory.makePositionInFrame(yAxisXpos, xAxisYpos);
		PositionInFrame endX = factory.makePositionInFrame(containerWidth, xAxisYpos);
		PositionInFrame endY = factory.makePositionInFrame(yAxisXpos, 0);
		
		float[][] indices = null;
		this.data.extractIndices().toArray(indices);
		
		int sizeOfIndices = indices[0].length;
		
		float minIndex1 = indices[0][0];
		float maxIndex1 = indices[0][sizeOfIndices];
		
		float minIndex2 = indices[1][0];
		float maxIndex2 = indices[1][sizeOfIndices];
		
		int stepsInXAxis = settingsProvider.getStepsInXAxis();
		int stepsInYAxis = settingsProvider.getStepsInYAxis();
		
		Color axisLine = settingsProvider.getAxisColor();
		int thickness = settingsProvider.getAxisThickness();
		
		DiagramAxis xAxis = factory
				.createSolidAxis(axisOrigin, endX, minIndex1, maxIndex1, stepsInXAxis, axisLine, thickness, this.container);
		
		DiagramAxis yAxis = factory
				.createSolidAxis(axisOrigin, endY, minIndex2, maxIndex2, stepsInYAxis, axisLine, thickness, this.container);
		
		return new DiagramAxis[] {xAxis, yAxis};
	}

	@Override
	protected DiagramValueDisplayComponent[] buildValueDisplayComponents(DiagramAxis[] axes,
			DiagramComponent[] diagramSpecificComponent) {
		float[][] indices = null;
		this.data.extractIndices().toArray(indices);
		
		float[][] values = null;
		this.data.extractValues().toArray(values);
		
		int indexCount = values.length;
		
		int dvdcCount = indexCount * indexCount;
		
		float minVal = Float.MAX_VALUE;
		float maxVal = Float.MIN_VALUE;
		
		for (int i = 0; i < values.length; i++) {
			float currentMinCandidate = values[i][0];
			if (minVal > currentMinCandidate) {
				minVal = currentMinCandidate;
			}
			float currentMaxCandidate = values[i][values[0].length - 1];
			if (maxVal < currentMaxCandidate) {
				maxVal = currentMaxCandidate;
			}
		}
		
		DiagramValueDisplayComponent[] dvdc = new DiagramValueDisplayComponent[dvdcCount];
		
		int thickness = settingsProvider.getHeatMapLabelBorderThickness();
		
		for (int i = 0; i < indexCount; i++) {
			for (int j = 0; j < indexCount; j++) {
				PositionIn2DDiagram topLeft = factory.makePositionInDiagram(axes[0], indices[0][i], axes[1], indices[1][j]);
				PositionIn2DDiagram bottomRight = factory.makePositionInDiagram(axes[0], indices[0][i + 1], axes[1], indices[1][j + 1]);
				
				
				dvdc[i * indexCount + j] = factory.createHeatMapLabel(topLeft, bottomRight,
						(DiagramColorScale) diagramSpecificComponent[0],
						maxVal, thickness, this.container);
			}
		}
		
		return dvdc;
	}

	@Override
	protected DiagramComponent[] buildDiagramSpecificComponent() {
		PositionInFrame topLeft = factory.makePositionInFrame(0, this.container.getHeight() - settingsProvider.getHeatMapColorScaleVerticalSpace());
		PositionInFrame bottomRight = factory.makePositionInFrame(this.container.getWidth(), this.container.getHeight());
		Color borderColor = settingsProvider.getHeatMapColorScaleBorderColor();
		int borderThickness = settingsProvider.getHeatMapColorScaleBorderThickness();
		Color[] colors = settingsProvider.getHeatMapColorScaleColors();
		
		float[][] values = null;
		this.data.extractValues().toArray(values);
		
		float minVal = Float.MAX_VALUE;
		float maxVal = Float.MIN_VALUE;
		
		for (int i = 0; i < values.length; i++) {
			float currentMinCandidate = values[i][0];
			if (minVal > currentMinCandidate) {
				minVal = currentMinCandidate;
			}
			float currentMaxCandidate = values[i][values[0].length - 1];
			if (maxVal < currentMaxCandidate) {
				maxVal = currentMaxCandidate;
			}
		}
		
		DiagramColorScale colorScale = factory.createBiColorScale(topLeft, bottomRight, borderColor, minVal, maxVal, colors[0], colors[1], borderThickness, this.container);
		return new DiagramColorScale[] {colorScale};
	}

	@Override
	public IDiagram buildDiagram() {
		
		return null;
	}

}

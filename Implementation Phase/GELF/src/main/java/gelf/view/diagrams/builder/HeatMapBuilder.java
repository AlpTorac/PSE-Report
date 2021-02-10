package gelf.view.diagrams.builder;

import java.awt.Color;
import java.awt.Container;
import java.util.ArrayList;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.components.DiagramAxis;
import gelf.view.diagrams.components.DiagramColorScale;
import gelf.view.diagrams.components.DiagramComponent;
import gelf.view.diagrams.components.DiagramComponentFactory;
import gelf.view.diagrams.components.DiagramValueDisplayComponent;
import gelf.view.diagrams.components.HeatMapLabel;
import gelf.view.diagrams.components.PositionIn2DDiagram;
import gelf.view.diagrams.components.PositionInFrame;
import gelf.view.diagrams.type.HeatMap;

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
		
		float minIndex1 = 0;
		float maxIndex1 = this.data.getMaximumIndexAt(0);
		
		float minIndex2 = 0;
		float maxIndex2 = this.data.getMaximumIndexAt(1);
		
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
		
		ArrayList<float[]> indexList = this.data.extractIndices();
		ArrayList<float[]> valueList = this.data.extractValues();
		
		float[][] indices = new float[indexList.size()][indexList.get(0).length];
		indexList.toArray(indices);
		
		float[][] values = new float[valueList.size()][valueList.get(0).length];
		valueList.toArray(values);
		
		int indexCount = values.length;
		
		int dvdcCount = indexCount * indexCount;
		
		DiagramValueDisplayComponent[] dvdc = new DiagramValueDisplayComponent[dvdcCount];
		
		int thickness = settingsProvider.getHeatMapLabelBorderThickness();
		
		dvdc[0] = makeOriginLabel(axes, indices, values, diagramSpecificComponent, thickness);
		
		for (int i = 1; i < indexCount; i++) {
			dvdc[i * indexCount] = makeNullIndex1Label(axes, indices, values, i, diagramSpecificComponent, thickness);
		}
		
		for (int j = 1; j < indexCount; j++) {
			dvdc[j] = makeNullIndex2Label(axes, indices, values, j, diagramSpecificComponent, thickness);
		}
		
		for (int i = 1; i < indexCount; i++) {
			for (int j = 1; j < indexCount; j++) {
				dvdc[i * indexCount + j] = makeLabel(axes, indices, values, i, j, diagramSpecificComponent, thickness);
			}
		}
		
		return dvdc;
	}
	
	/*
	 * Make the heat map label, whose bottom left corner is the origin
	 */
	private HeatMapLabel makeOriginLabel(DiagramAxis[] axes, float[][] indices, float[][] values, DiagramComponent[] diagramSpecificComponent,
			int thickness) {
		PositionIn2DDiagram topLeft = factory.makePositionInDiagram(axes[0], 0, axes[1], indices[1][0]);
		PositionIn2DDiagram bottomRight = factory.makePositionInDiagram(axes[0], indices[0][0], axes[1], 0);
		
		return factory.createHeatMapLabel(topLeft, bottomRight,
				(DiagramColorScale) diagramSpecificComponent[0],
				values[0][0], thickness, this.container);
	}
	
	/*
	 * Make a heat map label, whose left border is the axis line of the vertical index (but the bottom border is not the axis line of the horizontal index).
	 */
	private HeatMapLabel makeNullIndex1Label(DiagramAxis[] axes, float[][] indices, float[][] values, int i, DiagramComponent[] diagramSpecificComponent,
			int thickness) {
		PositionIn2DDiagram topLeft = factory.makePositionInDiagram(axes[0], indices[0][0], axes[1], indices[1][0]);
		PositionIn2DDiagram bottomRight = factory.makePositionInDiagram(axes[0], indices[0][i], axes[1], 0);
		
		return factory.createHeatMapLabel(topLeft, bottomRight,
				(DiagramColorScale) diagramSpecificComponent[0],
				values[i][0], thickness, this.container);
	}
	
	/*
	 * Make a heat map label, whose bottom border is the axis line of the horizontal index (but the left border is not the axis line of the vertical index).
	 */
	private HeatMapLabel makeNullIndex2Label(DiagramAxis[] axes, float[][] indices, float[][] values, int j, DiagramComponent[] diagramSpecificComponent,
			int thickness) {
		PositionIn2DDiagram topLeft = factory.makePositionInDiagram(axes[0], 0, axes[1], indices[1][j]);
		PositionIn2DDiagram bottomRight = factory.makePositionInDiagram(axes[0], indices[0][0], axes[1], indices[1][j - 1]);
		
		return factory.createHeatMapLabel(topLeft, bottomRight,
				(DiagramColorScale) diagramSpecificComponent[0],
				values[0][j], thickness, this.container);
	}
	
	private HeatMapLabel makeLabel(DiagramAxis[] axes, float[][] indices, float[][] values, int i, int j, DiagramComponent[] diagramSpecificComponent,
			int thickness) {
		PositionIn2DDiagram topLeft = factory.makePositionInDiagram(axes[0], indices[0][i - 1], axes[1], indices[1][j]);
		PositionIn2DDiagram bottomRight = factory.makePositionInDiagram(axes[0], indices[0][i], axes[1], indices[1][j - 1]);
		
		return factory.createHeatMapLabel(topLeft, bottomRight,
				(DiagramColorScale) diagramSpecificComponent[0],
				values[i][j], thickness, this.container);
	}

	@Override
	protected DiagramComponent[] buildDiagramSpecificComponent() {
		PositionInFrame topLeft = factory.makePositionInFrame(0, this.container.getHeight() + settingsProvider.getHeatMapSpaceBetweenDiagramAndColorScale() - settingsProvider.getHeatMapColorScaleVerticalSpace());
		PositionInFrame bottomRight = factory.makePositionInFrame(this.container.getWidth(), this.container.getHeight());
		Color borderColor = settingsProvider.getHeatMapColorScaleBorderColor();
		int borderThickness = settingsProvider.getHeatMapColorScaleBorderThickness();
		Color[] colors = settingsProvider.getHeatMapColorScaleColors();
		
		float minVal = this.data.getMinimumValue();
		float maxVal = this.data.getMaximumValue();
		
		DiagramColorScale colorScale = factory.createBiColorScale(topLeft, bottomRight, borderColor, minVal, maxVal, colors[0], colors[1], borderThickness, this.container);
		return new DiagramColorScale[] {colorScale};
	}

	@Override
	public IDiagram buildDiagram() {
		DiagramAxis[] axes = this.buildAxes();
		DiagramComponent[] dc = this.buildDiagramSpecificComponent();
		DiagramValueDisplayComponent[] dvdc = this.buildValueDisplayComponents(axes, dc);
		return new HeatMap(this.data, axes, dvdc, dc, this.container);
	}

}

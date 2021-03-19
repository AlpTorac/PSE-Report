package gelf.view.diagrams.builder;

import java.awt.Color;
import java.util.ArrayList;

import gelf.view.diagrams.components.DiagramAxis;
import gelf.view.diagrams.components.DiagramColorScale;
import gelf.view.diagrams.components.DiagramComponent;
import gelf.view.diagrams.components.DiagramValueDisplayComponent;
import gelf.view.diagrams.components.HeatMapLabel;
import gelf.view.diagrams.components.PositionIn2DDiagram;
import gelf.view.diagrams.components.PositionInFrame;
import gelf.view.diagrams.data.DiagramData;

public interface IHeatMapBuilder extends IDiagramBuilder, ContainerAccessPoint {
	@Override
	public default DiagramValueDisplayComponent[] buildValueDisplayComponentsForOneDiagram(DiagramData data, DiagramAxis[] axes,
			DiagramComponent[] diagramSpecificComponent) {
		
		ArrayList<float[]> indexList = data.extractIndices();
		ArrayList<float[]> valueList = data.extractValues();
		
		float[][] indices = new float[indexList.size()][];
		indexList.toArray(indices);
		
		float[][] values = new float[valueList.size()][];
		valueList.toArray(values);
		
		int index1Count = indexList.get(0).length;
		int index2Count = indexList.get(1).length;
		
		int dvdcCount = index1Count * index2Count;
		
		DiagramValueDisplayComponent[] dvdc = new DiagramValueDisplayComponent[dvdcCount];
		
		int thickness = this.getSettingsProvider().getHeatMapLabelBorderThickness();
		
		dvdc[0] = makeOriginLabel(axes, indices, values, diagramSpecificComponent, thickness);
		
		for (int i = 1; i < index1Count; i++) {
			dvdc[i * index2Count] = makeNullIndex1Label(axes, indices, values, i, diagramSpecificComponent, thickness);
		}
		
		for (int j = 1; j < index2Count; j++) {
			dvdc[j] = makeNullIndex2Label(axes, indices, values, j, diagramSpecificComponent, thickness);
		}
		
		for (int i = 1; i < index1Count; i++) {
			for (int j = 1; j < index2Count; j++) {
				dvdc[i * index2Count + j] = makeLabel(axes, indices, values, i, j, diagramSpecificComponent, thickness);
			}
		}
		
		return dvdc;
	}
	
	/*
	 * Make the heat map label, whose bottom left corner is the origin
	 */
	default HeatMapLabel makeOriginLabel(DiagramAxis[] axes, float[][] indices, float[][] values, DiagramComponent[] diagramSpecificComponent,
			int thickness) {
		PositionIn2DDiagram topLeft = this.getDiagramComponentFactory().makePositionInDiagram(axes[0], 0, axes[1], indices[1][0]);
		PositionIn2DDiagram bottomRight = this.getDiagramComponentFactory().makePositionInDiagram(axes[0], indices[0][0], axes[1], 0);
		
		return this.getDiagramComponentFactory().createHeatMapLabel(topLeft, bottomRight,
				(DiagramColorScale) diagramSpecificComponent[0],
				values[0][0], thickness);
	}
	
	/*
	 * Make a heat map label, whose left border is the axis line of the vertical index (but the bottom border is not the axis line of the horizontal index).
	 */
	default HeatMapLabel makeNullIndex1Label(DiagramAxis[] axes, float[][] indices, float[][] values, int i, DiagramComponent[] diagramSpecificComponent,
			int thickness) {
		PositionIn2DDiagram topLeft = this.getDiagramComponentFactory().makePositionInDiagram(axes[0], indices[0][i - 1], axes[1], indices[1][0]);
		PositionIn2DDiagram bottomRight = this.getDiagramComponentFactory().makePositionInDiagram(axes[0], indices[0][i], axes[1], 0);
		
		return this.getDiagramComponentFactory().createHeatMapLabel(topLeft, bottomRight,
				(DiagramColorScale) diagramSpecificComponent[0],
				values[i][0], thickness);
	}
	
	/*
	 * Make a heat map label, whose bottom border is the axis line of the horizontal index (but the left border is not the axis line of the vertical index).
	 */
	default HeatMapLabel makeNullIndex2Label(DiagramAxis[] axes, float[][] indices, float[][] values, int j, DiagramComponent[] diagramSpecificComponent,
			int thickness) {
		PositionIn2DDiagram topLeft = this.getDiagramComponentFactory().makePositionInDiagram(axes[0], 0, axes[1], indices[1][j]);
		PositionIn2DDiagram bottomRight = this.getDiagramComponentFactory().makePositionInDiagram(axes[0], indices[0][0], axes[1], indices[1][j - 1]);
		
		return this.getDiagramComponentFactory().createHeatMapLabel(topLeft, bottomRight,
				(DiagramColorScale) diagramSpecificComponent[0],
				values[0][j], thickness);
	}
	
	default HeatMapLabel makeLabel(DiagramAxis[] axes, float[][] indices, float[][] values, int i, int j, DiagramComponent[] diagramSpecificComponent,
			int thickness) {
		PositionIn2DDiagram topLeft = this.getDiagramComponentFactory().makePositionInDiagram(axes[0], indices[0][i - 1], axes[1], indices[1][j]);
		PositionIn2DDiagram bottomRight = this.getDiagramComponentFactory().makePositionInDiagram(axes[0], indices[0][i], axes[1], indices[1][j - 1]);
		
		return this.getDiagramComponentFactory().createHeatMapLabel(topLeft, bottomRight,
				(DiagramColorScale) diagramSpecificComponent[0],
				values[i][j], thickness);
	}

	@Override
	public default DiagramComponent[] buildDiagramSpecificComponentForOneDiagram(DiagramData data) {
		int containerHeight = this.getContainer().getHeight();
		
		PositionInFrame topLeft = this.getDiagramComponentFactory().makePositionInFrame(this.getContainer().getWidth() * this.getSettingsProvider().getHeatMapColorScaleLeftMariginFactor(),
				this.getContainer().getHeight() + containerHeight / 20 - containerHeight / 10);
		PositionInFrame bottomRight = this.getDiagramComponentFactory().makePositionInFrame(this.getContainer().getWidth() * (1 - this.getSettingsProvider().getHeatMapColorScaleRightMariginFactor()),
				this.getContainer().getHeight());
		Color borderColor = this.getSettingsProvider().getHeatMapColorScaleBorderColor();
		int borderThickness = this.getSettingsProvider().getHeatMapColorScaleBorderThickness();
		Color[] colors = this.getSettingsProvider().getHeatMapColorScaleColors();
		
		float minVal = data.getMinimumValue();
		float maxVal = data.getMaximumValue();
		
		DiagramColorScale colorScale = this.getDiagramComponentFactory().createBiColorScale(topLeft, bottomRight, borderColor, minVal, maxVal, colors[0], colors[1], borderThickness);
		return new DiagramColorScale[] {colorScale};
	}
}

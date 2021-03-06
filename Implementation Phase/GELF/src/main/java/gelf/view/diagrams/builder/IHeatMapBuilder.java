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

/**
 * An interface implemented by the classes responsible for building {@link gelf.view.diagrams.type.HeatMap HeatMap}.
 * @author Alp Torac Genc
 *
 */
public interface IHeatMapBuilder extends IDiagramBuilder {
	@Override
	public default DiagramValueDisplayComponent[] buildValueDisplayComponentsForOneDiagram(DiagramData data, DiagramAxis[] axes,
			DiagramComponent[] diagramSpecificComponent) {
		
		ArrayList<float[]> indexList = data.extractIndices();
		ArrayList<float[]> valueList = data.extractValues();
		
		float[][] values = new float[valueList.size()][];
		valueList.toArray(values);
		
		int index1Count = indexList.get(0).length;
		int index2Count = indexList.get(1).length;
		
		int dvdcCount = index1Count * index2Count;
		
		DiagramValueDisplayComponent[] dvdc = new DiagramValueDisplayComponent[dvdcCount];
		
		int thickness = this.getSettingsProvider().getHeatMapLabelBorderThickness();

		float currentIndex1Start = 0;
		float index1StepLength = (axes[0].getMax() - axes[0].getMin()) / ((float) index1Count);
		float index2StepLength = (axes[1].getMax() - axes[1].getMin()) / ((float) index2Count);
		float currentIndex1End = index1StepLength;

		for (int i = 0; i < index1Count; i++) {
			float currentIndex2Start = 0;
			float currentIndex2End = index2StepLength;
			for (int j = 0; j < index2Count; j++) {
				dvdc[i * index2Count + j] = makeLabel(axes, values[i][j], currentIndex1Start, currentIndex1End,
						currentIndex2Start, currentIndex2End, diagramSpecificComponent, thickness);
				currentIndex2Start = currentIndex2End;
				currentIndex2End += index2StepLength;
			}
			currentIndex1Start = currentIndex1End;
			currentIndex1End += index1StepLength;
		}
		axes[0].hide();
		axes[1].hide();
		return dvdc;
	}
	
	/**
	 * @param axes the axes, which stand for index1 and index2
	 * @param value the value associated with the component
	 * @param index1Start the minimum index1 covered by the component
	 * @param index1End the maximum index1 covered by the component
	 * @param index2Start the minimum index2 covered by the component
	 * @param index2End the maximum index2 covered by the component
	 * @param diagramSpecificComponent the component created in {@link #buildDiagramSpecificComponentForOneDiagram(DiagramData) buildDiagramSpecificComponentForOneDiagram}
	 * @param thickness the thickness of the border of the component
	 * @return The heat map label specified in the parameters.
	 */
	default HeatMapLabel makeLabel(DiagramAxis[] axes, float value, float index1Start,
			float index1End, float index2Start, float index2End, DiagramComponent[] diagramSpecificComponent,
			int thickness) {
		PositionIn2DDiagram topLeft = this.getDiagramComponentFactory().makePositionInDiagram(axes[0], index1Start, axes[1], index2End);
		PositionIn2DDiagram bottomRight = this.getDiagramComponentFactory().makePositionInDiagram(axes[0], index1End, axes[1], index2Start);
		
		return this.getDiagramComponentFactory().createHeatMapLabel(topLeft, bottomRight,
				(DiagramColorScale) diagramSpecificComponent[0],
				value, thickness);
	}
	
	@Override
	public default DiagramComponent[] buildDiagramSpecificComponentForOneDiagram(DiagramData data) {
		PositionInFrame topLeft = this.getDiagramComponentFactory().makePositionInFrame(this.getContainer().getWidth() * this.getSettingsProvider().getHeatMapColorScaleLeftMariginFactor(),
				this.getContainer().getHeight() * (1 - this.getSettingsProvider().getHeatMapColorScaleHeightInContainer()));
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

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
		
		int spaceForAxisValues = 10;
		
		int xAxisYpos = containerHeight - spaceForAxisValues;
		int yAxisXpos = spaceForAxisValues;
		
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
		
		int stepsInXAxis = 10;
		int stepsInYAxis = 10;
		
		Color axisLine = Color.BLACK;
		int thickness = 1;
		
		DiagramAxis xAxis = DiagramComponentFactory.getDiagramComponentFactory()
				.createSolidAxis(axisOrigin, endX, minIndex1, maxIndex1, stepsInXAxis, axisLine, thickness, this.container);
		
		DiagramAxis yAxis = DiagramComponentFactory.getDiagramComponentFactory()
				.createSolidAxis(axisOrigin, endY, minIndex2, maxIndex2, stepsInYAxis, axisLine, thickness, this.container);
		
		return new DiagramAxis[] {xAxis, yAxis};
	}

	@Override
	protected DiagramValueDisplayComponent[] buildValueDisplayComponents(DiagramAxis[] axes,
			DiagramComponent[] diagramSpecificComponent) {
		DiagramComponentFactory factory = DiagramComponentFactory.getDiagramComponentFactory();
		
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
		
		int thickness = 1;
		
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IDiagram buildDiagram() {
		// TODO Auto-generated method stub
		return null;
	}

}
package gelf.view.diagrams.builder;

import java.awt.Color;
import java.awt.Container;

import gelf.view.diagrams.SettingsProvider;
import gelf.view.diagrams.components.DiagramAxis;
import gelf.view.diagrams.components.DiagramComponent;
import gelf.view.diagrams.components.DiagramComponentFactory;
import gelf.view.diagrams.components.DiagramValueDisplayComponent;
import gelf.view.diagrams.components.PositionInFrame;
import gelf.view.diagrams.data.DiagramData;

public interface IDiagramBuilder {
	public default SettingsProvider getSettingsProvider() {
		return SettingsProvider.getInstance();
	}
	
	public default DiagramComponentFactory getDiagramComponentFactory() {
		return DiagramComponentFactory.getDiagramComponentFactory();
	}
	
	public default DiagramValueDisplayComponent[] buildValueDisplayComponentsForOneDiagram(DiagramData data,
			DiagramAxis[] axes, DiagramComponent[] diagramSpecificComponent) {
		return this.buildValueDisplayComponentsForOneDiagram(data, 0, axes, diagramSpecificComponent);
	}
	
	public default DiagramValueDisplayComponent[] buildValueDisplayComponentsForOneDiagram(DiagramData data, int orderInSameDiagram,
			DiagramAxis[] axes, DiagramComponent[] diagramSpecificComponent) throws UnsupportedOperationException {
		throw new UnsupportedOperationException("This type of diagram cannot be overlaid.");
	}
	
	public DiagramComponent[] buildDiagramSpecificComponentForOneDiagram(DiagramData data);
	
	public default Color getColorForDiagram() {
		return this.getSettingsProvider().getValueDisplayComponentColorAt(0);
	}
	
	public default Color getColorForDiagram(int orderInSameDiagram) {
		return this.getSettingsProvider().getValueDisplayComponentColorAt(orderInSameDiagram);
	}
	
	default PositionInFrame getXAxisEndPosition(PositionInFrame startX, int topRightXPos) {
		return this.getDiagramComponentFactory().makePositionInFrame(topRightXPos, startX.getYPos());
	}
	
	default PositionInFrame getYAxisEndPosition(PositionInFrame startY, int topRightYPos) {
		return this.getDiagramComponentFactory().makePositionInFrame(startY.getXPos(), topRightYPos);
	}
	
	default DiagramAxis makeXAxis(PositionInFrame axisOrigin, PositionInFrame endX, float minVal, float maxVal,
			int steps, Color axisLineColor, int thickness) {
		DiagramAxis xAxis = this.getDiagramComponentFactory().createSolidAxis(axisOrigin, endX, minVal, maxVal, steps, axisLineColor, thickness, this.getXAxisDescriptions());
		return xAxis;
	}
	
	default DiagramAxis makeYAxis(PositionInFrame axisOrigin, PositionInFrame endY, float minVal, float maxVal,
			int steps, Color axisLineColor, int thickness) {
		DiagramAxis yAxis = this.getDiagramComponentFactory().createSolidAxis(axisOrigin, endY, minVal, maxVal, steps, axisLineColor, thickness, this.getYAxisDescriptions());
		return yAxis;
	}

	default int getXAxisSteps() {
		return this.getSettingsProvider().getStepsInXAxis();
	}
	default int getYAxisSteps() {
		return this.getSettingsProvider().getStepsInYAxis();
	}
	
	public default DiagramAxis[] buildAxes() {
		int containerWidth = this.getContainer().getWidth();
		int containerHeight = this.getContainer().getHeight();
		
		int stepsInXAxis = this.getXAxisSteps();
		int stepsInYAxis = this.getYAxisSteps();
		
		float xAxisMinVal = this.getXAxisMinValue();
		float xAxisMaxVal = this.getXAxisMaxValue();
		float yAxisMinVal = this.getYAxisMinValue();
		float yAxisMaxVal = this.getYAxisMaxValue();
		
		int bottomLeftYPos = Math.round(containerHeight - this.getSettingsProvider().getDiagramBottomMarigin(containerHeight));
		int bottomLeftXPos = Math.round(this.getSettingsProvider().getDiagramLeftMarigin(containerWidth));
		int topRightYPos = Math.round(this.getSettingsProvider().getDiagramTopMarigin(containerHeight));
		int topRightXPos = Math.round(containerWidth - this.getSettingsProvider().getDiagramRightMarigin(containerWidth));
		
//		PositionInFrame axisOrigin = this.getAxisOriginPosition(xAxisYpos, yAxisXpos);
		PositionInFrame startY = this.getYAxisStartPosition(bottomLeftXPos, bottomLeftYPos);
		PositionInFrame endY = this.getYAxisEndPosition(startY, topRightYPos);
		PositionInFrame startX = this.getXAxisStartPosition(bottomLeftXPos, Math.abs(startY.getYPos() - endY.getYPos()),
				yAxisMinVal, yAxisMaxVal, topRightYPos, containerHeight);
		PositionInFrame endX = this.getXAxisEndPosition(startX, topRightXPos);
		
		
		Color axisLineColor = this.getSettingsProvider().getAxisColor();
		int thickness = this.getSettingsProvider().getAxisThickness();
		
		DiagramAxis xAxis = this.makeXAxis(startX, endX, xAxisMinVal, xAxisMaxVal, stepsInXAxis, axisLineColor, thickness);
		this.xAxisSpecificVisualEffect(xAxis, yAxisMinVal, yAxisMaxVal);
		DiagramAxis yAxis = this.makeYAxis(startY, endY, yAxisMinVal, yAxisMaxVal, stepsInYAxis, axisLineColor, thickness);
		this.yAxisSpecificVisualEffect(yAxis, xAxisMinVal, xAxisMaxVal);
		return new DiagramAxis[] {xAxis, yAxis};
	}
	
	default double getXAxisYPos(double yAxisLength, float yAxisMinVal, float yAxisMaxVal, int topRightYPos, int containerHeight) {
		double yCoordinateStartX = yAxisLength;
		
		if (Math.signum(yAxisMaxVal) != Math.signum(yAxisMinVal)) {
			yCoordinateStartX *= Math.abs(yAxisMaxVal) / (Math.abs(yAxisMaxVal) + Math.abs(yAxisMinVal));
		}
		
		yCoordinateStartX += topRightYPos;

		if (yCoordinateStartX <= this.getSettingsProvider().getDiagramTopMarigin(containerHeight)) {
			return this.getSettingsProvider().getDiagramTopMarigin(containerHeight);
		}
		return yCoordinateStartX;
	}
	
	default double getYAxisXPos(int bottomLeftXPos) {
		return bottomLeftXPos;
	}
	
	default PositionInFrame getYAxisStartPosition(int bottomLeftXPos, int bottomLeftYPos) {
		return this.getDiagramComponentFactory().makePositionInFrame(this.getYAxisXPos(bottomLeftXPos), bottomLeftYPos);
	}

	default PositionInFrame getXAxisStartPosition(int bottomLeftXPos, double yAxisLength, float yAxisMinVal, float yAxisMaxVal, int topRightYPos, int containerHeight) {
		return this.getDiagramComponentFactory().makePositionInFrame(bottomLeftXPos, this.getXAxisYPos(yAxisLength, yAxisMinVal, yAxisMaxVal, topRightYPos, containerHeight));
	}

	default void xAxisSpecificVisualEffect(DiagramAxis xAxis, float yMin, float yMax) {
		if (Math.abs(yMax) / Math.abs(yMax - yMin) >= 0.5d) {
			xAxis.setShowValuesUnderAxis(true);
		} else {
			xAxis.setShowValuesUnderAxis(false);
		}
	}
	default void yAxisSpecificVisualEffect(DiagramAxis yAxis, float xMin, float xMax) {
		if (Math.abs(xMax) / Math.abs(xMax - xMin) >= 0.5d) {
			yAxis.setShowValuesUnderAxis(false);
		} else {
			yAxis.setShowValuesUnderAxis(true);
		}
	}
	
	default String[] getXAxisDescriptions() {
		return null;
	}
	default String[] getYAxisDescriptions() {
		return null;
	}
	
	default float getXAxisMinValue() {
		return 0;
	}
	float getYAxisMinValue();
	float getXAxisMaxValue();
	float getYAxisMaxValue();
	
	public Container getContainer();
}

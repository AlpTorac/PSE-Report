package gelf.view.diagrams.builder;

import java.awt.Color;
import java.awt.Container;
import java.util.Collection;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.SettingsProvider;
import gelf.view.diagrams.components.DiagramAxis;
import gelf.view.diagrams.components.DiagramComponent;
import gelf.view.diagrams.components.DiagramComponentFactory;
import gelf.view.diagrams.components.DiagramValueDisplayComponent;
import gelf.view.diagrams.components.PositionInFrame;
import gelf.view.diagrams.data.DiagramData;

public abstract class DiagramBuilder implements IDiagramBuilder, ContainerAccessPoint {
	public static final SettingsProvider settingsProvider = SettingsProvider.getInstance();
	public static final DiagramComponentFactory factory = DiagramComponentFactory.getDiagramComponentFactory();
	
	protected DiagramData data;
	protected Container container;
	
	public DiagramBuilder(Container container) {
		this.container = container;
	}
	
	private void setDiagramData(DiagramData data) {
		this.data = data;
	}
	
	private PositionInFrame getXAxisEndPosition(PositionInFrame startX, int topRightXPos) {
		return factory.makePositionInFrame(topRightXPos, startX.getYPos());
	}
	
	private PositionInFrame getYAxisEndPosition(PositionInFrame startY, int topRightYPos) {
		return factory.makePositionInFrame(startY.getXPos(), topRightYPos);
	}
	
	private DiagramAxis makeXAxis(PositionInFrame axisOrigin, PositionInFrame endX, float minVal, float maxVal,
			int steps, Color axisLineColor, int thickness) {
		DiagramAxis xAxis = factory.createSolidAxis(axisOrigin, endX, minVal, maxVal, steps, axisLineColor, thickness, this.getXAxisDescriptions());
		return xAxis;
	}
	
	private DiagramAxis makeYAxis(PositionInFrame axisOrigin, PositionInFrame endY, float minVal, float maxVal,
			int steps, Color axisLineColor, int thickness) {
		DiagramAxis yAxis = factory.createSolidAxis(axisOrigin, endY, minVal, maxVal, steps, axisLineColor, thickness, this.getYAxisDescriptions());
		return yAxis;
	}
	
	protected float getXAxisMinValue() {
		return 0;
	}
	protected abstract float getYAxisMinValue();
	protected abstract float getXAxisMaxValue();
	protected abstract float getYAxisMaxValue();
	
	protected String[] getXAxisDescriptions() {
		return null;
	}
	protected String[] getYAxisDescriptions() {
		return null;
	}
	
	protected int getXAxisSteps() {
		return settingsProvider.getStepsInXAxis();
	}
	protected int getYAxisSteps() {
		return settingsProvider.getStepsInYAxis();
	}
	
	protected DiagramAxis[] buildAxes() {
		int containerWidth = this.container.getWidth();
		int containerHeight = this.container.getHeight();
		
		int stepsInXAxis = this.getXAxisSteps();
		int stepsInYAxis = this.getYAxisSteps();
		
		float xAxisMinVal = this.getXAxisMinValue();
		float xAxisMaxVal = this.getXAxisMaxValue();
		float yAxisMinVal = this.getYAxisMinValue();
		float yAxisMaxVal = this.getYAxisMaxValue();
		
		int bottomLeftYPos = Math.round(containerHeight - settingsProvider.getDiagramBottomMarigin(containerHeight));
		int bottomLeftXPos = Math.round(settingsProvider.getDiagramLeftMarigin(containerWidth));
		int topRightYPos = Math.round(settingsProvider.getDiagramTopMarigin(containerHeight));
		int topRightXPos = Math.round(containerWidth - settingsProvider.getDiagramRightMarigin(containerWidth));
		
//		PositionInFrame axisOrigin = this.getAxisOriginPosition(xAxisYpos, yAxisXpos);
		PositionInFrame startX = this.getXAxisStartPosition(bottomLeftXPos, bottomLeftYPos, yAxisMinVal, yAxisMaxVal, topRightYPos, containerHeight);
		PositionInFrame startY = this.getYAxisStartPosition(bottomLeftXPos, bottomLeftYPos);
		PositionInFrame endX = this.getXAxisEndPosition(startX, topRightXPos);
		PositionInFrame endY = this.getYAxisEndPosition(startY, topRightYPos);
		
		Color axisLineColor = settingsProvider.getAxisColor();
		int thickness = settingsProvider.getAxisThickness();
		
		DiagramAxis xAxis = this.makeXAxis(startX, endX, xAxisMinVal, xAxisMaxVal, stepsInXAxis, axisLineColor, thickness);
		this.xAxisSpecificVisualEffect(xAxis, yAxisMinVal, yAxisMaxVal);
		DiagramAxis yAxis = this.makeYAxis(startY, endY, yAxisMinVal, yAxisMaxVal, stepsInYAxis, axisLineColor, thickness);
		this.yAxisSpecificVisualEffect(yAxis, xAxisMinVal, xAxisMaxVal);
		return new DiagramAxis[] {xAxis, yAxis};
	}
	
	protected double getXAxisYPos(int bottomLeftYPos, float yAxisMinVal, float yAxisMaxVal, int topRightYPos, int containerHeight) {
		double startY = bottomLeftYPos * Math.abs(yAxisMaxVal) / (Math.abs(yAxisMaxVal) + Math.abs(yAxisMinVal));
		
		if (startY <= settingsProvider.getDiagramTopMarigin(containerHeight)) {
			return settingsProvider.getDiagramTopMarigin(containerHeight);
		}
		return startY;
	}
	
	protected double getYAxisXPos(int bottomLeftXPos) {
		return bottomLeftXPos;
	}
	
	protected PositionInFrame getYAxisStartPosition(int bottomLeftXPos, int bottomLeftYPos) {
		return factory.makePositionInFrame(this.getYAxisXPos(bottomLeftXPos), bottomLeftYPos);
	}

	protected PositionInFrame getXAxisStartPosition(int bottomLeftXPos, int bottomLeftYPos, float yAxisMinVal, float yAxisMaxVal, int topRightYPos, int containerHeight) {
		return factory.makePositionInFrame(bottomLeftXPos, this.getXAxisYPos(bottomLeftYPos, yAxisMinVal, yAxisMaxVal, topRightYPos, containerHeight));
	}

	protected DiagramComponent[] buildDiagramSpecificComponent() {
		return this.buildDiagramSpecificComponentForOneDiagram(this.getDiagramData());
	}

	protected DiagramValueDisplayComponent[] buildValueDisplayComponents(DiagramAxis[] axes,
			DiagramComponent[] diagramSpecificComponent) {
		return this.buildValueDisplayComponentsForOneDiagram(this.getDiagramData(),
				axes, diagramSpecificComponent);
	}
	protected abstract IDiagram makeDiagram(DiagramAxis[] axes, DiagramValueDisplayComponent[] valueDisplayComponents,
			DiagramComponent[] diagramSpecificComponent);
	
	public IDiagram buildDiagram() {
		DiagramAxis[] axes = this.buildAxes();
		DiagramComponent[] dc = this.buildDiagramSpecificComponent();
		DiagramValueDisplayComponent[] dvdc = this.buildValueDisplayComponents(axes, dc);
		return this.makeDiagram(axes, dvdc, dc);
	}
	
	public void receiveDiagramData(Collection<?> data, int numberOfIndices) {
		DiagramData diagramData = new DiagramData(data, numberOfIndices);
		this.setDiagramData(diagramData);
	}
	
	public void receiveDiagramData(Collection<?> data, Collection<?> descriptions, int numberOfIndices) {
		DiagramData diagramData = new DiagramData(data, descriptions, numberOfIndices);
		this.setDiagramData(diagramData);
	}
	
	protected void xAxisSpecificVisualEffect(DiagramAxis xAxis, float yMin, float yMax) {
		if (Math.abs(yMax) / Math.abs(yMax - yMin) >= 0.5d) {
			xAxis.setShowValuesUnderAxis(true);
		} else {
			xAxis.setShowValuesUnderAxis(false);
		}
	}
	protected void yAxisSpecificVisualEffect(DiagramAxis yAxis, float xMin, float xMax) {
		if (Math.abs(xMax) / Math.abs(xMax - xMin) >= 0.5d) {
			yAxis.setShowValuesUnderAxis(false);
		} else {
			yAxis.setShowValuesUnderAxis(true);
		}
	}

	protected DiagramData getDiagramData() {
		return this.data;
	}
	
	@Override
	public Container getContainer() {
		return this.container;
	}
}

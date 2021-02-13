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

public abstract class DiagramBuilder implements IDiagramBuilder, DataAccessPoint, ContainerAccessPoint {
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
	
	private PositionInFrame getAxisOriginPosition(int xAxisYpos, int yAxisXpos) {
		return factory.makePositionInFrame(yAxisXpos, xAxisYpos);
	}
	
	private PositionInFrame getXAxisEndPosition(int containerWidth, int xAxisYpos) {
		return factory.makePositionInFrame(containerWidth * (1 - settingsProvider.getDiagramRightMariginFactor()), xAxisYpos);
	}
	
	private PositionInFrame getYAxisEndPosition(int containerHeight, int yAxisXpos) {
		return factory.makePositionInFrame(yAxisXpos, containerHeight * settingsProvider.getDiagramTopMariginFactor());
	}
	
	private DiagramAxis makeXAxis(PositionInFrame axisOrigin, PositionInFrame endX, float minVal, float maxVal,
			int steps, Color axisLineColor, int thickness) {
		DiagramAxis xAxis = factory.createSolidAxis(axisOrigin, endX, minVal, maxVal, steps, axisLineColor, thickness);
		xAxis.showValuesUnderAxis();
		return xAxis;
	}
	
	private DiagramAxis makeYAxis(PositionInFrame axisOrigin, PositionInFrame endY, float minVal, float maxVal,
			int steps, Color axisLineColor, int thickness) {
		DiagramAxis yAxis = factory.createSolidAxis(axisOrigin, endY, minVal, maxVal, steps, axisLineColor, thickness);
		yAxis.showValuesUnderAxis();
		return yAxis;
	}
	
	protected float getXAxisMinValue() {
		return 0;
	}
	protected float getYAxisMinValue() {
		return 0;
	}
	protected abstract float getXAxisMaxValue();
	protected abstract float getYAxisMaxValue();
	
	protected int getXAxisSteps() {
		return settingsProvider.getStepsInXAxis();
	}
	protected int getYAxisSteps() {
		return settingsProvider.getStepsInYAxis();
	}
	
	protected DiagramAxis[] buildAxes() {
		int containerWidth = this.container.getWidth();
		int containerHeight = this.container.getHeight();
		
		int xAxisYpos = Math.round(containerHeight * (1 - settingsProvider.getDiagramBottomMariginFactor()));
		int yAxisXpos = Math.round(containerWidth * settingsProvider.getDiagramLeftMariginFactor());
		
		PositionInFrame axisOrigin = this.getAxisOriginPosition(xAxisYpos, yAxisXpos);
		PositionInFrame endX = this.getXAxisEndPosition(containerWidth, xAxisYpos);
		PositionInFrame endY = this.getYAxisEndPosition(containerHeight, yAxisXpos);
		
		int stepsInXAxis = this.getXAxisSteps();
		int stepsInYAxis = this.getYAxisSteps();
		
		float xAxisMinVal = this.getXAxisMinValue();
		float xAxisMaxVal = this.getXAxisMaxValue();
		float yAxisMinVal = this.getYAxisMinValue();
		float yAxisMaxVal = this.getYAxisMaxValue();
		
		Color axisLineColor = settingsProvider.getAxisColor();
		int thickness = settingsProvider.getAxisThickness();
		
		DiagramAxis xAxis = this.makeXAxis(axisOrigin, endX, xAxisMinVal, xAxisMaxVal, stepsInXAxis, axisLineColor, thickness);
		this.xAxisSpecificVisualEffect(xAxis);
		DiagramAxis yAxis = this.makeYAxis(axisOrigin, endY, yAxisMinVal, yAxisMaxVal, stepsInYAxis, axisLineColor, thickness);
		this.yAxisSpecificVisualEffect(yAxis);
		return new DiagramAxis[] {xAxis, yAxis};
	}
	
	protected DiagramComponent[] buildDiagramSpecificComponent() {
		return this.buildDiagramSpecificComponentForOneDiagram(this.getDiagramData());
	}

	protected DiagramValueDisplayComponent[] buildValueDisplayComponents(DiagramAxis[] axes,
			DiagramComponent[] diagramSpecificComponent) {
		return this.buildValueDisplayComponentsForOneDiagram(this.getDiagramData(), axes, diagramSpecificComponent);
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
	
	protected void xAxisSpecificVisualEffect(DiagramAxis xAxis) {}
	protected void yAxisSpecificVisualEffect(DiagramAxis yAxis) {
		yAxis.showValuesAboveAxis();
	}
	
	@Override
	public DiagramData getDiagramData() {
		return this.data;
	}

	@Override
	public Container getContainer() {
		return this.container;
	}
}

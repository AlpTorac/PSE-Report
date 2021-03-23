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

/**
 * An interface implemented by classes that build {@link gelf.view.diagrams.type.Diagram Diagram}.
 * @author Alp Torac Genc
 *
 */
public interface IDiagramBuilder {
	public default SettingsProvider getSettingsProvider() {
		return SettingsProvider.getInstance();
	}
	
	public default DiagramComponentFactory getDiagramComponentFactory() {
		return DiagramComponentFactory.getDiagramComponentFactory();
	}
	/**
	 * The standard call of {@link #buildValueDisplayComponentsForOneDiagram(DiagramData, int, DiagramAxis[], DiagramComponent[]) buildValueDisplayComponentsForOneDiagram}, when
	 * diagrams are not being overlaid.
	 */
	public default DiagramValueDisplayComponent[] buildValueDisplayComponentsForOneDiagram(DiagramData data,
			DiagramAxis[] axes, DiagramComponent[] diagramSpecificComponent) {
		return this.buildValueDisplayComponentsForOneDiagram(data, 0, axes, diagramSpecificComponent);
	}
	/**
	 * Builds components of the diagram specified in data, which are responsible for displaying values.
	 * Overridden in sub-interfaces, if overlaying the type of diagram to be built is implemented.
	 * <p>
	 * Call to build diagrams, which will be overlaid into a new diagram.
	 * @param data the data needed to build the diagram.
	 * @param orderInSameDiagram the order of the diagram (build time-wise) in the overlaid diagram. Used to decide which color
	 * the component will have in {@link gelf.view.diagrams.SettingsProvider#valueDisplayComponentColors valueDisplayComponentColors}.
	 * @param axes the axes, according to which the components responsible for displaying values will be positioned
	 * @param diagramSpecificComponent components, which are not axes and which influence the said components
	 * @return The said built components.
	 * @throws UnsupportedOperationException If overlaying of the diagram has not been implemented or impossible.
	 */
	public default DiagramValueDisplayComponent[] buildValueDisplayComponentsForOneDiagram(DiagramData data, int orderInSameDiagram,
			DiagramAxis[] axes, DiagramComponent[] diagramSpecificComponent) throws UnsupportedOperationException {
		throw new UnsupportedOperationException("This type of diagram cannot be overlaid.");
	}
	/**
	 * Builds components of the diagram that are not axes and are not responsible for displaying values.
	 * @param data the data needed to built the diagram.
	 * @return The built said components.
	 */
	public DiagramComponent[] buildDiagramSpecificComponentForOneDiagram(DiagramData data);
	/**
	 * @return The standard method for {@link #getColorForDiagram(int) getColorForDiagram}, when no diagram is being overlaid.
	 */
	public default Color getColorForDiagram() {
		return this.getSettingsProvider().getValueDisplayComponentColorAt(0);
	}
	/**
	 * @return The color that will be used to paint components built in {@link #buildValueDisplayComponentsForOneDiagram(DiagramData, int, DiagramAxis[], DiagramComponent[]) buildValueDisplayComponentsForOneDiagram}.
	 */
	public default Color getColorForDiagram(int orderInSameDiagram) {
		return this.getSettingsProvider().getValueDisplayComponentColorAt(orderInSameDiagram);
	}
	/**See {@link #buildAxes} for parameters.
	 * @return The position of the end of the x-axis.
	 */
	default PositionInFrame getXAxisEndPosition(PositionInFrame startX, int topRightXPos) {
		return this.getDiagramComponentFactory().makePositionInFrame(topRightXPos, startX.getYPos());
	}
	/**See {@link #buildAxes} for parameters.
	 * @return The position of the end of the y-axis.
	 */
	default PositionInFrame getYAxisEndPosition(PositionInFrame startY, int topRightYPos) {
		return this.getDiagramComponentFactory().makePositionInFrame(startY.getXPos(), topRightYPos);
	}
	/**See {@link #buildAxes} for parameters.
	 * @return The specified x-axis.
	 */
	default DiagramAxis makeXAxis(PositionInFrame axisOrigin, PositionInFrame endX, float minVal, float maxVal,
			int steps, Color axisLineColor, int thickness) {
		DiagramAxis xAxis = this.getDiagramComponentFactory().createSolidAxis(axisOrigin, endX, minVal, maxVal, steps, axisLineColor, thickness, this.getXAxisDescriptions());
		return xAxis;
	}
	/**See {@link #buildAxes} for parameters.
	 * @return The specified y-axis.
	 */
	default DiagramAxis makeYAxis(PositionInFrame axisOrigin, PositionInFrame endY, float minVal, float maxVal,
			int steps, Color axisLineColor, int thickness) {
		DiagramAxis yAxis = this.getDiagramComponentFactory().createSolidAxis(axisOrigin, endY, minVal, maxVal, steps, axisLineColor, thickness, this.getYAxisDescriptions());
		return yAxis;
	}
	/**
	 * @return The amount of steps the x-axis will have.
	 */
	default int getXAxisSteps() {
		return this.getSettingsProvider().getStepsInXAxis();
	}
	/**
	 * @return The amount of steps the y-axis will have.
	 */
	default int getYAxisSteps() {
		return this.getSettingsProvider().getStepsInYAxis();
	}
	/**
	 * @return The axes of the diagram to be built.
	 */
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
	/**See {@link #buildAxes} for parameters.
	 * @return The y-coordinate of the x-axis.
	 */
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
	/**See {@link #buildAxes} for parameters.
	 * @return The x-coordinate of the y-axis.
	 */
	default double getYAxisXPos(int bottomLeftXPos) {
		return bottomLeftXPos;
	}
	/**See {@link #buildAxes} for parameters.
	 * @return The position of the start of the y-axis.
	 */
	default PositionInFrame getYAxisStartPosition(int bottomLeftXPos, int bottomLeftYPos) {
		return this.getDiagramComponentFactory().makePositionInFrame(this.getYAxisXPos(bottomLeftXPos), bottomLeftYPos);
	}
	/**See {@link #buildAxes} for parameters.
	 * @return The position of the start of the x-axis.
	 */
	default PositionInFrame getXAxisStartPosition(int bottomLeftXPos, double yAxisLength, float yAxisMinVal, float yAxisMaxVal, int topRightYPos, int containerHeight) {
		return this.getDiagramComponentFactory().makePositionInFrame(bottomLeftXPos, this.getXAxisYPos(yAxisLength, yAxisMinVal, yAxisMaxVal, topRightYPos, containerHeight));
	}
	/**
	 * The method, which encapsulates what will be done after the creation of the x-axis.
	 * @param xAxis the built x-axis
	 * @param yMin the minimum value on the y-axis
	 * @param yMax the maximum value on the y-axis
	 */
	default void xAxisSpecificVisualEffect(DiagramAxis xAxis, float yMin, float yMax) {
		/*
		 * Display values of the x-axis underneath the axis, if half or more of the
		 * y-axis is above the x-axis.
		 */
		if (Math.abs(yMax) / Math.abs(yMax - yMin) >= 0.5d) {
			xAxis.setShowValuesUnderAxis(true);
		} else {
			xAxis.setShowValuesUnderAxis(false);
		}
	}
	default void yAxisSpecificVisualEffect(DiagramAxis yAxis, float xMin, float xMax) {
		/*
		 * Display values of the y-axis left of the axis, if half or more of the
		 * x-axis is at right of the y-axis.
		 */
		if (Math.abs(xMax) / Math.abs(xMax - xMin) >= 0.5d) {
			yAxis.setShowValuesUnderAxis(false);
		} else {
			yAxis.setShowValuesUnderAxis(true);
		}
	}
	/**
	 * @return What will be displayed at the steps of the x-axis.
	 */
	default String[] getXAxisDescriptions() {
		return null;
	}
	/**
	 * @return What will be displayed at the steps of the y-axis.
	 */
	default String[] getYAxisDescriptions() {
		return null;
	}
	default float getXAxisMinValue() {
		return 0;
	}
	float getYAxisMinValue();
	float getXAxisMaxValue();
	float getYAxisMaxValue();
	/**
	 * @return The container, in respect to which the diagram's dimensions will be chosen.
	 */
	public Container getContainer();
}

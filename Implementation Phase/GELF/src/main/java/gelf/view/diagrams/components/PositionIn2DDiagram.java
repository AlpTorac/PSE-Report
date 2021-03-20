package gelf.view.diagrams.components;

import gelf.view.diagrams.SettingsProvider;

/**
 * The class that represents a point (x, y) in a two dimensional {@link gelf.view.diagrams.type.Diagram Diagram}.
 * @author Alp Torac Genc
 *
 */
public class PositionIn2DDiagram {
	private DiagramAxis xAxis;
	private double xCoordinate;
	private DiagramAxis yAxis;
	private double yCoordinate;

	protected PositionIn2DDiagram(DiagramAxis xAxis, double xCoordinate, DiagramAxis yAxis, double yCoordinate) {
		this.xAxis = xAxis;
		this.yAxis = yAxis;
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
	}
	/**
	 * Constructs an instance by adding the vector specified in x and y coordinates to reference point's coordinates.
	 */
	protected PositionIn2DDiagram(PositionIn2DDiagram referencePoint, double xCoordinate, double yCoordinate) {
		this.xAxis = referencePoint.xAxis;
		this.yAxis = referencePoint.yAxis;
		this.xCoordinate = referencePoint.xCoordinate + xCoordinate;
		this.yCoordinate = referencePoint.yCoordinate + yCoordinate;
	}
	
	public void setXCoordinate(double xCoordinate) {
		this.xCoordinate = xCoordinate;
	}

	public double getXCoordinate() {
		return this.xCoordinate;
	}

	public void setYCoordinate(double yCoordinate) {
		this.yCoordinate = yCoordinate;
	}

	public double getYCoordinate() {
		return this.yCoordinate;
	}

	@Override
	public PositionIn2DDiagram clone() {
		return new PositionIn2DDiagram(this.xAxis, this.getXCoordinate(), this.yAxis, this.getYCoordinate());
	}

	public PositionInFrame toPositionInFrame() {
		PositionInFrame xPosInFrame = this.xAxis.valueToCoordinate(this.getXCoordinate());
		PositionInFrame yPosInFrame = this.yAxis.valueToCoordinate(this.getYCoordinate());
		return new PositionInFrame(xPosInFrame.getXPos(), yPosInFrame.getYPos());
	}

	@Override
	public boolean equals(Object pos) {
		if (pos == null || !(pos instanceof PositionIn2DDiagram)) {
			return false;
		}
		
		PositionIn2DDiagram otherPos = (PositionIn2DDiagram) pos;
		
		if (!((Math.abs(this.xCoordinate - otherPos.xCoordinate) <= SettingsProvider.getTolerance()) && (this.xAxis.equals(otherPos.xAxis)))) {
			return false;
		}
		if (!((Math.abs(this.yCoordinate - otherPos.yCoordinate) <= SettingsProvider.getTolerance()) && (this.yAxis.equals(otherPos.yAxis)))) {
			return false;
		}
		
		return true;
	}
}

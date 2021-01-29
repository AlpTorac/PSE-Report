package gelf.view.diagrams.components;

public abstract class PositionInDiagram {
	private DiagramAxis[] axes;
	private double[] positionsInAxes;

	public PositionInDiagram(DiagramAxis[] axes, double[] coordinatesInAxes) {
		this.axes = axes;
		this.positionsInAxes = coordinatesInAxes;
	}

	public double axisCoordinateToFrameCoordinate(int index) {
		return to2DCoordinate(index);
	}

	public PositionInFrame toPositionInFrame() {
		double[] coordinates = to2D();
		return new PositionInFrame(coordinates[0], coordinates[1]);
	}

	protected void setAxisCoordinate(int index, double position) {
		this.positionsInAxes[index] = position;
	}

	protected void setAxisCoordinates(double[] coordinates) {
		this.positionsInAxes = coordinates;
	}

	protected double getAxisPos(int index) {
		return this.positionsInAxes[index];
	}

	/**
	 * Converts each axis coordinate to 2D coordinates (x, y) using the
	 * transformation matrix inside the subclass.
	 * 
	 * @return The converted coordinates
	 */
	protected abstract double[] to2D();

	/**
	 * Converts each axis coordinate to one of the 2D coordinates (x, y) using the
	 * transformation matrix inside the subclass.
	 * 
	 * @param index The row of the said transformation matrix to use.
	 * 
	 * @return The converted coordinates
	 */
	protected abstract double to2DCoordinate(int index);
}

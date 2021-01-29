package gelf.view.diagrams.components;

public abstract class PositionInDiagram {
	private DiagramAxis[] axes;
	private Number[] positionsInAxes;
	
	public PositionInDiagram(DiagramAxis[] axes, Number[] coordinatesInAxes) {
		this.axes = axes;
		this.positionsInAxes = coordinatesInAxes;
	}
	public Number axisCoordinateToFrameCoordinate(int index) {
		return to2DCoordinate(index);
	}
	public PositionInFrame toPositionInFrame() {
		Number[] coordinates = to2D();
		return new PositionInFrame(coordinates[0], coordinates[1]);
	}
	protected void setAxisCoordinate(int index, Number position) {
		this.positionsInAxes[index] = position;
	}
	protected void setAxisCoordinates(Number[] coordinates) {
		this.positionsInAxes = coordinates;
	}
	protected Number getAxisPos(int index) {
		return this.positionsInAxes[index];
	}
	/**
	 * Converts each axis coordinate to 2D coordinates (x, y) using the transformation matrix
	 * inside the subclass.
	 * 
	 * @return The converted coordinates
	 */
	protected abstract Number[] to2D();
	
	/**
	 * Converts each axis coordinate to one of the 2D coordinates (x, y) using the transformation matrix
	 * inside the subclass.
	 * 
	 * @param index The row of the said transformation matrix to use.
	 * 
	 * @return The converted coordinates
	 */
	protected abstract Number to2DCoordinate(int index);
}

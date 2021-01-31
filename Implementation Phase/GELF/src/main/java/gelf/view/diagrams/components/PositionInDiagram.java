package gelf.view.diagrams.components;

public abstract class PositionInDiagram {
	private DiagramAxis[] axes;
	private double[] coordinatesInAxes;

	public PositionInDiagram(DiagramAxis[] axes, double[] coordinatesInAxes) {
		this.axes = axes;
		this.coordinatesInAxes = coordinatesInAxes;
	}
	
	public PositionInDiagram(PositionInDiagram referencePoint, double[] vector) {
		this.axes = referencePoint.axes;
		
		for (int i = 0; i < this.coordinatesInAxes.length; i++) {
			this.coordinatesInAxes[i] = referencePoint.getAxisPos(i) + vector[i];
		}
	}

	public double axisCoordinateToFrameCoordinate(int index) {
		return to2DCoordinate(index);
	}

	public PositionInFrame toPositionInFrame() {
		double[] coordinates = to2D();
		return new PositionInFrame(coordinates[0], coordinates[1]);
	}

	protected void setAxisCoordinate(int index, double position) {
		this.coordinatesInAxes[index] = position;
	}

	protected void setAxisCoordinates(double[] coordinates) {
		this.coordinatesInAxes = coordinates;
	}

	protected double getAxisPos(int index) {
		return this.coordinatesInAxes[index];
	}

	protected DiagramAxis[] getAxes() {
		return this.axes;
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

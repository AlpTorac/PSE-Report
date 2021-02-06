package gelf.view.diagrams.components;

public class PositionIn2DDiagram extends PositionInDiagram {

	private static final double[][] TO_2D_MATRIX = { { 1, 0 }, { 0, 1 } };

	public PositionIn2DDiagram(DiagramAxis xAxis, double xCoordinate, DiagramAxis yAxis, double yCoordinate) {
		super(new DiagramAxis[] { xAxis, yAxis }, new double[] { xCoordinate, yCoordinate });
	}
	
	public PositionIn2DDiagram(PositionIn2DDiagram referencePoint, double[] vector) {
		super(referencePoint, vector);
	}

	public void setXCoordinate(double xCoordinate) {
		this.setAxisCoordinate(0, xCoordinate);
	}

	public double getXCoordinate() {
		return this.getAxisPos(0);
	}

	public void setYCoordinate(double yCoordinate) {
		this.setAxisCoordinate(1, yCoordinate);
	}

	public double getYCoordinate() {
		return this.getAxisPos(1);
	}

	/**
	 * Converts each axis coordinate to 2D coordinates (x, y) using
	 * {@link #TO_2D_MATRIX}.
	 * 
	 * @return The converted coordinates
	 */
	@Override
	protected double[] to2D() {
		double xCoordinate = this.to2DCoordinate(0);
		double yCoordinate = this.to2DCoordinate(1);

		return new double[] { xCoordinate, yCoordinate };
	}

	/**
	 * Converts each axis coordinate to one of the 2D coordinates (x, y) using
	 * {@link #TO_2D_MATRIX}.
	 * 
	 * @return The converted coordinates
	 */
	@Override
	protected double to2DCoordinate(int index) {
		double coordinate = 0;

		double[][] tMatrix = TO_2D_MATRIX;

		coordinate += tMatrix[index][0] * this.getXCoordinate();
		coordinate += tMatrix[index][1] * this.getYCoordinate();

		return coordinate;
	}

	@Override
	public PositionIn2DDiagram clone() {
		return new PositionIn2DDiagram(this.getAxes()[0], this.getXCoordinate(), this.getAxes()[1], this.getYCoordinate());
	}
}
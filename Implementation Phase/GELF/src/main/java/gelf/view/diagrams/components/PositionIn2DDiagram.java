package gelf.view.diagrams.components;

public class PositionIn2DDiagram extends PositionInDiagram {
	
	private static final Number[][] TO_2D_MATRIX = {{1, 0}, {0, 1}};
	
	public PositionIn2DDiagram(DiagramAxis xAxis, Number xCoordinate, DiagramAxis yAxis, Number yCoordinate) {
		super(new DiagramAxis[] {xAxis, yAxis},
				new Number[] {xCoordinate, yCoordinate});
	}
	public void setXCoordinate(Number xCoordinate) {
		this.setAxisCoordinate(0, xCoordinate);
	}
	public Number getXCoordinate() {
		return this.getAxisPos(0);
	}
	public void setYCoordinate(Number yCoordinate) {
		this.setAxisCoordinate(1, yCoordinate);
	}
	public Number getYCoordinate() {
		return this.getAxisPos(1);
	}
	/**
	 * Converts each axis coordinate to 2D coordinates (x, y) using {@link #TO_2D_MATRIX}.
	 * 
	 * @return The converted coordinates
	 */
	@Override
	protected Number[] to2D() {
		double xCoordinate = this.to2DCoordinate(0).doubleValue();
		double yCoordinate = this.to2DCoordinate(1).doubleValue();
		
		return new Number[] {xCoordinate, yCoordinate};
	}
	
	/**
	 * Converts each axis coordinate to one of the 2D coordinates (x, y) using {@link #TO_2D_MATRIX}.
	 * 
	 * @return The converted coordinates
	 */
	@Override
	protected Number to2DCoordinate(int index) {
		double coordinate = 0;
		
		Number[][] tMatrix = TO_2D_MATRIX;
		
		for (int i = 0; i < tMatrix.length; i++) {
			coordinate += tMatrix[i][0].doubleValue() * this.getXCoordinate().doubleValue();
			coordinate += tMatrix[i][1].doubleValue() * this.getYCoordinate().doubleValue();
		}
		
		return coordinate;
	}
}

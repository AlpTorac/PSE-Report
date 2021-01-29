package gelf.view.diagrams.components;

import java.awt.Color;
import java.awt.Rectangle;

public abstract class DiagramPoint extends DiagramValueDisplayComponent {
	/**
	 * The middle of the point.
	 */
	private PositionIn2DDiagram position;
	/**
	 * The radius of the point.
	 */
	private double size;

	protected DiagramPoint(PositionIn2DDiagram position, Color color, double value, double size) {
		super(color, value);

		this.position = position;
		this.size = size;
	}

	public PositionIn2DDiagram getPositionInDiagram() {
		return this.position;
	}

	public void setPositionInDiagram(double x, double y) {
		this.position.setXCoordinate(x);
		this.position.setYCoordinate(y);
		
		this.setComponentBounds();
	}

	public double getSize() {
		return this.size;
	}

	public void setSize(double size) {
		this.size = size;
	}
	
	@Override
	protected void setComponentBounds() {
		Rectangle bounds = new Rectangle();
		PositionInFrame framePosition = this.position.toPositionInFrame();
		
		double sizeInFrame = this.size;
		
		PositionInFrame diagonalStart = new PositionInFrame(framePosition.getXPos() - sizeInFrame,
				framePosition.getYPos() - sizeInFrame);
		PositionInFrame diagonalEnd = new PositionInFrame(framePosition.getXPos() + sizeInFrame,
				framePosition.getYPos() + sizeInFrame);
		
		bounds.setFrameFromDiagonal(diagonalStart.getXPos(),
				diagonalStart.getYPos(), diagonalEnd.getXPos(),
				diagonalEnd.getYPos());
		
		this.visualElement.setBounds(bounds);
	}
}

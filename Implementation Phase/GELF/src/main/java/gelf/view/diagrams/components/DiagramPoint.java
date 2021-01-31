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
	private float size;

	protected DiagramPoint(PositionIn2DDiagram position, Color color, float value, float size) {
		super(color, value);

		this.position = position;
		this.size = size;
	}

	public PositionIn2DDiagram getPositionInDiagram() {
		return this.position;
	}

	public void setPositionInDiagram(float x, float y) {
		this.position.setXCoordinate(x);
		this.position.setYCoordinate(y);
		
		this.setComponentBounds();
	}

	public float getSize() {
		return this.size;
	}

	public void setSize(float size) {
		this.size = size;
	}
	
	@Override
	protected void setComponentBounds() {
		Rectangle bounds = new Rectangle();
		PositionInFrame framePosition = this.position.toPositionInFrame();
		
		float sizeInFrame = this.size;
		
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

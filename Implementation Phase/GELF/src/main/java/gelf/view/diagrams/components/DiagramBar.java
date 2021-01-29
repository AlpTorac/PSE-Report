package gelf.view.diagrams.components;

import java.awt.Color;
import java.awt.Rectangle;

public abstract class DiagramBar extends DiagramValueDisplayComponent {
	private PositionIn2DDiagram topLeft;
	private PositionIn2DDiagram bottomRight;
	private double borderThickness;

	protected DiagramBar(Color color, double value, PositionIn2DDiagram topLeft, PositionIn2DDiagram bottomRight,
			double borderThickness) {
		super(color, value);
	}

	public double getHeight() {
		return 0;
	}

	public double getWidth() {
		return 0;
	}

	public void setTopLeftInDiagram(double x1, double y1) {
		this.topLeft.setXCoordinate(x1);
		this.topLeft.setYCoordinate(y1);
		
		this.setComponentBounds();
	}

	public void setBottomRightInDiagram(double x2, double y2) {
		this.bottomRight.setXCoordinate(x2);
		this.bottomRight.setYCoordinate(y2);
		
		this.setComponentBounds();
	}

	public PositionIn2DDiagram getTopLeftInDiagram() {
		return this.topLeft;
	}

	public PositionIn2DDiagram getBottomRightInDiagram() {
		return this.bottomRight;
	}

	@Override
	public void refreshValueRelevantAttributes() {

	}
	
	@Override
	protected void setComponentBounds() {
		Rectangle bounds = new Rectangle();
		PositionInFrame frameTopLeft = this.topLeft.toPositionInFrame();
		PositionInFrame frameBottomRight = this.bottomRight.toPositionInFrame();
		
		bounds.setFrameFromDiagonal(frameTopLeft.getXPos(),
				frameTopLeft.getYPos(), frameBottomRight.getXPos(),
				frameBottomRight.getYPos());
		
		this.visualElement.setBounds(bounds);
	}
}

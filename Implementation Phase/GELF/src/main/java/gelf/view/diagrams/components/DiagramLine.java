package gelf.view.diagrams.components;

import java.awt.Color;
import java.awt.Rectangle;

public abstract class DiagramLine extends DiagramComponent {
	private PositionInFrame start;
	private PositionInFrame end;
	private float thickness;

	protected DiagramLine(PositionInFrame start, PositionInFrame end, Color color, float thickness) {
		super(color);

		this.start = start;
		this.end = end;
		this.thickness = thickness;
	}

	public PositionInFrame getStartInFrame() {
		return this.start;
	}

	public void setStartInFrame(float x1, float y1) {
		this.start.setXPos(x1);
		this.start.setYPos(y1);
		
		this.setComponentBounds();
	}

	public PositionInFrame getEndInFrame() {
		return this.end;
	}

	public void setEndInFrame(float x2, float y2) {
		this.end.setXPos(x2);
		this.end.setYPos(y2);
		
		this.setComponentBounds();
	}

	public float getThickness() {
		return this.thickness;
	}

	public void setThickness(float thickness) {
		this.thickness = thickness;
	}
	
	protected double calculateHorizontalLength() {
		return Math.abs(this.end.getXPos() - this.start.getXPos());
	}
	
	protected double calculateVerticalLength() {
		return Math.abs(this.end.getYPos() - this.end.getYPos());
	}
	
	protected double calculateLength() {
		double xEdge = this.calculateHorizontalLength();
		double yEdge = this.calculateVerticalLength();
		
		return Math.sqrt(xEdge * xEdge + yEdge * yEdge);
	}
	
	@Override
	protected void setComponentBounds() {
		Rectangle bounds = new Rectangle();
		PositionInFrame frameStart = this.start;
		PositionInFrame frameEnd = this.end;
		
		bounds.setFrameFromDiagonal(frameStart.getXPos(),
				frameStart.getYPos(), frameEnd.getXPos(),
				frameEnd.getYPos());
		
		this.visualElement.setBounds(bounds);
	}
}

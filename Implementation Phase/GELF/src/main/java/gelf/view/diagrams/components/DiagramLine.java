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
		
		this.setComponentBounds(this.getFrameBounds());
	}

	public PositionInFrame getEndInFrame() {
		return this.end;
	}

	public void setEndInFrame(float x2, float y2) {
		this.end.setXPos(x2);
		this.end.setYPos(y2);
		
		this.setComponentBounds(this.getFrameBounds());
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
	protected Rectangle getFrameBounds() {
		Rectangle bounds = new Rectangle();
		
		PositionInFrame topLeftInFrame = this.start;
		PositionInFrame bottomRightInFrame = this.end;
		
		bounds.setFrameFromDiagonal(topLeftInFrame.getXPos(), topLeftInFrame.getYPos(), bottomRightInFrame.getXPos(), bottomRightInFrame.getYPos());
		
		return bounds;
	}
}

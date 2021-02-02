package gelf.view.diagrams.components;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Line2D;

import javax.swing.JLabel;

public abstract class DiagramLine extends DiagramComponent {
	private PositionInFrame start;
	private PositionInFrame end;
	private int thickness;

	protected DiagramLine(PositionInFrame start, PositionInFrame end, Color color, int thickness, Container containingElement) {
		super(color, containingElement);

		this.start = start;
		this.end = end;
		this.thickness = thickness;
		this.initVisualElement();
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

	public int getThickness() {
		return this.thickness;
	}

	public void setThickness(int thickness) {
		this.thickness = thickness;
		this.setComponentBounds(this.getFrameBounds());
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
		
		PositionInFrame startPos = this.start;
		
		bounds.setRect(startPos.getXPos(), startPos.getYPos(), this.calculateLength(), this.thickness);
		
		return bounds;
	}
	
	protected double getAngleRadian() {
		PositionInFrame startPos = this.start;
		PositionInFrame endPos = this.end;
		
		double xDifference = endPos.getXPos() - startPos.getXPos();
		double yDifference = endPos.getYPos() - startPos.getYPos();
		
		return Math.atan2(yDifference, xDifference);
	}
	
	@Override
	protected void initVisualElement() {
		this.visualElement = new LineVisual(this);
	}
	
	protected class LineVisual extends JLabel {
		/**
		 * Generated serial version ID.
		 */
		private static final long serialVersionUID = -2051091734012179305L;
		DiagramLine line;
		
		protected LineVisual(DiagramLine line) {
			this.line = line;
			this.line.setComponentBounds(this.line.getFrameBounds());
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D graphs = (Graphics2D) g;
			
			Rectangle bounds = this.getBounds();
			
			graphs.setColor(this.line.getColor());
			
			Shape line = new Line2D.Double(0, 0, bounds.getMaxX(), 0);
			graphs.draw(line);
			
			graphs.rotate(this.line.getAngleRadian());
			
			super.paintComponent(graphs);
		}
	}
}

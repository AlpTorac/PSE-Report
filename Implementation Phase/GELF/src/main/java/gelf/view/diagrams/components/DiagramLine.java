package gelf.view.diagrams.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Double;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.Border;

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
	}
	
	protected double calculateHorizontalLength() {
		return Math.abs(this.end.getXPos() - this.start.getXPos());
	}
	
	protected double calculateVerticalLength() {
		return Math.abs(this.end.getYPos() - this.start.getYPos());
	}
	
	protected double calculateLength() {
		double xEdge = this.calculateHorizontalLength();
		double yEdge = this.calculateVerticalLength();
		
		return Math.sqrt(xEdge * xEdge + yEdge * yEdge);
	}
	
	@Override
	protected Rectangle getFrameBounds() {
		Rectangle bounds = new Rectangle();
		
		double topLeftX = Math.min(this.start.getXPos(), this.end.getXPos());
		double topLeftY = Math.min(this.start.getYPos(), this.end.getYPos());
		double bottomRightX = Math.max(this.start.getXPos(), this.end.getXPos());
		double bottomRightY = Math.max(this.start.getYPos(), this.end.getYPos());
		
		bounds.setRect(topLeftX, topLeftY, bottomRightX - topLeftX + this.getThickness(), bottomRightY - topLeftY + this.getThickness());
		
		return bounds;
	}
	
	protected double getAngleRadian() {
		PositionInFrame startPos = this.start;
		PositionInFrame endPos = this.end;
		
		double xDifference = endPos.getXPos() - startPos.getXPos();
		double yDifference = endPos.getYPos() - startPos.getYPos();
		
		double angle = Math.atan2(yDifference, xDifference);
		
		return angle;
	}
	
	@Override
	protected void initVisualElement() {
		this.visualElement = new LineVisual(this);
		this.attachToContainer(this.containingElement);
	}
	
	protected class LineVisual extends JLabel {
		/**
		 * Generated serial version ID.
		 */
		private static final long serialVersionUID = -2051091734012179305L;
		private DiagramLine line;
		
		protected LineVisual(DiagramLine line) {
			this.line = line;
			this.setBounds(this.line.getFrameBounds());
			this.setOpaque(false);
			
//			Border border = BorderFactory.createLineBorder(Color.RED, this.line.getThickness());
//			
//			this.setBorder(border);
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			this.setBounds(this.line.getFrameBounds());
			Graphics2D graphs = (Graphics2D) g;
			
			Rectangle bounds = this.getBounds();
			
			graphs.setColor(this.line.getColor());
			graphs.setStroke(new BasicStroke(this.line.getThickness()));
			
			double x1 = this.line.getStartInFrame().getXPos() - bounds.getMinX();
			double y1 = this.line.getStartInFrame().getYPos() - bounds.getMinY();
			double x2 = this.line.getEndInFrame().getXPos() - bounds.getMinX();
			double y2 = this.line.getEndInFrame().getYPos() - bounds.getMinY();
			
			Line2D line = new Line2D.Double(x1, y1, x2, y2);
			
			graphs.draw(line);
		}
	}
}
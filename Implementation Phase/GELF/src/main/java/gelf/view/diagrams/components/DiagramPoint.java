package gelf.view.diagrams.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import javax.swing.JLabel;

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
		
		this.setComponentBounds(this.getFrameBounds());
	}

	public float getSize() {
		return this.size;
	}

	public void setSize(float size) {
		this.size = size;
		this.visualElement.repaint();
	}
	
	@Override
	protected Rectangle getFrameBounds() {
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
		
		return bounds;
	}
	
	@Override
	protected void initVisualElement() {
		this.visualElement = new PointVisual(this);
		this.setComponentBounds(this.getFrameBounds());
	}
	
	protected class PointVisual extends JLabel {

		private DiagramPoint point;
		
		/**
		 * Generated serial version ID.
		 */
		private static final long serialVersionUID = 1231288861330935790L;
		
		protected PointVisual(DiagramPoint point) {
			this.point = point;
			this.setBounds(this.point.getFrameBounds());
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D graphs = (Graphics2D) g;
			
			Rectangle bounds = this.getBounds();
			double diameter = this.point.getSize() * 2d;
			
			Shape point = new Ellipse2D.Double(bounds.getCenterX(), bounds.getCenterY(), diameter, diameter);
			
			graphs.setColor(this.point.getColor());
			graphs.draw(point);
		}
	}
}

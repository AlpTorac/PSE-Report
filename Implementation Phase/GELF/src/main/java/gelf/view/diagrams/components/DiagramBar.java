package gelf.view.diagrams.components;

import java.awt.Color;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.Border;

public abstract class DiagramBar extends DiagramValueDisplayComponent {
	private PositionIn2DDiagram topLeft;
	private PositionIn2DDiagram bottomRight;
	private int borderThickness;

	protected DiagramBar(Color color, float value, PositionIn2DDiagram topLeft, PositionIn2DDiagram bottomRight,
			int borderThickness) {
		super(color, value);
		
		this.topLeft = topLeft;
		this.bottomRight = bottomRight;
		this.borderThickness = borderThickness;
	}

	public double getHeight() {
		return this.getHeight();
	}

	public double getWidth() {
		return (this.bottomRight.getXCoordinate() - this.topLeft.getXCoordinate());
	}

	@Override
	protected Rectangle getFrameBounds() {
		Rectangle bounds = new Rectangle();
		
		PositionInFrame topLeftInFrame = this.topLeft.toPositionInFrame();
		PositionInFrame bottomRightInFrame = this.bottomRight.toPositionInFrame();
		
		bounds.setFrameFromDiagonal(topLeftInFrame.getXPos(), topLeftInFrame.getYPos(), bottomRightInFrame.getXPos(), bottomRightInFrame.getYPos());
		
		return bounds;
	}
	
	public void setTopLeftInDiagram(float x1, float y1) {
		this.topLeft.setXCoordinate(x1);
		this.topLeft.setYCoordinate(y1);
		
		this.setComponentBounds(this.getFrameBounds());
	}

	public void setBottomRightInDiagram(float x2, float y2) {
		this.bottomRight.setXCoordinate(x2);
		this.bottomRight.setYCoordinate(y2);
		
		this.setComponentBounds(this.getFrameBounds());
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
	protected void initVisualElement() {
		this.visualElement = new Bar(this);
	}

	public int getBorderThickness() {
		return this.borderThickness;
	}

	public void setBorderThickness(int borderThickness) {
		this.borderThickness = borderThickness;
		this.visualElement.repaint();
	}
	
	protected class Bar extends JLabel {
		
		/**
		 * Generated serial version ID.
		 */
		private static final long serialVersionUID = -45014560833219508L;
		private DiagramBar bar;
		
		protected Bar(DiagramBar bar) {
			this.bar = bar;
			
			this.setBounds(this.bar.getFrameBounds());
			Border border = BorderFactory.createLineBorder(Color.BLACK, this.bar.borderThickness);
			
			this.setBorder(border);
			
			this.setBackground(this.bar.getColor());
		}
	}
}

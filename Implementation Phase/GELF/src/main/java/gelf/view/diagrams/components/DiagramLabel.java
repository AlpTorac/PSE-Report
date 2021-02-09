package gelf.view.diagrams.components;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

public abstract class DiagramLabel extends DiagramComponent {
	private String caption;
	private PositionInFrame topLeft;
	private PositionInFrame bottomRight;
	private int borderThickness;

	protected DiagramLabel(PositionInFrame topLeft, PositionInFrame bottomRight, Color color, String caption,
			int borderThickness, Container containingElement) {
		super(color, containingElement);

		this.caption = caption;
		this.topLeft = topLeft;
		this.bottomRight = bottomRight;
		this.borderThickness = borderThickness;
		this.initVisualElement();
	}

	public String getCaption() {
		return this.caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
		this.visualElement.repaint();
	}

	public PositionInFrame getTopLeftInDiagram() {
		return this.topLeft;
	}

	public void setTopLeftInDiagram(double x1, double y1) {
		this.topLeft.setXPos(x1);
		this.topLeft.setYPos(y1);
		
		this.setComponentBounds(this.getFrameBounds());
	}

	public PositionInFrame getBottomRightInDiagram() {
		return this.bottomRight;
	}

	public void setBottomRightInDiagram(double x2, double y2) {
		this.bottomRight.setXPos(x2);
		this.bottomRight.setYPos(y2);
		
		this.setComponentBounds(this.getFrameBounds());
	}

	public int getBorderThickness() {
		return this.borderThickness;
	}

	public void setBorderThickness(int borderThickness) {
		this.borderThickness = borderThickness;
		this.visualElement.repaint();
	}
	
	@Override
	protected Rectangle getFrameBounds() {
		Rectangle bounds = new Rectangle();
		
		PositionInFrame topLeftInFrame = this.topLeft;
		PositionInFrame bottomRightInFrame = this.bottomRight;
		
		bounds.setFrameFromDiagonal(topLeftInFrame.getXPos(), topLeftInFrame.getYPos(), bottomRightInFrame.getXPos(), bottomRightInFrame.getYPos());
		
		return bounds;
	}
	
	@Override
	protected void initVisualElement() {
		this.visualElement = new LabelVisual(this);
		this.attachToContainer(this.containingElement);
	}
	
	protected class LabelVisual extends JLabel {
		/**
		 * Generated serial version ID.
		 */
		private static final long serialVersionUID = -784432558268794661L;
		private DiagramLabel label;
		
		protected LabelVisual(DiagramLabel label) {
			this.label = label;
			this.setBounds(this.label.getFrameBounds());
			this.setBackground(this.label.getColor());
			this.setBorder(BorderFactory.createLineBorder(Color.BLACK, this.label.getBorderThickness()));
			this.setOpaque(true);
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			this.setBounds(this.label.getFrameBounds());
			this.setBackground(this.label.getColor());
			this.setBorder(BorderFactory.createLineBorder(Color.BLACK, this.label.getBorderThickness()));
			this.setHorizontalAlignment(CENTER);
			this.setVerticalAlignment(CENTER);
			this.setForeground(Color.BLACK);
			this.setText(this.label.getCaption());
		}
	}
}
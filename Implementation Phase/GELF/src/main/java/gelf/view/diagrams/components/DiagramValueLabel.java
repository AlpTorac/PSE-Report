package gelf.view.diagrams.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

public abstract class DiagramValueLabel extends DiagramValueDisplayComponent {
	private static final Color DEFAULT_BORDER_COLOR = Color.BLACK;
	
	private PositionIn2DDiagram bottomRight;
	private PositionIn2DDiagram topLeft;
	private String caption;
	private Color borderColor = DEFAULT_BORDER_COLOR;
	private int borderThickness;

	protected DiagramValueLabel(PositionIn2DDiagram topLeft, PositionIn2DDiagram bottomRight, Color color, float value,
			int borderThickness) {
		super(color, value);

		this.bottomRight = bottomRight;
		this.topLeft = topLeft;
		this.borderThickness = borderThickness;
		this.caption = String.valueOf(value);
		
		this.initHoverableVisualElement();
	}

	public PositionIn2DDiagram getBottomRightInDiagram() {
		return this.bottomRight;
	}

	public void setBottomRightInDiagram(double x2, double y2) {
		this.bottomRight.setXCoordinate(x2);
		this.bottomRight.setYCoordinate(y2);
		
		this.setComponentBounds(this.getFrameBounds());
	}

	public PositionIn2DDiagram getTopLeftInDiagram() {
		return this.topLeft;
	}

	public void setTopLeftInDiagram(double x1, double y1) {
		this.topLeft.setXCoordinate(x1);
		this.topLeft.setYCoordinate(y1);
		
		this.setComponentBounds(this.getFrameBounds());
	}

	protected void refreshCaption() {
//		((ValueLabelVisual) this.visualElement).setText(this.caption);
		this.visualElement.repaint();
	}

	public String getCaption() {
		return this.caption;
	}

	private void setCaption(String caption) {
		this.caption = caption;
		
		this.refreshCaption();
	}

	public int getBorderThickness() {
		return this.borderThickness;
	}

	public void setBorderThickness(int borderThickness) {
		this.borderThickness = borderThickness;
		this.visualElement.repaint();
	}
	
	@Override
	public void refreshValueRelevantAttributes() {
		this.setCaption(String.valueOf(this.getValue()));
	}
	
	@Override
	protected Rectangle getFrameBounds() {
		Rectangle bounds = new Rectangle();
		PositionInFrame frameTopLeft = this.topLeft.toPositionInFrame();
		PositionInFrame frameBottomRight = this.bottomRight.toPositionInFrame();
		
		bounds.setFrameFromDiagonal(frameTopLeft.getXPos(),
				frameTopLeft.getYPos(), frameBottomRight.getXPos(),
				frameBottomRight.getYPos());
		
		return bounds;
	}
	
	@Override
	protected void initVisualElement() {
		this.visualElement = new ValueLabelVisual(this);
	}
	
	protected class ValueLabelVisual extends JLabel {
		/**
		 * Generated serial version ID.
		 */
		private static final long serialVersionUID = -784432558268794661L;
		private DiagramValueLabel label;
		
		protected ValueLabelVisual(DiagramValueLabel label) {
			this.label = label;
			this.setBounds(this.label.getFrameBounds());
			this.setBorder(BorderFactory.createLineBorder(borderColor, this.label.getBorderThickness()));
			this.setHorizontalAlignment(CENTER);
			this.setVerticalAlignment(CENTER);
			this.setForeground(borderColor);
			this.setText(this.label.getCaption());
			this.setOpaque(true);
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			this.setHorizontalAlignment(CENTER);
			this.setVerticalAlignment(CENTER);
			this.setForeground(borderColor);
			this.setText(this.label.getCaption());
//			this.setBounds(this.label.getFrameBounds());
//			this.setBorder(BorderFactory.createLineBorder(borderColor, this.label.getBorderThickness()));
//			this.setHorizontalAlignment(CENTER);
//			this.setVerticalAlignment(CENTER);
//			this.setForeground(borderColor);
//			this.setText(this.label.getCaption());
		}
	}
}

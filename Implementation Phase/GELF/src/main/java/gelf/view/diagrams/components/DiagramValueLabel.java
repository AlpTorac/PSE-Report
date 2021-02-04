package gelf.view.diagrams.components;

import java.awt.Color;
import java.awt.Container;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

public abstract class DiagramValueLabel extends DiagramValueDisplayComponent {
	private PositionIn2DDiagram bottomRight;
	private PositionIn2DDiagram topLeft;
	private String caption;
	private int borderThickness;

	protected DiagramValueLabel(PositionIn2DDiagram topLeft, PositionIn2DDiagram bottomRight, Color color, float value,
			int borderThickness, Container containingElement) {
		super(color, value, containingElement);

		this.bottomRight = bottomRight;
		this.topLeft = topLeft;
		this.borderThickness = borderThickness;
		this.initHoverableVisualElement();
	}

	public PositionIn2DDiagram getBottomRightInDiagram() {
		return this.bottomRight;
	}

	public void setBottomRightInDiagram(float x2, float y2) {
		this.bottomRight.setXCoordinate(x2);
		this.bottomRight.setYCoordinate(y2);
		
		this.setComponentBounds(this.getFrameBounds());
	}

	public PositionIn2DDiagram getTopLeftInDiagram() {
		return this.topLeft;
	}

	public void setTopLeftInDiagram(float x1, float y1) {
		this.topLeft.setXCoordinate(x1);
		this.topLeft.setYCoordinate(y1);
		
		this.setComponentBounds(this.getFrameBounds());
	}

	protected void refreshCaption() {
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
			this.setBorder(BorderFactory.createLineBorder(Color.BLACK, this.label.getBorderThickness()));
			this.setBounds(this.label.getFrameBounds());
			this.setText(this.label.getCaption());
		}
	}
}
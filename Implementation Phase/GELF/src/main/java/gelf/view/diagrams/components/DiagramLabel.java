package gelf.view.diagrams.components;

import java.awt.Color;
import java.awt.Rectangle;

public abstract class DiagramLabel extends DiagramComponent {
	private String caption;
	private PositionInFrame topLeft;
	private PositionInFrame bottomRight;
	private float borderThickness;

	protected DiagramLabel(PositionInFrame topLeft, PositionInFrame bottomRight, Color color, String caption,
			float borderThickness) {
		super(color);

		this.caption = caption;
		this.topLeft = topLeft;
		this.bottomRight = bottomRight;
		this.borderThickness = borderThickness;
	}

	public String getCaption() {
		return this.caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public PositionInFrame getTopLeftInDiagram() {
		return this.topLeft;
	}

	public void setTopLeftInDiagram(float x1, float y1) {
		this.topLeft.setXPos(x1);
		this.topLeft.setYPos(y1);
		
		this.setComponentBounds();
	}

	public PositionInFrame getBottomRightInDiagram() {
		return this.bottomRight;
	}

	public void setBottomRightInDiagram(float x2, float y2) {
		this.bottomRight.setXPos(x2);
		this.bottomRight.setYPos(y2);
		
		this.setComponentBounds();
	}

	public float getBorderThickness() {
		return this.borderThickness;
	}

	public void setBorderThickness(float borderThickness) {
		this.borderThickness = borderThickness;
	}
	
	@Override
	protected void setComponentBounds() {
		Rectangle bounds = new Rectangle();
		PositionInFrame frameTopLeft = this.topLeft;
		PositionInFrame frameBottomRight = this.bottomRight;
		
		bounds.setFrameFromDiagonal(frameTopLeft.getXPos(),
				frameTopLeft.getYPos(), frameBottomRight.getXPos(),
				frameBottomRight.getYPos());
		
		this.visualElement.setBounds(bounds);
	}
}

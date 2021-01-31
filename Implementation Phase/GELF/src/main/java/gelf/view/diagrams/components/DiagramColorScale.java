package gelf.view.diagrams.components;

import java.awt.Color;
import java.awt.Rectangle;

public abstract class DiagramColorScale extends DiagramComponent {
	private PositionInFrame topLeft;
	private PositionInFrame bottomRight;
	private float borderThickness;
	private float[] values;
	private Color[] valueColors;

	protected DiagramColorScale(PositionInFrame topLeft, PositionInFrame bottomRight, Color borderColor,
			float[] values, Color[] valueColors, float borderThickness) {
		super(borderColor);

		this.topLeft = topLeft;
		this.bottomRight = bottomRight;
		this.borderThickness = borderThickness;
		this.values = values;
		this.valueColors = valueColors;
	}

	public abstract Color valueToColor(float value);

	public PositionInFrame getTopLeftInFrame() {
		return this.topLeft;
	}

	public void setTopLeftInFrame(float x1, float y1) {
		this.topLeft.setXPos(x1);
		this.topLeft.setYPos(y1);
		
		this.setComponentBounds();
	}

	public PositionInFrame getBottomRightInFrame() {
		return this.bottomRight;
	}

	public void setBottomRightInFrame(float x2, float y2) {
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

	public float[] getValues() {
		return this.values;
	}

	public void setValues(float[] values) {
		this.values = values;
	}

	public Color[] getValueColors() {
		return this.valueColors;
	}

	public void setValueColors(Color[] valueColors) {
		this.valueColors = valueColors;
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

package gelf.view.diagrams.components;

import java.awt.Color;
import java.awt.Rectangle;

public abstract class DiagramColorScale extends DiagramComponent {
	private PositionInFrame topLeft;
	private PositionInFrame bottomRight;
	private double borderThickness;
	private double[] values;
	private Color[] valueColors;

	protected DiagramColorScale(PositionInFrame topLeft, PositionInFrame bottomRight, Color borderColor,
			double[] values, Color[] valueColors, double borderThickness) {
		super(borderColor);

		this.topLeft = topLeft;
		this.bottomRight = bottomRight;
		this.borderThickness = borderThickness;
		this.values = values;
		this.valueColors = valueColors;
	}

	public abstract Color valueToColor(double value);

	public PositionInFrame getTopLeftInFrame() {
		return this.topLeft;
	}

	public void setTopLeftInFrame(double x1, double y1) {
		this.topLeft.setXPos(x1);
		this.topLeft.setYPos(y1);
		
		this.setComponentBounds();
	}

	public PositionInFrame getBottomRightInFrame() {
		return this.bottomRight;
	}

	public void setBottomRightInFrame(double x2, double y2) {
		this.bottomRight.setXPos(x2);
		this.bottomRight.setYPos(y2);
		
		this.setComponentBounds();
	}

	public double getBorderThickness() {
		return this.borderThickness;
	}

	public void setBorderThickness(double borderThickness) {
		this.borderThickness = borderThickness;
	}

	public double[] getValues() {
		return this.values;
	}

	public void setValues(double[] values) {
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

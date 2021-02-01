package gelf.view.diagrams.components;

import java.awt.Color;

public class BiColorScale extends DiagramColorScale {
	private Color minValueColor;
	private Color maxValueColor;
	private float minValue;
	private float maxValue;

	protected BiColorScale(PositionInFrame topLeft, PositionInFrame bottomRight, Color borderColor, float minVal,
			float maxVal, Color minValColor, Color maxValColor, int borderThickness) {
		super(topLeft, bottomRight, borderColor, new float[] {minVal, maxVal},
				new Color[] {minValColor, maxValColor}, borderThickness);

		float[] values = super.getValues();
		Color[] colors = super.getValueColors();
		
		this.minValueColor = colors[0];
		this.maxValueColor = colors[1];
		this.minValue = values[0];
		this.maxValue = values[1];
	}

	public Color getMinValueColor() {
		return minValueColor;
	}

	public void setMinValueColor(Color minValueColor) {
		this.minValueColor = minValueColor;
	}

	public Color getMaxValueColor() {
		return maxValueColor;
	}

	public void setMaxValueColor(Color maxValueColor) {
		this.maxValueColor = maxValueColor;
	}

	public float getMinValue() {
		return minValue;
	}

	public void setMinValue(float minValue) {
		this.minValue = minValue;
	}

	public float getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(float maxValue) {
		this.maxValue = maxValue;
	}

	@Override
	public Color valueToColor(float value) {
		return super.getMixedColor(value, 0, 1);
	}
	
	@Override
	public BiColorScale clone() {
		Color newColor = new Color(this.getColor().getRGB());
		Color minValColor = new Color(this.getMinValueColor().getRGB());
		Color maxValColor = new Color(this.getMaxValueColor().getRGB());
		
		return new BiColorScale(this.getTopLeftInFrame().clone(), this.getBottomRightInFrame().clone(), newColor, this.getMinValue(),
				this.getMaxValue(), minValColor, maxValColor, this.getBorderThickness());
	}
}

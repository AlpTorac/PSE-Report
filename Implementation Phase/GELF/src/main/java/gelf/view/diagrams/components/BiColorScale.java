package gelf.view.diagrams.components;

import java.awt.Color;
import java.awt.color.ColorSpace;

public class BiColorScale extends DiagramColorScale {
	private Color minValueColor;
	private Color maxValueColor;
	private float minValue;
	private float maxValue;

	protected BiColorScale(PositionInFrame topLeft, PositionInFrame bottomRight, Color borderColor, float minVal,
			float maxVal, Color minValColor, Color maxValColor, float borderThickness) {
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
	public DiagramComponent clone() {
		return null;
	}

	@Override
	protected void initVisualElement() {
		// TODO Auto-generated method stub

	}

	@Override
	public Color valueToColor(float value) {
		return super.getMixedColor(value, 0, 1);
	}
}

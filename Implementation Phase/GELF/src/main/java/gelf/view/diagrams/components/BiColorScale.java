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
		super(topLeft, bottomRight, borderColor, new float[] { minVal, maxVal },
				new Color[] { minValColor, maxValColor }, borderThickness);

		this.minValueColor = minValColor;
		this.maxValueColor = maxValColor;
		this.minValue = minVal;
		this.maxValue = maxVal;
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
		return null;
	}
}

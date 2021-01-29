package gelf.view.diagrams.components;

import java.awt.Color;
import java.awt.color.ColorSpace;

public class BiColorScale extends DiagramColorScale {
	private Color minValueColor;
	private Color maxValueColor;
	private double minValue;
	private double maxValue;

	protected BiColorScale(PositionInFrame topLeft, PositionInFrame bottomRight, Color borderColor, double minVal,
			double maxVal, Color minValColor, Color maxValColor, double borderThickness) {
		super(topLeft, bottomRight, borderColor, new double[] { minVal, maxVal },
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

	public double getMinValue() {
		return minValue;
	}

	public void setMinValue(double minValue) {
		this.minValue = minValue;
	}

	public double getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(double maxValue) {
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
	public Color valueToColor(double value) {
		return null;
	}
}

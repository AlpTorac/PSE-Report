package gelf.view.diagrams.components;

import java.awt.Color;
import java.awt.Component;

public class BiColorScale extends DiagramColorScale {
	private Color minValueColor;
	private Color maxValueColor;
	private Number minValue;
	private Number maxValue;
	
	protected BiColorScale(PositionInFrame bottomLeft, PositionInFrame topRight, Color borderColor, Number minVal, Number maxVal, Color minValColor, Color maxValColor, Number borderThickness) {
		super(bottomLeft, topRight, borderColor,
				new Number[] {minVal, maxVal},
				new Color[] {minValColor, maxValColor},
				borderThickness);
		
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
	public Number getMinValue() {
		return minValue;
	}
	public void setMinValue(Number minValue) {
		this.minValue = minValue;
	}
	public Number getMaxValue() {
		return maxValue;
	}
	public void setMaxValue(Number maxValue) {
		this.maxValue = maxValue;
	}
	@Override
	public void hide() {
		
	}
	@Override
	public void show() {
		
	}
	@Override
	public DiagramComponent clone() {
		return null;
	}
	@Override
	protected void initVisualElement() {
		// TODO Auto-generated method stub
		
	}
}

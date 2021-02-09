package gelf.view.diagrams.components;

import java.awt.Color;
import java.awt.Container;

public class BiColorScale extends DiagramColorScale {

	protected BiColorScale(PositionInFrame topLeft, PositionInFrame bottomRight, Color borderColor, float minVal,
			float maxVal, Color minValColor, Color maxValColor, int borderThickness, Container containingElement) {
		super(topLeft, bottomRight, borderColor, new float[] {minVal, maxVal},
				new Color[] {minValColor, maxValColor}, borderThickness, containingElement);
	}

	public Color getMinValueColor() {
		return super.getValueColor(0);
	}

	public void setMinValueColor(Color minValueColor) {
		super.setValueColor(0, minValueColor);
		this.visualElement.repaint();
	}

	public Color getMaxValueColor() {
		return super.getValueColor(1);
	}

	public void setMaxValueColor(Color maxValueColor) {
		super.setValueColor(1, maxValueColor);
		this.visualElement.repaint();
	}

	public float getMinValue() {
		return super.getValue(0);
	}

	public void setMinValue(float minValue) {
		super.setValue(0, minValue);
		this.visualElement.repaint();
	}

	public float getMaxValue() {
		return super.getValue(1);
	}

	public void setMaxValue(float maxValue) {
		super.setValue(1, maxValue);
		this.visualElement.repaint();
	}

//	@Override
//	public Color valueToColor(float value) {
//		return super.getMixedColor(value, 0, 1);
//	}
	
	@Override
	public BiColorScale clone() {
		Color newColor = new Color(this.getColor().getRGB());
		Color minValColor = new Color(this.getMinValueColor().getRGB());
		Color maxValColor = new Color(this.getMaxValueColor().getRGB());
		
		return new BiColorScale(this.getTopLeftInFrame().clone(), this.getBottomRightInFrame().clone(), newColor, this.getMinValue(),
				this.getMaxValue(), minValColor, maxValColor, this.getBorderThickness(), this.containingElement);
	}
}
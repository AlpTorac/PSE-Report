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

	private int getRangeMinIndex(float value) {
		
		int index = 0;
		
		while (this.values[index] < value) {
			index++;
		}
		
		return index - 1;
	}
	
	protected Color getMixedColor(float value, int rangeMinIndex, int rangeMaxIndex) {
		float minValue = this.values[rangeMinIndex];
		float maxValue = this.values[rangeMaxIndex];
		Color minValueColor = this.valueColors[rangeMinIndex];
		Color maxValueColor = this.valueColors[rangeMaxIndex];
		
		double minValColorWeight = (value - minValue) / (maxValue - minValue);
		double maxValColorWeight = 1 - minValColorWeight;
		
		float[] hsbMinValColor = new float[3];
		Color.RGBtoHSB(minValueColor.getRed(), minValueColor.getGreen(), minValueColor.getBlue(), hsbMinValColor);
		
		float[] hsbMaxValColor = new float[3];
		Color.RGBtoHSB(maxValueColor.getRed(), maxValueColor.getGreen(), maxValueColor.getBlue(), hsbMaxValColor);
		
		double mixHue = (hsbMinValColor[0] * minValColorWeight) + (hsbMaxValColor[0] * maxValColorWeight);
		double mixSaturation = (hsbMinValColor[1] * minValColorWeight) + (hsbMaxValColor[1] * maxValColorWeight);
		double mixBrightness = (hsbMinValColor[2] * minValColorWeight) + (hsbMaxValColor[2] * maxValColorWeight);
		
		int mixedColorBits = Color.HSBtoRGB((float) mixHue, (float) mixSaturation, (float) mixBrightness);
		
		Color mixedColor = new Color(mixedColorBits);
		
		return mixedColor;
	}
	
	public Color valueToColor(float value) {
		int rangeMinIndex = this.getRangeMinIndex(value);
		int rangeMaxIndex = this.getRangeMinIndex(value) + 1;
		
		return this.getMixedColor(value, rangeMinIndex, rangeMaxIndex);
	}

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

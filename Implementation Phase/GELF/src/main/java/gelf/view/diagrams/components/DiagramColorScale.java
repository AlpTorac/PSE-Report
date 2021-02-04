package gelf.view.diagrams.components;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public abstract class DiagramColorScale extends DiagramComponent {
	private PositionInFrame topLeft;
	private PositionInFrame bottomRight;
	private int borderThickness;
	private float[] values;
	private Color[] valueColors;

	protected DiagramColorScale(PositionInFrame topLeft, PositionInFrame bottomRight, Color borderColor,
			float[] values, Color[] valueColors, int borderThickness, Container containingElement) {
		super(borderColor, containingElement);

		this.topLeft = topLeft;
		this.bottomRight = bottomRight;
		this.borderThickness = borderThickness;
		this.values = values;
		this.valueColors = valueColors;
		this.initVisualElement();
	}

	private int getRangeMinIndex(float value) {
		
		int index = 0;
		
		while (this.values[index] < value) {
			index++;
		}
		
		return index - 1;
	}
	
	@Override
	protected Rectangle getFrameBounds() {
		Rectangle bounds = new Rectangle();
		
		PositionInFrame topLeftInFrame = this.topLeft;
		PositionInFrame bottomRightInFrame = this.bottomRight;
		
		bounds.setFrameFromDiagonal(topLeftInFrame.getXPos(), topLeftInFrame.getYPos(), bottomRightInFrame.getXPos(), bottomRightInFrame.getYPos());
		
		return bounds;
	}
	
	protected Color getMixedColor(float value, int rangeMinIndex, int rangeMaxIndex) {
		float minValue = this.values[rangeMinIndex];
		float maxValue = this.values[rangeMaxIndex];
		Color minValueColor = this.valueColors[rangeMinIndex];
		Color maxValueColor = this.valueColors[rangeMaxIndex];
		
		double maxValColorWeight = (value - minValue) / (maxValue - minValue);
		double minValColorWeight = 1 - maxValColorWeight;
		
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
		
		this.setComponentBounds(this.getFrameBounds());
	}

	public PositionInFrame getBottomRightInFrame() {
		return this.bottomRight;
	}

	public void setBottomRightInFrame(float x2, float y2) {
		this.bottomRight.setXPos(x2);
		this.bottomRight.setYPos(y2);
		
		this.setComponentBounds(this.getFrameBounds());
	}

	public int getBorderThickness() {
		return this.borderThickness;
	}

	public void setBorderThickness(int borderThickness) {
		this.borderThickness = borderThickness;
		this.visualElement.repaint();
	}
	
	public float[] getValues() {
		return this.values;
	}

	public void setValues(float[] values) {
		this.values = values;
		this.visualElement.repaint();
	}

	public Color[] getValueColors() {
		return this.valueColors;
	}

	public void setValueColors(Color[] valueColors) {
		this.valueColors = valueColors;
	}
	
	@Override
	protected void setComponentBounds(Rectangle bounds) {		
		this.visualElement.setBounds(bounds);
	}
	
	@Override
	protected void initVisualElement() {
		this.visualElement = new ScalePanel(this);
		this.attachToContainer(containingElement);
	}
	
	protected class ScalePanel extends JPanel {

		/**
		 * Generated serial version ID.
		 */
		private static final long serialVersionUID = 769932577992152528L;
		private final DiagramColorScale colorScale;
		
		protected ScalePanel(DiagramColorScale colorScale) {
			this.colorScale = colorScale;
			this.setBorder(BorderFactory.createLineBorder(this.colorScale.getColor(), this.colorScale.getBorderThickness()));
			this.setBounds(this.colorScale.getFrameBounds());
			this.setOpaque(true);
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D graphs = (Graphics2D) g;
			
			float maxVal = this.colorScale.getValues()[this.colorScale.getValues().length - 1];
			float minVal = this.colorScale.getValues()[0];
			
			float valueIntervalLength = maxVal - minVal;
			
			for (int x = 0; x < this.getBounds().width - 2; x++) {
				float currentValue = minVal + valueIntervalLength * (((float) x) / ((float) (this.getBounds().width - 2)));
				
				graphs.setColor(this.colorScale.valueToColor(currentValue));
				graphs.draw(this.getBounds());
				graphs.fillRect(x, 0, 1, this.getBounds().height - 1);
			}
		}
	}
}

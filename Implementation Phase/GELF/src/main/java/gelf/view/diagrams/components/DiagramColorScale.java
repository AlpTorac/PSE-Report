package gelf.view.diagrams.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.SettingsProvider;

/**
 * The super class to classes, which represent color scales in {@link gelf.view.diagrams.type.Diagram Diagram}.
 * <p>
 * A color scale has an array of {@link #values} and {@link #valueColors} that each belong to their (index-wise)
 * corresponding value. These arrays must have the same size. For every value, which is not explicitly given in
 * the values array, a corresponding color will be calculated via a weighted sum of their HSL values.
 * <p>
 * Each color scale has a {@link DiagramAxis} with it to display the meanings of some colors. Change
 * {@link gelf.view.diagrams.SettingsProvider#colorScaleValueDisplaySteps colorScaleValueDisplaySteps}
 * to increase/decrease the frequency, at which the meanings of colors will be displayed.
 * @author Alp Torac Genc
 */
public abstract class DiagramColorScale extends DiagramComponent {
	private PositionInFrame topLeft;
	private PositionInFrame bottomRight;
	private int borderThickness;
	private float[] values;
	private Color[] valueColors;
	private DiagramAxis valueDisplay;

	private static SettingsProvider sp = SettingsProvider.getInstance();
	
	protected DiagramColorScale(PositionInFrame topLeft, PositionInFrame bottomRight, Color borderColor,
			float[] values, Color[] valueColors, int borderThickness) {
		super(borderColor, sp.getDiagramNonValueDisplayLayer());

		this.topLeft = topLeft;
		this.bottomRight = bottomRight;
		this.borderThickness = borderThickness;
		this.values = values;
		this.valueColors = valueColors;
		this.valueDisplay = this.makeValueDisplay(topLeft, bottomRight, values, borderThickness);
		this.valueDisplay.show();
		this.valueDisplay.showValues();
		this.valueDisplay.setShowValuesUnderAxis(false);
		this.initVisualElement();
	}
	private DiagramAxis makeValueDisplay(PositionInFrame topLeft, PositionInFrame bottomRight, float[] values, int borderThickness) {
		DiagramComponentFactory factory = DiagramComponentFactory.getDiagramComponentFactory();
		PositionInFrame end = factory.makePositionInFrame(bottomRight.getXPos(), topLeft.getYPos());
		DiagramAxis valueDisplay = factory.createSolidAxis(topLeft, end, values[0], values[values.length - 1],
				sp.getColorScaleValueDisplaySteps(), this.getColor(), borderThickness);
		return valueDisplay;
	}
	private void updateValueDisplay() {
		this.valueDisplay.setMin(this.values[0]);
		this.valueDisplay.setMax(this.values[this.values.length - 1]);
		this.valueDisplay.setLineThickness(this.getBorderThickness());
		this.valueDisplay.setColor(this.getColor());
		this.valueDisplay.setLineByPos(this.topLeft.getXPos(), this.topLeft.getYPos(), this.bottomRight.getXPos(), this.topLeft.getYPos());
	}
	/**
	 * @param value a given value
	 * @return The minimum value of the interval of {@link #values}, between which the given
	 * value is.
	 */
	private int getRangeMinIndex(float value) {
		int index = 0;
		while (index < this.values.length - 1 && this.values[index] <= value) {
			index++;
		}
		return (index - 1);
	}
	@Override
	protected Rectangle getFrameBounds() {
		Rectangle bounds = new Rectangle();
		
		PositionInFrame topLeftInFrame = this.topLeft;
		PositionInFrame bottomRightInFrame = this.bottomRight;
		
		bounds.setFrameFromDiagonal(topLeftInFrame.getXPos(), topLeftInFrame.getYPos(), bottomRightInFrame.getXPos(), bottomRightInFrame.getYPos());
		
		return bounds;
	}
	/**
	 * Computes a weighted sum of the HSL values of the colors at {@code valueColors[rangeMinIndex]}
	 * and {@code valueColors[rangeMaxIndex]}
	 * @param value a given value
	 * @param rangeMinIndex the index of the minimum value in the interval, in which the value
	 * is.
	 * @param rangeMaxIndex the index of the maximum value in the interval, in which the value
	 * is.
	 * @return The color that corresponds to the given value.
	 */
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
	/**
	 * @param value a given value
	 * @return The color that corresponds to the given value.
	 */
	public Color valueToColor(float value) {
		int rangeMinIndex = this.getRangeMinIndex(value);
		int rangeMaxIndex = rangeMinIndex + 1;
		
		return this.getMixedColor(value, rangeMinIndex, rangeMaxIndex);
	}
	public PositionInFrame getTopLeftInFrame() {
		return this.topLeft;
	}
	public void setTopLeftInFrame(double x1, double y1) {
		this.topLeft.setXPos(x1);
		this.topLeft.setYPos(y1);
		this.updateValueDisplay();
		this.setComponentBounds(this.getFrameBounds());
	}
	public PositionInFrame getBottomRightInFrame() {
		return this.bottomRight;
	}
	public void setBottomRightInFrame(double x2, double y2) {
		this.bottomRight.setXPos(x2);
		this.bottomRight.setYPos(y2);
		this.updateValueDisplay();
		this.setComponentBounds(this.getFrameBounds());
	}
	public int getBorderThickness() {
		return this.borderThickness;
	}
	public void setBorderThickness(int borderThickness) {
		this.borderThickness = borderThickness;
		this.updateValueDisplay();
		this.visualElement.repaint();
	}
	public float[] getValues() {
		return this.values;
	}
	public float getValue(int index) {
		return this.values[index];
	}
	public void setValues(float[] values) {
		this.values = values;
		this.updateValueDisplay();
		this.visualElement.repaint();
	}
	public void setValue(int index, float value) {
		this.values[index] = value;
		this.updateValueDisplay();
	}
	public Color[] getValueColors() {
		return this.valueColors;
	}
	public Color getValueColor(int index) {
		return this.valueColors[index];
	}
	public void setValueColors(Color[] valueColors) {
		this.valueColors = valueColors;
	}
	public void setValueColor(int index, Color color) {
		this.valueColors[index] = color;
	}
	@Override
	protected void setComponentBounds(Rectangle bounds) {		
		this.visualElement.setBounds(bounds);
	}
	@Override
	protected void initVisualElement() {
		this.visualElement = new ScalePanel(this);
	}
	@Override
	public void attachToDiagram(IDiagram diagram) {
		super.attachToDiagram(diagram);
		this.valueDisplay.attachToDiagram(diagram);
	}
	/**
	 * The class that encapsulates the visuals of {@link DiagramColorScale}.
	 * @author Alp Torac Genc
	 */
	protected class ScalePanel extends JPanel {

		/**
		 * Generated serial version ID.
		 */
		private static final long serialVersionUID = 769932577992152528L;
		private final DiagramColorScale colorScale;
		
		protected ScalePanel(DiagramColorScale colorScale) {
			this.colorScale = colorScale;
			this.setBounds(this.colorScale.getFrameBounds());
			this.setOpaque(true);
		}
		
		@Override
		protected void paintBorder(Graphics g) {
			super.paintBorder(g);
			this.setBorder(BorderFactory.createLineBorder(this.colorScale.getColor(), this.colorScale.getBorderThickness()));
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D graphs = (Graphics2D) g;
			
			float maxVal = this.colorScale.getValues()[this.colorScale.getValues().length - 1];
			float minVal = this.colorScale.getValues()[0];
			
			float valueIntervalLength = maxVal - minVal;
			
			for (int x = 0; x < this.getBounds().width; x++) {
				float currentValue = minVal + valueIntervalLength * (((float) x) / ((float) (this.getBounds().width)));
				
				graphs.setColor(this.colorScale.valueToColor(currentValue));
				graphs.draw(this.getBounds());
				graphs.fillRect(x, 0, 1, this.getBounds().height - 1);
			}
		}
	}
}

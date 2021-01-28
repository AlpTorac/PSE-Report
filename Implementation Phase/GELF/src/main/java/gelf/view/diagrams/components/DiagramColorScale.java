package gelf.view.diagrams.components;

import java.awt.Color;

public abstract class DiagramColorScale extends DiagramComponent {
	private PositionInFrame bottomLeft;
	private PositionInFrame topRight;
	private Number borderThickness;
	private Number[] values;
	private Color[] valueColors;
	
	protected DiagramColorScale(PositionInFrame bottomLeft, PositionInFrame topRight, Color borderColor, Number[] values, Color[] valueColors, Number borderThickness) {
		super(borderColor);
		
		this.bottomLeft = bottomLeft;
		this.topRight = topRight;
		this.borderThickness = borderThickness;
		this.values = values;
		this.valueColors = valueColors;
	}
	public Color valueToColor(Number value) {
		return null;
	}
	public PositionInFrame getBottomLeftInFrame() {
		return bottomLeft;
	}
	public void setBottomLeftInFrame(Number x1, Number y1) {
		
	}
	public PositionInFrame getTopRightInFrame() {
		return topRight;
	}
	public void setTopRightInFrame(Number x2, Number y2) {
		
	}
	public Number getBorderThickness() {
		return borderThickness;
	}
	public void setBorderThickness(Number borderThickness) {
		this.borderThickness = borderThickness;
	}
	public Number[] getValues() {
		return values;
	}
	public void setValues(Number[] values) {
		this.values = values;
	}
	public Color[] getValueColors() {
		return valueColors;
	}
	public void setValueColors(Color[] valueColors) {
		this.valueColors = valueColors;
	}
}

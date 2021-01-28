package gelf.view.diagrams.components;

import java.awt.Color;

public abstract class DiagramAxis extends DiagramComponent {
	private Number min;
	private Number max;
	private int steps;
	private boolean showValues;
	private DiagramLine axisLine;
	
	protected DiagramAxis(DiagramLine axisLine, Number min, Number max, int steps) {
		super(axisLine.getColor());
		
		this.min = min;
		this.max = max;
		this.steps = steps;
		this.axisLine = axisLine;
	}
	public Number getMin() {
		return min;
	}
	public void setMin(Number min) {
		this.min = min;
	}
	public Number getMax() {
		return max;
	}
	public void setMax(Number max) {
		this.max = max;
	}
	public int getSteps() {
		return steps;
	}
	public void setSteps(int steps) {
		this.steps = steps;
	}
	public void showValues() {
		this.showValues = true;
	}
	public void hideValues() {
		this.showValues = false;
	}
	public PositionInFrame valueToCoordinate(Number value) {
		return null;
	}
	public Number coordinateToValue(PositionInFrame coordinate) {
		return 0;
	}
	public void setLineByPos(Number minValXPos, Number minValYPos, Number maxValXPos, Number maxValYPos) {
		
	}
	public void setLineColor(Color color) {
		
	}
	public void setLineThickness(Number thickness) {
		
	}
	public Number getLineLength() {
		return 0;
	}
	@Override
	public void show() {
		
	}
	@Override
	public void hide() {
		
	}
}

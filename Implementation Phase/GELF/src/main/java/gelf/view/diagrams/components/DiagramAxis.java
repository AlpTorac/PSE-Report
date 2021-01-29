package gelf.view.diagrams.components;

import java.awt.Color;

public abstract class DiagramAxis extends DiagramComponent {
	private double min;
	private double max;
	private int steps;
	private boolean showValues;
	private DiagramLine axisLine;

	protected DiagramAxis(DiagramLine axisLine, double min, double max, int steps) {
		super(axisLine.getColor());

		this.min = min;
		this.max = max;
		this.steps = steps;
		this.axisLine = axisLine;
	}

	public double getMin() {
		return this.min;
	}

	public void setMin(double min) {
		this.min = min;
	}

	public double getMax() {
		return this.max;
	}

	public void setMax(double max) {
		this.max = max;
	}

	public int getSteps() {
		return this.steps;
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
	
	//The minVal is on start of line, maxVal is on end of line
	public PositionInFrame valueToCoordinate(double value) {
		PositionInFrame lineStart = this.axisLine.getStartInFrame();
		double hLength = this.axisLine.calculateHorizontalLength();
		double vLength = this.axisLine.calculateVerticalLength();
		
		// The distance of the given coordinate from minVal's point
		double factor = (value / this.max);
		
		double frameXPos = hLength * factor;
		double frameYPos = vLength * factor;
		
		return new PositionInFrame(lineStart.getXPos() + frameXPos,
				lineStart.getYPos() + frameYPos);
	}
	/*
	 * Use Fourier formula to calculate:
	 * 
	 * 1) (maxValPos - minValPos) * (coordinate)
	 * 2) coordinate * coordinate // so length * length
	 * 3) 1) / 2)
	 */
	public double coordinateToValue(PositionInFrame coordinate) {
		double scalarProduct = coordinate.getXPos() *
				(this.axisLine.getEndInFrame().getXPos() -
						this.axisLine.getStartInFrame().getXPos());
		
		scalarProduct += coordinate.getYPos() * 
				(this.axisLine.getEndInFrame().getYPos() -
						this.axisLine.getStartInFrame().getYPos());
		
		double hLength = this.axisLine.calculateHorizontalLength();
		double vLength = this.axisLine.calculateVerticalLength();
		
		double denominator = hLength * hLength + vLength * vLength;
		
		return (scalarProduct / denominator);
	}

	public void setLineByPos(double minValXPos, double minValYPos, double maxValXPos, double maxValYPos) {
		this.axisLine.setStartInFrame(minValXPos, minValYPos);
		this.axisLine.setEndInFrame(maxValXPos, maxValYPos);
	}

	public void setLineColor(Color color) {
		this.axisLine.setColor(color);
		this.setColor(color);
	}

	public void setLineThickness(double thickness) {
		this.axisLine.setThickness(thickness);
	}

	public double getLineLength() {
		return this.axisLine.calculateLength();
	}
	
	@Override
	protected void setComponentBounds() {
		this.axisLine.setComponentBounds();
	}
}

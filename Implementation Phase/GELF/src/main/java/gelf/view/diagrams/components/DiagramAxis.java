package gelf.view.diagrams.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Line2D;

import javax.swing.JLabel;

public abstract class DiagramAxis extends DiagramComponent {
	private float min;
	private float max;
	private int steps;
	private boolean showValues;
	private DiagramLine axisLine;

	protected DiagramAxis(DiagramLine axisLine, float min, float max, int steps) {
		super(axisLine.getColor());

		this.min = min;
		this.max = max;
		this.steps = steps;
		this.axisLine = axisLine;
	}

	public float getMin() {
		return this.min;
	}

	public void setMin(float min) {
		this.min = min;
	}

	public float getMax() {
		return this.max;
	}

	public void setMax(float max) {
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
		this.visualElement.repaint();
	}

	public void hideValues() {
		this.showValues = false;
		this.visualElement.repaint();
	}
	
	//The minVal is on start of line, maxVal is on end of line
	public PositionInFrame valueToCoordinate(float value) {
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

	public void setLineByPos(float minValXPos, float minValYPos, float maxValXPos, float maxValYPos) {
		this.axisLine.setStartInFrame(minValXPos, minValYPos);
		this.axisLine.setEndInFrame(maxValXPos, maxValYPos);
	}

	public void setLineColor(Color color) {
		this.axisLine.setColor(color);
	}

	public void setLineThickness(int thickness) {
		this.axisLine.setThickness(thickness);
		this.visualElement.repaint();
	}

	public double getLineLength() {
		return this.axisLine.calculateLength();
	}
	
	@Override
	protected Rectangle getFrameBounds() {
		Rectangle bounds = this.axisLine.getFrameBounds();
		
		bounds.setBounds(bounds.x, bounds.y, bounds.width, bounds.height * 4);
		
		return bounds;
	}
	
	@Override
	protected void setComponentBounds(Rectangle bounds) {
		this.axisLine.setComponentBounds(bounds);
	}
	
	@Override
	protected void initVisualElement() {
		this.visualElement = new AxisVisual(this);
	}
	
	protected class AxisVisual extends JLabel {
		/**
		 * Generated serial version ID.
		 */
		private static final long serialVersionUID = -5939793188795940048L;
		private DiagramAxis axis;
		
		protected AxisVisual(DiagramAxis axis) {
			this.axis = axis;
			this.setBounds(this.axis.getFrameBounds());
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D graphs = (Graphics2D) g;
			
			Rectangle bounds = this.getBounds();
			
			graphs.setColor(this.axis.getColor());
			
			double x1 = 0;
			double y1 = bounds.height / 4d;
			double y2 = 3d * bounds.height / 4d;
			double stepLengthInFrame = bounds.getWidth() / ((double) this.axis.getSteps());
			
			float stepLengthInAxis = (this.axis.getMax() - this.axis.getMin()) / ((float) this.axis.getSteps());
			float currentValue = this.axis.getMin();
			
			if (this.axis.showValues) {
				for (int i = 0; i < this.axis.getSteps(); i++) {
					Shape line = new Line2D.Double(x1, y1, x1, y2);
					graphs.draw(line);
					graphs.drawString(String.valueOf(currentValue), (float) x1, (float) y2);
					currentValue =+ stepLengthInAxis;
					x1 =+ stepLengthInFrame;
				}
			} else {
				for (int i = 0; i < this.axis.getSteps(); i++) {
					Shape line = new Line2D.Double(x1, y1, x1, y2);
					graphs.draw(line);
					x1 =+ stepLengthInFrame;
				}
			}
			
			graphs.rotate(this.axis.axisLine.getAngleRadian());
			super.paintComponent(graphs);
		}
	}
}

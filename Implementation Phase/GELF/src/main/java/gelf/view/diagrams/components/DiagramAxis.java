package gelf.view.diagrams.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Line2D;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.Border;

public abstract class DiagramAxis extends DiagramComponent {
	private int fontSize = 10;
	private float min;
	private float max;
	private int steps;
	private boolean showValues;
	protected DiagramLine axisLine;

	protected DiagramAxis(DiagramLine axisLine, float min, float max, int steps, Container containingElement) {
		super(axisLine.getColor(), containingElement);

		this.min = min;
		this.max = max;
		this.steps = steps;
		this.axisLine = axisLine;
		this.initVisualElement();
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
		PositionInFrame lineEnd = this.axisLine.getEndInFrame();
		
		//Weight of lineEnd
		double factor = ((value - this.min) / (this.max - this.min));
		
		//Weighted summation of lineStart and lineEnd coordinates
		double frameXPos = lineStart.getXPos() * (1 - factor) + lineEnd.getXPos() * factor;
		double frameYPos = lineStart.getYPos() * (1 - factor) + lineEnd.getYPos() * factor;
		
		return new PositionInFrame(frameXPos, frameYPos);
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
	
	public int getLineThickness() {
		return this.axisLine.getThickness();
	}

	public double getLineLength() {
		return this.axisLine.calculateLength();
	}
	
	@Override
	protected Rectangle getFrameBounds() {
		Rectangle bounds = this.axisLine.getFrameBounds();
		
		double lineAngleRadian = this.axisLine.getAngleRadian();
		
		double absValOfDifference = this.fontSize * 10;
		
		double horizontalDifference = absValOfDifference * Math.sin(lineAngleRadian);
		double verticalDifference = absValOfDifference * Math.cos(lineAngleRadian);
		
		double topLeftX = Math.min(bounds.getX(), bounds.getX() + horizontalDifference);
		double topLeftY = Math.min(bounds.getY(), bounds.getY() + verticalDifference);
		double width = Math.max(bounds.getWidth(), bounds.getWidth() + horizontalDifference);
		double height = Math.max(bounds.getHeight(), bounds.getHeight() + verticalDifference);
		
		bounds.setRect(topLeftX, topLeftY, width, height);
		
		return bounds;
	}
	
	@Override
	protected void setComponentBounds(Rectangle bounds) {
		this.axisLine.setComponentBounds(bounds);
	}
	
	@Override
	protected void initVisualElement() {
		this.visualElement = new AxisVisual(this);
		this.attachToContainer(this.containingElement);
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
			
//			Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
//			
//			this.setBorder(border);
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D graphs = (Graphics2D) g;
			
			Rectangle bounds = this.getBounds();
			
			graphs.setColor(this.axis.getColor());
			graphs.setStroke(new BasicStroke(this.axis.getLineThickness()));
			graphs.setFont(new Font("TimesRoman", Font.PLAIN, this.axis.fontSize));
			
			double rotationAngleInRadian = this.axis.axisLine.getAngleRadian();
			
			float xValueSpace = (float) (this.axis.fontSize * Math.cos(rotationAngleInRadian));
			float yValueSpace = (float) (this.axis.fontSize * Math.sin(rotationAngleInRadian));
			
			double x1 = Math.abs(Math.cos(rotationAngleInRadian) * bounds.getCenterX());
			double y1 = Math.abs(Math.sin(rotationAngleInRadian) * bounds.getCenterY());
			double x2 = x1 + xValueSpace;
			double y2 = y1 + yValueSpace;
			
			//graphs.rotate(rotationAngleInRadian, x1, y1);
			
			double xStepLengthInFrame = Math.cos(rotationAngleInRadian) * bounds.getWidth() / ((double) this.axis.getSteps());
			double yStepLengthInFrame = Math.sin(rotationAngleInRadian) * bounds.getHeight() / ((double) this.axis.getSteps());
			
			float stepLengthInAxis = (this.axis.getMax() - this.axis.getMin()) / ((float) this.axis.getSteps());
			float currentValue = this.axis.getMin();
			
			for (int i = 0; i < this.axis.getSteps(); i++) {
				Shape line = new Line2D.Double(x1, y1, x2, y2);
				graphs.draw(line);
				if (this.axis.showValues) {
					graphs.drawString(String.valueOf(currentValue), (float) x2 + xValueSpace, (float) y2 + yValueSpace);
					currentValue = currentValue + stepLengthInAxis;
					System.out.println(x2 + xValueSpace + ", " + y2 + yValueSpace);
				}
				x1 = x1 + xStepLengthInFrame;
				y1 = y1 + yStepLengthInFrame;
				x2 = x2 + xStepLengthInFrame;
				y2 = y2 + yStepLengthInFrame;
			}

		}
	}
}

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
	private boolean showValuesUnderAxis = true;
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
	
	public void showValuesUnderAxis() {
		this.showValuesUnderAxis = true;
		this.visualElement.repaint();
	}
	
	public void showValuesAboveAxis() {
		this.showValuesUnderAxis = false;
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
		this.visualElement.repaint();
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
	
	public PositionInFrame getLineStart() {
		return this.axisLine.getStartInFrame();
	}
	
	public PositionInFrame getLineEnd() {
		return this.axisLine.getEndInFrame();
	}
	
	@Override
	protected Rectangle getFrameBounds() {
		Rectangle bounds = this.axisLine.getFrameBounds();
		
		double lineAngleRadian = this.axisLine.getAngleRadian();
		
		// By how much the axis' visual part will be larger than that of this.axisLine
		double absValOfDifference = this.fontSize * 4;
		
		// Subtract these from the coordinates to get this.axisLine from the edge to center
		double centeringX = absValOfDifference / 2d;
		double centeringY = absValOfDifference / 2d;
		
		double topLeftX = bounds.getX() - centeringX - this.fontSize;
		double topLeftY = bounds.getY() - centeringY - this.fontSize;
		double width = bounds.getWidth() + absValOfDifference * Math.abs(Math.sin(lineAngleRadian)) + this.fontSize;
		double height = bounds.getHeight() + absValOfDifference * Math.abs(Math.cos(lineAngleRadian)) + this.fontSize;
		
		bounds.setRect(topLeftX, topLeftY, width, height);
		
		return bounds;
	}
	
	@Override
	protected void setComponentBounds(Rectangle bounds) {
//		this.axisLine.setComponentBounds(bounds);
//		this.visualElement.repaint();
	}
	
	@Override
	protected void initVisualElement() {
		this.visualElement = new AxisVisual(this);
		this.attachToContainer(this.containingElement);
	}
	
	@Override
	public void show() {
		super.show();
		this.showValues();
	}
	
	@Override
	public void hide() {
		super.hide();
		this.hideValues();
	}
	
	protected class AxisVisual extends JLabel {
		/**
		 * Generated serial version ID.
		 */
		private static final long serialVersionUID = -5939793188795940048L;
		private DiagramAxis axis;
		
		protected AxisVisual(DiagramAxis axis) {
			this.axis = axis;
//			this.setBounds(new Rectangle(0,0,1000,1000));
			this.setBounds(this.axis.getFrameBounds());
//			Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
//			
//			this.setBorder(border);
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D graphs = (Graphics2D) g;
			this.setBounds(this.axis.getFrameBounds());
			Rectangle bounds = this.getBounds();
			
			int fontSize = this.axis.fontSize;
			
			graphs.setColor(this.axis.getColor());
			graphs.setStroke(new BasicStroke(this.axis.getLineThickness()));
			graphs.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
			
			double rotationAngleInRadian = this.axis.axisLine.getAngleRadian();
			
			double indicatorLineLength = fontSize;
			
			double axisLineStartX = this.axis.axisLine.getStartInFrame().getXPos() - bounds.getMinX();
			double axisLineStartY = this.axis.axisLine.getStartInFrame().getYPos() - bounds.getMinY();
			
			double x1 = axisLineStartX;
			double y1 = axisLineStartY - indicatorLineLength;
			double y2 = axisLineStartY + indicatorLineLength;
			
			graphs.rotate(rotationAngleInRadian, axisLineStartX, axisLineStartY);
			
			double xStepLengthInFrame = this.axis.axisLine.calculateLength() / ((double) this.axis.getSteps());
			
			float stepLengthInAxis = (this.axis.getMax() - this.axis.getMin()) / ((float) this.axis.getSteps());
			float currentValue = this.axis.getMin() + stepLengthInAxis;
			
			float stringY = this.getStringYPos(y1, y2, fontSize);
			
			x1 = x1 + xStepLengthInFrame;
			
			for (int i = 1; i < this.axis.getSteps(); i++) {
				Shape line = new Line2D.Double(x1, y1, x1, y2);
				graphs.draw(line);
				if (this.axis.showValues) {
					graphs.drawString(String.valueOf(currentValue), (float) x1 - fontSize, stringY);
					currentValue = currentValue + stepLengthInAxis;
//					System.out.println(x2 + xValueSpace + ", " + y2 + yValueSpace);
				}
				x1 = x1 + xStepLengthInFrame;
			}
		}
		
		private float getStringYPos(double indicatorLineY1, double indicatorLineY2, int fontSize) {
			float stringY;
			
			if (this.axis.showValuesUnderAxis) {
				stringY = (float) (indicatorLineY2 + fontSize);
			} else {
				stringY = (float) (indicatorLineY1 - fontSize / 2d);
			}
			
			return stringY;
		}
		
	}
}

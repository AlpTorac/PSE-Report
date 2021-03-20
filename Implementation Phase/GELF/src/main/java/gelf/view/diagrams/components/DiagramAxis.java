package gelf.view.diagrams.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Line2D;

import javax.swing.JLabel;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.SettingsProvider;

/**
 * The class that represents an axis used in {@link gelf.view.diagrams.type.Diagram Diagram}.
 * <p>
 * An axis has a minimum, a maximum value and the amount of {@link #steps} it has, which
 * decides how many equal sized partitions the axis will have. These axis partitions are
 * indicated via short, to the axis perpendicular indicator lines (see {@link AxisVisual#drawStepDisplay drawStepDisplay}). 
 * @author Alp Torac Genc
 */
public abstract class DiagramAxis extends DiagramComponent {
	private static SettingsProvider settingsProvider = SettingsProvider.getInstance();

	private static int DEFAULT_FONT_SIZE = settingsProvider.getAxisValueFontSize();
	private static String DEFAULT_FONT_TYPE = settingsProvider.getAxisFontType();
	private static int DEFAULT_FONT_STYLE = Font.PLAIN;
	
	private int fontSize = DEFAULT_FONT_SIZE;
	private float min;
	private float max;
	/**
	 * The amount of partitions the axis will have.
	 */
	private int steps;
	/**
	 * Whether the {@link #stepDisplays} will be displayed.
	 */
	private boolean showValues;
	/**
	 * Whether the {@link #stepDisplays} will be displayed underneath the axis (for x-axes)
	 * or right side of the axis (for y-axes).
	 */
	private boolean showValuesUnderAxis;
	/**
	 * The line part of the axis.
	 */
	protected DiagramLine axisLine;
	/**
	 * The text, which will be displayed for {@link #steps}.
	 */
	private String[] stepDisplays;
	/**
	 * Creates an axis with {@link #stepDisplays} generated by {@link #setStepDisplays}.
	 */
	protected DiagramAxis(DiagramLine axisLine, float min, float max, int steps) {
		super(axisLine.getColor(), SettingsProvider.getInstance().getDiagramAxisLayer());

		this.min = min;
		this.max = max;
		this.steps = steps;
		this.axisLine = axisLine;
		this.initVisualElement();
	}
	/**
	 * Creates an axis with the given {@link #stepDisplays}.
	 */
	protected DiagramAxis(DiagramLine axisLine, float min, float max, int steps, String[] stepDisplays) {
		super(axisLine.getColor(), SettingsProvider.getInstance().getDiagramAxisLayer());

		this.min = min;
		this.max = max;
		this.steps = steps;
		this.axisLine = axisLine;
		
		if (stepDisplays != null) {
			this.stepDisplays = this.fillNullsWithEmptyString(stepDisplays, this.steps);
		}
		
		this.initVisualElement();
	}
	/**
	 * Adapts the stepDisplays parameter given in {@link #DiagramAxis(DiagramLine, float, float, int, String[]) DiagramAxis},
	 * if it is too short or contains null values.
	 * @return Adapted {@link #stepDisplays}
	 */
	private String[] fillNullsWithEmptyString(String[] stepDisplays, int steps) {
		String[] result;
		int displaysNewLength = steps + 1;
		
		result = new String[displaysNewLength];
		result[0] = "";
		
		int i = 1;
		for (; i < stepDisplays.length; i++) {
			result[i] = stepDisplays[i - 1];
		}
		if (stepDisplays.length < displaysNewLength) {
			result[i] = stepDisplays[stepDisplays.length - 1];
			i++;
			for (; i < result.length; i++) {
				result[i] = "";
			}
		}
		
		return result;
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
	
	public int getFontSize() {
		return this.fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
		this.visualElement.repaint();
	}

	public void showValues() {
		this.showValues = true;
		this.visualElement.repaint();
	}
	
	public void setShowValuesUnderAxis(boolean showValuesUnderAxis) {
		this.showValuesUnderAxis = showValuesUnderAxis;
	}
	
	public boolean areValuesUnderAxis() {
		return this.showValuesUnderAxis;
	}

	public void hideValues() {
		this.showValues = false;
		this.visualElement.repaint();
	}
	
	/**
	 * @param value a given value as a coordinate on the axis
	 * @return The {@link PositionInFrame} that corresponds to the given value's coordinate
	 * on the axis.
	 */
	public PositionInFrame valueToCoordinate(double value) {
		PositionInFrame lineStart = this.axisLine.getStartInFrame();
		PositionInFrame lineEnd = this.axisLine.getEndInFrame();
		
		//Weight of lineEnd
		double factor = ((value - this.min) / (this.max - this.min));
		
		//Weighted summation of lineStart and lineEnd coordinates
		double frameXPos = lineStart.getXPos() * (1 - factor) + lineEnd.getXPos() * factor;
		double frameYPos = lineStart.getYPos() * (1 - factor) + lineEnd.getYPos() * factor;
		
		return new PositionInFrame(frameXPos, frameYPos);
	}
	/**
	 * Sets the position of {@link #axisLine}.
	 */
	public void setLineByPos(double minValXPos, double minValYPos, double maxValXPos, double maxValYPos) {
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
		
		int lastStepDisplayLen = this.getStepDisplays().length - 1;
		
		if (lastStepDisplayLen < 4) {
			lastStepDisplayLen = 4;
		}
		
		// By how much the axis' visual part will be larger than that of this.axisLine
		double absValOfDifference = this.fontSize * lastStepDisplayLen;
		
		// Subtract these from the coordinates to get this.axisLine from the edge to center
		double centeringX = absValOfDifference / (((double) lastStepDisplayLen) / 2d) + this.fontSize;
		double centeringY = absValOfDifference / (((double) lastStepDisplayLen) / 2d) + this.fontSize;
		
		double topLeftX = bounds.getX() - centeringX;
		double topLeftY = bounds.getY() - centeringY;
		double width = bounds.getWidth() + absValOfDifference * Math.abs(Math.sin(lineAngleRadian)) + this.fontSize * lastStepDisplayLen;
		double height = bounds.getHeight() + absValOfDifference * Math.abs(Math.cos(lineAngleRadian)) + this.fontSize * lastStepDisplayLen;
		
		bounds.setRect(topLeftX, topLeftY, width, height);
		
		return bounds;
	}
	@Override
	protected void initVisualElement() {
		this.visualElement = new AxisVisual(this);
	}
	@Override
	public void show() {
		super.show();
		this.axisLine.show();
		this.showValues();
	}
	@Override
	public void hide() {
		super.hide();
		this.axisLine.hide();
		this.hideValues();
	}
	@Override
	public void attachToDiagram(IDiagram diagram) {
		super.attachToDiagram(diagram);
		this.axisLine.attachToDiagram(diagram);
	}
	@Override
	public void removeFromDiagram() {
		super.removeFromDiagram();
		this.axisLine.removeFromDiagram();
	}
	public String[] getStepDisplays() {
		if (this.stepDisplays == null) {
			this.setStepDisplays();
		}
		return this.stepDisplays.clone();
	}
	public void setStepDisplays(String[] stepDisplays) {
		if (stepDisplays.length < this.getSteps() + 1) {
			throw new IllegalArgumentException("Given step display array is too short.");
		}
		this.stepDisplays = stepDisplays;
	}
	/**
	 * Fills {@link #stepDisplays} with the String value of the values at axis steps.
	 */
	public void setStepDisplays() {
		int displayCount = this.getSteps() + 1;
		String[] stepDisplays = new String[displayCount];
		
		float stepLengthInAxis = (this.getMax() - this.getMin()) / ((float) this.getSteps());
		float currentValue = this.getMin();
		
		for (int i = 0; i < displayCount; i++) {
			stepDisplays[i] = SettingsProvider.getInstance().getRoundedValueAsString(currentValue);
			currentValue += stepLengthInAxis;
		}
		this.stepDisplays = stepDisplays;
	}
	/**
	 * The class that represents a part of the visuals of the {@link DiagramAxis}.
	 * <p>
	 * The visual part of the axis is the visual part of its line {@link DiagramAxis#axisLine axisLine}
	 * and the indicators and step displays.
	 * <p>
	 * This class is responsible for encapsulating the visuals of indicators and step displays. 
	 * @author Alp Torac Genc
	 */
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
			this.setBounds(this.axis.getFrameBounds());
			super.paintComponent(g);
			Graphics2D graphs = (Graphics2D) g;
			Rectangle bounds = this.getBounds();
			
			int fontSize = this.axis.fontSize;
			
			graphs.setColor(this.axis.getColor());
			graphs.setStroke(new BasicStroke(this.axis.getLineThickness()));
			graphs.setFont(new Font(DiagramAxis.DEFAULT_FONT_TYPE, DiagramAxis.DEFAULT_FONT_STYLE, fontSize));
			
			double rotationAngleInRadian = this.axis.axisLine.getAngleRadian();
			
			double indicatorLineLength = fontSize;
			
			double axisLineStartX = this.axis.axisLine.getStartInFrame().getXPos() - bounds.getMinX();
			double axisLineStartY = this.axis.axisLine.getStartInFrame().getYPos() - bounds.getMinY();
			
			double x1 = axisLineStartX;
			double y1 = axisLineStartY - indicatorLineLength;
			double y2 = axisLineStartY + indicatorLineLength;
			
			graphs.rotate(rotationAngleInRadian, axisLineStartX, axisLineStartY);
			
			double xStepLengthInFrame = this.axis.axisLine.calculateLength() / ((double) this.axis.getSteps());
			
			String[] displays = this.axis.getStepDisplays();
			
			for (int i = 0; i <= this.axis.getSteps(); i++) {
				this.drawStepDisplay(graphs, displays[i], fontSize, x1, y1, y2);
				x1 += xStepLengthInFrame;
			}
		}
		/**
		 * Draws the line, which indicates an axis partition (indicator), along with the {@link DiagramAxis#stepDisplays}
		 * corresponding to the line.
		 */
		private void drawStepDisplay(Graphics2D graphs, String display, int fontSize, double x1, double y1, double y2) {
			String stringToDisplay = display;
			
			if (stringToDisplay == null) {
				stringToDisplay = "";
			}
			
			Shape line = new Line2D.Double(x1, y1, x1, y2);
			graphs.draw(line);
			if (this.axis.showValues) {
				graphs.drawString(stringToDisplay, this.getStringXPos(stringToDisplay, fontSize, x1),
						this.getStringYPos(y1, y2, fontSize));
			}
		}
		/**
		 * @param indicatorLineY1 the y1 position (vertical starting position) of the indicator.
		 * @param indicatorLineY2 the y1 position (vertical position) of the indicator.
		 * @return The vertical position of the text to be printed for an indicator, based on the given positions.
		 */
		private float getStringYPos(double indicatorLineY1, double indicatorLineY2, int fontSize) {
			float stringY;
			
			if (this.axis.areValuesUnderAxis()) {
				stringY = (float) (indicatorLineY2 + fontSize);
			} else {
				stringY = (float) (indicatorLineY1 - fontSize / 2d);
			}
			
			return stringY;
		}
		/**
		 * @param x1 the x1 position (horizontal starting position) of the indicator.
		 * @return The horizontal position of the text to be drawn for a line, indicating an axis partition.
		 */
		private float getStringXPos(String stringToDisplay, int fontSize, double x1) {
			return (float) (x1 - (stringToDisplay.length() * fontSize) / 4d);
		}
	}
}

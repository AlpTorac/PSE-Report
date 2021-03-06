package gelf.view.diagrams.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.Border;

/**
 * The super class to the classes, which represent bars in a {@link gelf.view.diagrams.type.Diagram Diagram}.
 * @author Alp Torac Genc
 */
public abstract class DiagramBar extends DiagramValueDisplayComponent {
	private static final Color DEFAULT_BORDER_COLOR = Color.BLACK;
	
	private PositionIn2DDiagram topLeft;
	private PositionIn2DDiagram bottomRight;
	private Color borderColor = DEFAULT_BORDER_COLOR;
	private int borderThickness;

	protected DiagramBar(Color color, float value, PositionIn2DDiagram topLeft, PositionIn2DDiagram bottomRight,
			int borderThickness) {
		super(color, value);
		
		this.topLeft = topLeft;
		this.bottomRight = bottomRight;
		this.borderThickness = borderThickness;
		this.initHoverableVisualElement();
		this.setValue(value);
	}

	public double getHeight() {
		return Math.abs(this.getValue());
	}

	public double getWidth() {
		return (this.bottomRight.getXCoordinate() - this.topLeft.getXCoordinate());
	}

	@Override
	protected Rectangle getFrameBounds() {
		Rectangle bounds = new Rectangle();
		
		PositionInFrame topLeftInFrame = this.topLeft.toPositionInFrame();
		PositionInFrame bottomRightInFrame = this.bottomRight.toPositionInFrame();
		
		bounds.setFrameFromDiagonal(topLeftInFrame.getXPos(), topLeftInFrame.getYPos(), bottomRightInFrame.getXPos(), bottomRightInFrame.getYPos());
		
		return bounds;
	}
	public void setPositionInDiagram(double x1, double x2, double y2) {
		this.setBottomRightInDiagram(x2, y2);
		
		double y1 = (new PositionIn2DDiagram(this.getBottomRightInDiagram(), 0, this.getValue())).getYCoordinate();
		
		this.setTopLeftInDiagram(x1, y1);
		this.setComponentBounds(this.getFrameBounds());
	}
	
	private void setTopLeftInDiagram(double x1, double y1) {
		this.topLeft.setXCoordinate(x1);
		this.topLeft.setYCoordinate(y1);
		
		this.setComponentBounds(this.getFrameBounds());
	}

	private void setBottomRightInDiagram(double x2, double y2) {
		this.bottomRight.setXCoordinate(x2);
		this.bottomRight.setYCoordinate(y2);
		
		this.setComponentBounds(this.getFrameBounds());
	}

	public PositionIn2DDiagram getTopLeftInDiagram() {
		return this.topLeft;
	}

	public PositionIn2DDiagram getBottomRightInDiagram() {
		return this.bottomRight;
	}
	
	@Override
	protected void refreshValueRelevantAttributes() {
		double y1 = (new PositionIn2DDiagram(this.getBottomRightInDiagram(), 0, this.getValue())).getYCoordinate();
		
		this.setTopLeftInDiagram(this.getTopLeftInDiagram().getXCoordinate(), y1);
	}
	
	@Override
	protected void initVisualElement() {
		this.visualElement = new Bar(this);
	}

	public int getBorderThickness() {
		return this.borderThickness;
	}

	public void setBorderThickness(int borderThickness) {
		this.borderThickness = borderThickness;
		this.visualElement.repaint();
	}

	@Override
	protected String getRoundedPositionInDiagramString() {
		if (this.diagram != null) {
			float[] indices = this.diagram.cloneDiagramData().extractIndices().get(0);
			int indexPosition = this.diagram.getIndexPositionsOfComponent(this)[0] % indices.length;
			float beginIndex = (indexPosition - 1 >= 0) ? indices[indexPosition - 1] : 0;
			String result = "index1: " +
			String.valueOf(beginIndex) + " - " +
			String.valueOf(indices[indexPosition]);
			
			return result;
		} else {
			return "";
		}
	}
	
	/**
	 * The class that encapsulates the visuals of {@link DiagramBar}.
	 * @author Alp Torac Genc
	 *
	 */
	protected class Bar extends JLabel {
		
		/**
		 * Generated serial version ID.
		 */
		private static final long serialVersionUID = -45014560833219508L;
		private DiagramBar bar;
		
		protected Bar(DiagramBar bar) {
			this.bar = bar;
			
			this.setBounds(this.bar.getFrameBounds());
			this.setBackground(this.bar.getColor());
			this.setOpaque(true);
		}
		
		@Override
		protected void paintBorder(Graphics g) {
			super.paintBorder(g);
			Border border = BorderFactory.createLineBorder(borderColor, this.bar.borderThickness);
			
			this.setBorder(border);
		}
	}
}

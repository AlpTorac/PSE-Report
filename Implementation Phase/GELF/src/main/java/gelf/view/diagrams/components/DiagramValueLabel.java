package gelf.view.diagrams.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

/**
 * The super class of classes, which represent labels that are responsible for
 * displaying values in a {@link gelf.view.diagrams.type.Diagram Diagram}.
 * @author Alp Torac Genc
 *
 */
public abstract class DiagramValueLabel extends DiagramValueDisplayComponent {
	private static final Color DEFAULT_BORDER_COLOR = Color.BLACK;
	
	private PositionIn2DDiagram bottomRight;
	private PositionIn2DDiagram topLeft;
	private String caption;
	private Color borderColor = DEFAULT_BORDER_COLOR;
	private int borderThickness;

	protected DiagramValueLabel(PositionIn2DDiagram topLeft, PositionIn2DDiagram bottomRight, Color color, float value,
			int borderThickness) {
		super(color, value);

		this.bottomRight = bottomRight;
		this.topLeft = topLeft;
		this.borderThickness = borderThickness;
		this.caption = this.getRoundedString(this.getValue());
		
		this.initHoverableVisualElement();
	}

	public PositionIn2DDiagram getBottomRightInDiagram() {
		return this.bottomRight;
	}

	public void setBottomRightInDiagram(double x2, double y2) {
		this.bottomRight.setXCoordinate(x2);
		this.bottomRight.setYCoordinate(y2);
		
		this.setComponentBounds(this.getFrameBounds());
	}

	public PositionIn2DDiagram getTopLeftInDiagram() {
		return this.topLeft;
	}

	public void setTopLeftInDiagram(double x1, double y1) {
		this.topLeft.setXCoordinate(x1);
		this.topLeft.setYCoordinate(y1);
		
		this.setComponentBounds(this.getFrameBounds());
	}

	protected void refreshCaption() {
		this.visualElement.repaint();
	}

	public String getCaption() {
		return this.caption;
	}

	private void setCaption(String caption) {
		this.caption = caption;
		
		this.refreshCaption();
	}

	public int getBorderThickness() {
		return this.borderThickness;
	}

	public void setBorderThickness(int borderThickness) {
		this.borderThickness = borderThickness;
		this.visualElement.repaint();
	}
	
	@Override
	public void refreshValueRelevantAttributes() {
		this.setCaption(this.getRoundedString(this.getValue()));
	}
	
	@Override
	protected Rectangle getFrameBounds() {
		Rectangle bounds = new Rectangle();
		PositionInFrame frameTopLeft = this.topLeft.toPositionInFrame();
		PositionInFrame frameBottomRight = this.bottomRight.toPositionInFrame();
		
		bounds.setFrameFromDiagonal(frameTopLeft.getXPos(),
				frameTopLeft.getYPos(), frameBottomRight.getXPos(),
				frameBottomRight.getYPos());
		
		return bounds;
	}
	
	@Override
	protected void initVisualElement() {
		this.visualElement = new ValueLabelVisual(this);
	}
	
	@Override
	protected String getRoundedPositionInDiagramString() {
		int[] indexPositions = this.diagram.getIndexPositionsOfComponent(this);
		ArrayList<float[]> indices = this.diagram.cloneDiagramData().extractIndices();
		float beginIndex1 = (indexPositions[0] - 1 >= 0) ? indices.get(0)[indexPositions[0] - 1] : 0;
		float beginIndex2 = (indexPositions[1] - 1 >= 0) ? indices.get(1)[indexPositions[1] - 1] : 0;
		String result = "index1: " +
		String.valueOf(beginIndex1) + " - " +
		String.valueOf(indices.get(0)[indexPositions[0]]) + "\n" +
		"index2: " +
		String.valueOf(beginIndex2) + " - " +
		String.valueOf(indices.get(1)[indexPositions[1]]);
		
		return result;
	}
	/**
	 * The class that encapsulates the visuals of {@link DiagramValueLabel}.
	 * @author Alp Torac Genc
	 */
	protected class ValueLabelVisual extends JLabel {
		/**
		 * Generated serial version ID.
		 */
		private static final long serialVersionUID = -784432558268794661L;
		private DiagramValueLabel label;
		
		protected ValueLabelVisual(DiagramValueLabel label) {
			this.label = label;
			this.setBounds(this.label.getFrameBounds());
			this.setHorizontalAlignment(CENTER);
			this.setVerticalAlignment(CENTER);
			this.setForeground(this.label.borderColor);
			this.setText(this.label.getCaption());
			this.setOpaque(true);
		}
		
		@Override
		protected void paintBorder(Graphics g) {
			super.paintBorder(g);
			this.setBorder(BorderFactory.createLineBorder(this.label.borderColor, this.label.getBorderThickness()));
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			this.setForeground(borderColor);
			this.setText(this.label.getCaption());
		}
	}
}

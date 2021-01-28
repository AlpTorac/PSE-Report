package gelf.view.diagrams.components;

import java.awt.Color;

public abstract class DiagramValueLabel extends DiagramValueDisplayComponent {
	private PositionIn2DDiagram topRight;
	private PositionIn2DDiagram bottomLeft;
	private String caption;
	private Number borderThickness;
	
	protected DiagramValueLabel(PositionIn2DDiagram bottomLeft, PositionIn2DDiagram topRight, Color color, Number value, Number borderThickness) {
		super(color, value);
		
		this.topRight = topRight;
		this.bottomLeft = bottomLeft;
		this.borderThickness = borderThickness;
	}

	public PositionIn2DDiagram getTopRightInDiagram() {
		return topRight;
	}

	public void setTopRightInDiagram(Number x2, Number y2) {
		
	}

	public PositionIn2DDiagram getBottomLeftInDiagram() {
		return bottomLeft;
	}

	public void setBottomLeftInDiagram(Number x1, Number y1) {
		
	}

	protected void refreshCaption() {
		
	}
	
	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public Number getBorderThickness() {
		return borderThickness;
	}

	public void setBorderThickness(Number borderThickness) {
		this.borderThickness = borderThickness;
	}
	
}

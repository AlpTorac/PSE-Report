package gelf.view.diagrams.components;

import java.awt.Color;

public abstract class DiagramLabel extends DiagramComponent {
	private String caption;
	private PositionInFrame bottomLeft;
	private PositionInFrame topRight;
	private Number borderThickness;
	
	protected DiagramLabel(PositionInFrame bottomLeft, PositionInFrame topRight, Color color, String caption, Number borderThickness) {
		super(color);
		
		this.caption = caption;
		this.bottomLeft = bottomLeft;
		this.topRight = topRight;
		this.borderThickness = borderThickness;
	}
	
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	public PositionInFrame getBottomLeftInDiagram() {
		return bottomLeft;
	}
	public void setBottomLeftInDiagram(Number x1, Number y1) {
		
	}
	public PositionInFrame getTopRightInDiagram() {
		return topRight;
	}
	public void setTopRightInDiagram(Number x2, Number y2) {
		
	}
	public Number getBorderThickness() {
		return borderThickness;
	}
	public void setBorderThickness(Number borderThickness) {
		this.borderThickness = borderThickness;
	}
}

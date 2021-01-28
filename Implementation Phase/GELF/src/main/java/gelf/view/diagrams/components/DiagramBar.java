package gelf.view.diagrams.components;

import java.awt.Color;

public abstract class DiagramBar extends DiagramValueDisplayComponent {
	private PositionIn2DDiagram bottomLeft;
	private PositionIn2DDiagram topRight;
	private Number borderThickness;
	
	protected DiagramBar(Color color, Number value, PositionIn2DDiagram bottomLeft, PositionIn2DDiagram topRight, Number borderThickness) {
		super(color, value);
	}
	public Number getHeight() {
		return 0;
	}
	public Number getWidth() {
		return 0;
	}
	public void setBottomLeftInDiagram(Number x1, Number y1) {
		
	}
	public void setTopRightInDiagram(Number x2, Number y2) {
		
	}
	public PositionIn2DDiagram getBottomLeftInDiagram() {
		return null;
	}
	public PositionIn2DDiagram getTopRightInDiagram() {
		return null;
	}
	@Override
	public void refreshValueRelevantAttributes() {
		
	}
}

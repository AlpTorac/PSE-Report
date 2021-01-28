package gelf.view.diagrams.components;

import java.awt.Color;

public abstract class DiagramLine extends DiagramComponent {
	private PositionInFrame start;
	private PositionInFrame end;
	private Number thickness;
	
	protected DiagramLine(PositionInFrame start, PositionInFrame end, Color color, Number thickness) {
		super(color);
		
		this.start = start;
		this.end = end;
		this.thickness = thickness;
	}
	public PositionInFrame getStartInFrame() {
		return start;
	}
	public void setStartInFrame(Number x1, Number y1) {
		
	}
	public PositionInFrame getEndInFrame() {
		return end;
	}
	public void setEndInFrame(Number x2, Number y2) {
		
	}
	public Number getThickness() {
		return thickness;
	}
	public void setThickness(Number thickness) {
		this.thickness = thickness;
	}
	protected Number calculateLength() {
		return 0;
	}
}

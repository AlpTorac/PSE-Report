package gelf.view.diagrams.components;

import java.awt.Color;

public abstract class DiagramPoint extends DiagramValueDisplayComponent {
	private PositionIn2DDiagram position;
	private Number size;
	
	protected DiagramPoint(PositionIn2DDiagram position, Color color, Number value, Number size) {
		super(color, value);
		
		this.position = position;
		this.size = size;
	}

	public PositionIn2DDiagram getPositionInDiagram() {
		return position;
	}

	public void setPositionInDiagram(Number x, Number y) {
		
	}

	public Number getSize() {
		return size;
	}

	public void setSize(Number size) {
		this.size = size;
	}
	
}

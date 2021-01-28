package gelf.view.diagrams.components;

import java.awt.Color;

public abstract class DiagramValueDisplayComponent extends DiagramComponent implements Hoverable {
	private Number value;
	
	protected DiagramValueDisplayComponent(Color color, Number value) {
		super(color);
		
		this.value = value;
	}
	
	public void setValue() {
		
	}
	public Number getValue() {
		return this.value;
	}
	public abstract void refreshValueRelevantAttributes();
}

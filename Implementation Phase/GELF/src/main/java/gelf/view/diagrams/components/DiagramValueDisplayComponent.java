package gelf.view.diagrams.components;

import java.awt.Color;

public abstract class DiagramValueDisplayComponent extends DiagramComponent implements Hoverable {
	private double value;

	protected DiagramValueDisplayComponent(Color color, double value) {
		super(color);

		this.value = value;
		this.addHoverListener(this.visualElement);
	}

	public void setValue(double value) {
		this.value = value;
		
		this.refreshValueRelevantAttributes();
	}

	public double getValue() {
		return this.value;
	}

	public abstract void refreshValueRelevantAttributes();
}

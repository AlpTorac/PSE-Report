package gelf.view.diagrams.components;

import java.awt.Color;

public abstract class DiagramValueDisplayComponent extends DiagramComponent implements Hoverable {
	private float value;

	protected DiagramValueDisplayComponent(Color color, float value) {
		super(color);

		this.value = value;
		this.addHoverListener(this.visualElement);
	}

	public void setValue(float value) {
		this.value = value;
		
		this.refreshValueRelevantAttributes();
	}

	public float getValue() {
		return this.value;
	}

	public abstract void refreshValueRelevantAttributes();
}

package gelf.view.diagrams.components;

import java.awt.Color;
import java.awt.Container;

public abstract class DiagramValueDisplayComponent extends DiagramComponent implements Hoverable {
	private float value;

	protected DiagramValueDisplayComponent(Color color, float value, Container containingElement) {
		super(color, containingElement);

		this.value = value;
	}

	protected void initHoverableVisualElement() {
		this.initVisualElement();
		this.setColor(this.getColor());
		this.attachToContainer(this.containingElement);
		this.addHoverListener(this.visualElement, this.containingElement);
	}
	
	public void setValue(float value) {
		this.value = value;
		this.refreshValueRelevantAttributes();
	}

	public float getValue() {
		return this.value;
	}

	protected abstract void refreshValueRelevantAttributes();
	
	@Override
	public abstract DiagramValueDisplayComponent clone();
}

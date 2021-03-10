package gelf.view.diagrams.components;

import java.awt.Color;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.SettingsProvider;

public abstract class DiagramValueDisplayComponent extends DiagramComponent implements Hoverable {
	private float value;

	protected DiagramValueDisplayComponent(Color color, float value) {
		super(color, SettingsProvider.getInstance().getDiagramValueDisplayLayer());

		this.value = value;
	}

	protected void initHoverableVisualElement() {
		this.initVisualElement();
		this.setColor(this.getColor());
	}
	
	@Override
	public void attachToDiagram(IDiagram diagram) {
		super.attachToDiagram(diagram);
		this.addHoverListener(this, this.visualElement, this.diagram);
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
	
	protected abstract String getRoundedPositionInDiagramString();
	
	protected String getRoundedString(double number) {
		return SettingsProvider.getInstance().getRoundedValueAsString(number);
	}
	
	@Override
	public String toString() {
		return this.getRoundedPositionInDiagramString() + "\n\n" + "value: " + this.getRoundedString(this.value);
	}
}

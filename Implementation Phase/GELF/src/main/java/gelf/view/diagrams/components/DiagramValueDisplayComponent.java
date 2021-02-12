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
//		this.addHoverListener(this.visualElement, this.diagram);
	}
	
	@Override
	public void attachToDiagram(IDiagram diagram) {
		super.attachToDiagram(diagram);
		this.addHoverListener(this.visualElement, this.diagram);
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

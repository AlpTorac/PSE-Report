package gelf.view.diagrams.components;

import java.awt.Color;
import java.awt.Component;

public abstract class DiagramComponent {
	protected Component visualElement;
	private Color color;
	
	protected DiagramComponent(Color color) {
		this.color = color;
	}
	@Override
	public abstract DiagramComponent clone();
	
	protected abstract void initVisualElement();
	
	public void setColor(Color color) {
		
	}
	public Color getColor() {
		return this.color;
	}
	
	public abstract void show();
	public abstract void hide();
}

package gelf.view.diagrams.components;

import java.awt.Color;
import java.awt.Component;

public abstract class DiagramComponent {
	protected Component visualElement;
	private Color color;

	protected DiagramComponent(Color color) {
		this.color = color;
		
		this.initVisualElement();
	}

	@Override
	public abstract DiagramComponent clone();

	protected abstract void initVisualElement();

	protected abstract void setComponentBounds();
	
	public void setColor(Color color) {
		this.color = color;
		this.setComponentColor();
	}

	private void setComponentColor() {
		this.visualElement.setBackground(color);
	}

	public Color getColor() {
		return this.color;
	}

	public void show() {
		this.visualElement.setVisible(true);
	}

	public void hide() {
		this.visualElement.setVisible(false);
	}
}

package gelf.view.diagrams.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;

public abstract class DiagramComponent {
	protected Component visualElement;

	protected DiagramComponent(Color color) {
		this.initVisualElement();
		this.setColor(color);
	}

	@Override
	public abstract DiagramComponent clone();

	protected abstract void initVisualElement();

	protected void setComponentBounds(Rectangle bounds) {
		this.visualElement.setBounds(bounds);
	}
	
	protected abstract Rectangle getFrameBounds();
	
	public void setColor(Color color) {
		this.visualElement.setBackground(color);
	}

	public Color getColor() {
		return this.visualElement.getBackground();
	}

	public void show() {
		this.visualElement.setVisible(true);
	}

	public void hide() {
		this.visualElement.setVisible(false);
	}
}

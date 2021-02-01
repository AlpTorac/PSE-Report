package gelf.view.diagrams.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;

public abstract class DiagramComponent {
	protected Component visualElement;
	private Color color;

	protected DiagramComponent(Color color) {
		this.color = color;
	}

	@Override
	public abstract DiagramComponent clone();

	protected abstract void initVisualElement();

	protected void setComponentBounds(Rectangle bounds) {
		this.visualElement.setBounds(bounds);
	}
	
	protected abstract Rectangle getFrameBounds();
	
	public void setColor(Color color) {
		this.color = color;
		this.visualElement.setBackground(color);
		this.visualElement.repaint();
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

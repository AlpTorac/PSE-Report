package gelf.view.diagrams.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Rectangle;

public abstract class DiagramComponent implements HasAttachablePart {
	protected Container containingElement;
	protected Component visualElement;
	private Color color;

	protected DiagramComponent(Color color, Container containingElement) {
		this.color = color;
		this.containingElement = containingElement;
	}

	@Override
	public abstract DiagramComponent clone();

	protected abstract void initVisualElement();

	protected void setComponentBounds(Rectangle bounds) {
		this.visualElement.setBounds(bounds);
		this.visualElement.repaint();
	}
	
	protected abstract Rectangle getFrameBounds();
	
	public void setColor(Color color) {
		this.color = color;
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
	
	@Override
	public void attachToContainer(Container container) {
		if (this.containingElement != container) {
			this.removeFromContainer();
		}
		this.containingElement = container;
		this.containingElement.add(this.visualElement);
		this.setComponentBounds(this.getFrameBounds());
		this.visualElement.setIgnoreRepaint(true);
	}
	
	@Override
	public void removeFromContainer() {
		if (this.containingElement != null) {
			this.containingElement.remove(this.visualElement);
		}
	}
}

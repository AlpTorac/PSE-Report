package gelf.view.diagrams.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;

import gelf.view.diagrams.IDiagram;

public abstract class DiagramComponent implements HasAttachablePart {
	private int layer;
	protected Component visualElement;
	protected IDiagram diagram;
	
	private Color color;

	protected DiagramComponent(Color color, int layer) {
		this.color = color;
		this.layer = layer;
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
	public void attachToDiagram(IDiagram diagram) {
		if (this.diagram != null) {
			this.diagram.removeComponent(this.getVisualElement());
		}
			this.diagram = diagram;
			diagram.addComponent(this.getVisualElement(), this.layer);
			this.setComponentBounds(this.getFrameBounds());
	}
	
	@Override
	public void removeFromDiagram() {
		if (this.diagram != null) {
			this.diagram.removeComponent(this.getVisualElement());
		}
		this.diagram = null;
	}
	
	public int getLayer() {
		return layer;
	}

	public void setLayer(int layer) {
		this.layer = layer;
		if (this.diagram != null) {
			this.diagram.removeComponent(this.getVisualElement());
			this.diagram.addComponent(this.getVisualElement(), this.layer);
			this.setComponentBounds(this.getFrameBounds());
		}
	}
	
	public void incrementLayer() {
		this.setLayer(this.layer + 1);
	}
	
	public void decrementLayer() {
		this.setLayer(this.layer - 1);
	}
	
	public Component getVisualElement() {
		return this.visualElement;
	}
}

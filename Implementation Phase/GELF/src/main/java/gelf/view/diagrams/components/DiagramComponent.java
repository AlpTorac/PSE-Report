package gelf.view.diagrams.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;

import gelf.view.diagrams.IDiagram;

/**
 * The super class of the classes, which represent base {@link gelf.view.diagrams.type.Diagram Diagram}
 * components.
 * @author Alp Torac Genc
 */
public abstract class DiagramComponent implements HasAttachablePart {
	/**
	 * The depth value of {@link #visualElement}
	 * in {@link gelf.view.diagrams.type.Diagram#containingElement containingElement}.
	 * <p>
	 * This attribute is often set to a default value in {@link gelf.view.diagrams.SettingsProvider SettingsProvider}.
	 */
	private int layer;
	/**
	 * The component that encapsulates the visuals of this component.
	 */
	protected Component visualElement;
	/**
	 * The {@link gelf.view.diagrams.IDiagram IDiagram}, to which this component is attached to.
	 */
	protected IDiagram diagram;
	
	private Color color;
	/**
	 * @param color the color of this component
	 * @param layer {@link #layer}
	 */
	protected DiagramComponent(Color color, int layer) {
		this.color = color;
		this.layer = layer;
	}
	/**
	 * Note: Methods connected to {@link gelf.view.diagrams.components.DiagramComponent#diagram diagram}
	 * will not work for the clones till they are attached to a diagram.
	 */
	@Override
	public abstract DiagramComponent clone();
	/**
	 * Initializes {@link #visualElement}.
	 */
	protected abstract void initVisualElement();
	/**
	 * Sets the dimensions of {@link #visualElement}.
	 * @param bounds a given rectangle that defines dimensions of the said component.
	 */
	protected void setComponentBounds(Rectangle bounds) {
		this.visualElement.setBounds(bounds);
		this.visualElement.repaint();
	}
	/**
	 * @return Computes the dimensions of {@link #visualElement} after adjustments.
	 */
	protected abstract Rectangle getFrameBounds();
	public void setColor(Color color) {
		this.color = color;
		this.visualElement.setBackground(color);
	}
	public Color getColor() {
		return this.color;
	}
	/**
	 * Shows the visuals of this component encapsulated by {@link #visualElement}
	 */
	public void show() {
		this.visualElement.setVisible(true);
	}
	/**
	 * Hides the visuals of this component encapsulated by {@link #visualElement}
	 */
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
	/**Automatically removes and adds the component back into its {@link #diagram}.
	 * @param layer a given integer to be {@link #layer}
	 */
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

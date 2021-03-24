package gelf.view.diagrams.components;

import java.awt.Color;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.SettingsProvider;

/**
 * The super class of classes that represent base components of a {@link gelf.view.diagrams.type.Diagram Diagram},
 * which are responsible for displaying values and are not {@link DiagramAxis} themselves.
 * @author Alp Torac Genc
 */
public abstract class DiagramValueDisplayComponent extends DiagramComponent implements Hoverable {
	private float value;

	protected DiagramValueDisplayComponent(Color color, float value) {
		super(color, SettingsProvider.getInstance().getDiagramValueDisplayLayer());

		this.value = value;
	}
	/**
	 * Initializes {@link DiagramComponent#visualElement} and sets its color.
	 */
	protected void initHoverableVisualElement() {
		this.initVisualElement();
		this.setColor(this.getColor());
	}
	
	@Override
	public void attachToDiagram(IDiagram diagram) {
		super.attachToDiagram(diagram);
		this.addHoverListener(this, this.visualElement, this.diagram);
	}
	/**
	 * Sets {@link #value} and updates attributes related to it.
	 * @param value a given value
	 */
	public void setValue(float value) {
		this.value = value;
		this.refreshValueRelevantAttributes();
	}

	public float getValue() {
		return this.value;
	}
	/**
	 * Updates attributes related to {@link #value}.
	 */
	protected abstract void refreshValueRelevantAttributes();
	
	@Override
	public abstract DiagramValueDisplayComponent clone();
	/**
	 * @return A String specifying the positions (indices) of {@link #value}.
	 */
	protected abstract String getRoundedPositionInDiagramString();
	/**
	 * @param number a given number
	 * @return The String value of the rounded version of the given number.
	 */
	protected String getRoundedString(double number) {
		return SettingsProvider.getInstance().getRoundedValueAsString(number);
	}
	
	/**
	 * Provides information about {@link #value} and its associated indices.
	 * @return Information about the component.
	 */
	@Override
	public String toString() {
		return this.getRoundedPositionInDiagramString() + "\n\n" + "value: " + this.value;
	}
}

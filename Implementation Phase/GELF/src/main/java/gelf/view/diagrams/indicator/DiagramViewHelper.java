package gelf.view.diagrams.indicator;

import java.util.ArrayList;
import java.util.Collection;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.IDiagramViewHelper;

/**
 * The super class of classes, which represent a component (or group of components)
 * that are not actually a part of the {@link gelf.view.diagrams.type.Diagram Diagram}
 * but help visualize the data in the diagram better or help display statistics on
 * the diagram.
 * @author Alp Torac Genc
 */
public abstract class DiagramViewHelper implements IDiagramViewHelper {
	/**
	 * {@link gelf.view.diagrams.components.DiagramComponent#layer layer}
	 */
	private int layer;
	/**
	 * A collection of the components that belong to a diagram view helper.
	 */
	private Collection<ViewHelperComponent> helperComponents;
	/**
	 * The unique identifier associated with the diagram view helper.
	 */
	private IndicatorIdentifier id;
	/**
	 * The {@link gelf.view.diagrams.IDiagram IDiagram} this diagram view helper is attached to.
	 */
	protected IDiagram diagram;
	
	protected DiagramViewHelper(IDiagram diagram, int layer,
			IndicatorIdentifier id) {
		this.diagram = diagram;
		this.layer = layer;
		this.id = id;
		
		this.helperComponents = new ArrayList<ViewHelperComponent>();
	}
	/**
	 * Attaches the diagram view helper to {@link #diagram} and refreshes the visuals
	 * of it.
	 */
	public void attachToDiagram() {
		this.diagram.addDiagramViewHelper(this);
		this.attachToDiagramContainingElement();
		this.diagram.refresh();
	}
	/**
	 * Attaches every component in {@link #helperComponents} to {@link #diagram}.
	 */
	private void attachToDiagramContainingElement() {
		for (ViewHelperComponent vhc : this.helperComponents) {
			vhc.attachToDiagram(diagram);
		}
	}
	
	public int getLayerNumber() {
		return this.layer;
	}
	/**
	 * Detaches every component in {@link #helperComponents} from {@link #diagram},
	 * removes the diagram view helper from the diagram and sets {@link #diagram} to null.
	 */
	public void remove() {
		for (ViewHelperComponent vhc : this.helperComponents) {
			vhc.removeFromDiagram();
		}
		this.diagram.removeDiagramViewHelper(this.getID());
		this.diagram = null;
	}
	/**
	 * Displays every component in {@link #helperComponents}.
	 */
	public void show() {
		for (ViewHelperComponent vhc : this.helperComponents) {
			vhc.show();
		}
	}
	/**
	 * Hides visuals of every component in {@link #helperComponents}.
	 */
	public void hide() {
		for (ViewHelperComponent vhc : this.helperComponents) {
			vhc.hide();
		}
	}
	/**
	 * @param vhc a component to be added to {@link #helperComponents}
	 * @return True, if successfully added. Otherwise, false.
	 */
	public boolean addViewHelperComponent(ViewHelperComponent vhc) {
		return this.helperComponents.add(vhc);
	}
	/**
	 * @param vhc a component to be removed from {@link #helperComponents}
	 * @return True, if successfully removed. Otherwise, false.
	 */
	public boolean removeViewHelperComponent(ViewHelperComponent vhc) {
		return this.helperComponents.remove(vhc);
	}
	/** Removes every component in {@link #helperComponents}
	 * @return True, if successfully cleaned. Otherwise, false.
	 */
	public boolean clearViewHelperComponents() {
		this.helperComponents.clear();
		return (this.helperComponents.size() == 0);
	}
	
	public IndicatorIdentifier getID() {
		return this.id;
	}
	/**
	 * Creates components to be added to {@link #helperComponents} and attaches
	 * the diagram view helper to {@link #diagram}.
	 */
	protected void initViewHelperComponents() {
		this.generateHelperComponents();
		this.attachToDiagram();
	}
	/**
	 * Creates components to be added to {@link #helperComponents}.
	 */
	protected abstract void generateHelperComponents();
}

package gelf.view.diagrams.indicator;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.components.DiagramComponent;

/** The class that wraps a component, which will be a part of a {@link DiagramViewHelper}.
 * @author Alp Torac Genc
 */
public class ViewHelperComponent {
	private DiagramComponent dc;
	private DiagramViewHelper dvh;
	private IDiagram diagram;
	
	protected ViewHelperComponent(DiagramComponent dc, DiagramViewHelper dvh) {
		this.dc = dc;
		this.dvh = dvh;
	}
	/**
	 * Displays the visuals of {@link #dc}.
	 */
	public void show() {
		this.dc.show();
	}
	/**
	 * Hides the visuals of {@link #dc}.
	 */
	public void hide() {
		this.dc.hide();
	}
	/**
	 * @param diagram a given diagram to be attached to.
	 */
	public void attachToDiagram(IDiagram diagram) {
		this.removeFromDiagram();
		this.diagram = diagram;
		this.diagram.addComponent(this.dc.getVisualElement(), this.dvh.getLayerNumber());
	}
	/**
	 * Removes {@link #dc} from {@link #diagram}.
	 */
	public void removeFromDiagram() {
		if (this.diagram != null) {
			this.diagram.removeComponent(this.dc.getVisualElement());
			this.diagram = null;
		}
	}
}

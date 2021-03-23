package gelf.view.diagrams.components;

import gelf.view.diagrams.IDiagram;
/**
 * An interface implemented by classes that have attributes, which encapsulate
 * visuals of that class and which need to be added/removed from a
 * {@link gelf.view.diagrams.type.Diagram Diagram} for their visuals to be
 * visible and active.
 * @author Alp Torac Genc
 */
public interface HasAttachablePart {
	/**
	 * Adds the attributes of the implementing class to the given diagram.
	 * @param diagram a given {@link gelf.view.diagrams.IDiagram IDiagram}
	 */
	public void attachToDiagram(IDiagram diagram);
	/**
	 * Removes the attributes of the implementing class from the diagram it
	 * was attached to (via {@link #attachToDiagram}).
	 */
	public void removeFromDiagram();
}

package gelf.view.diagrams;

/**
 * An interface implemented by containers, which have components that have to be
 * highlighted with the help of listeners in {@link gelf.view.diagrams.components.Hoverable Hoverable}.
 * @author Alp Torac Genc
 *
 */
public interface IHighlightableElementContainer {
	/**
	 * Implement this method to receive index positions of a {@link gelf.view.diagrams.components.DiagramValueDisplayComponent DiagramValueDisplayComponent}'s
	 * {@link gelf.view.diagrams.components.DiagramValueDisplayComponent#value value}
	 * @param indexPositions an array of indices to receive.
	 */
	public void receiveHoveredElementInfo(int[] indexPositions);
	/**
	 * Implement this method to be notified, when the highlighting should stop.
	 */
	public void stopHighlighting();
}

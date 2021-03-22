package gelf.view.diagrams;

import gelf.view.diagrams.overlayer.DiagramOverlayStrategy;
/**
 * An interface implemented by classes, which are responsible for overlaying diagrams.
 * @author Alp Torac Genc
 *
 */
public interface IDiagramOverlayer {
	/**
	 * @return The diagram that is the result of overlaying the diagrams stored in the overlayer.
	 */
	public IDiagram overlay();
	/**
	 * Sets the overlay strategy of the overlayer to the given one.
	 * @param overlayStrategy a given overlay strategy.
	 */
	public void setOverlayStrategy(DiagramOverlayStrategy overlayStrategy);
}

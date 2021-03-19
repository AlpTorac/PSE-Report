package gelf.view.diagrams;

import gelf.view.diagrams.overlayer.DiagramOverlayStrategy;
/**
 * An interface implemented by classes, which are responsible for overlaying diagrams.
 * @author Alp Torac Genc
 *
 */
public interface IDiagramOverlayer {
	/**
	 * @param index a given index.
	 * @return The diagram at the given index.
	 */
	public IDiagram getDiagram(int index);
	/**
	 * Sets the diagram at the given index with the given diagram.
	 * @param index a given index.
	 * @param diagram a given diagram.
	 */
	public void setDiagram(int index, IDiagram diagram);
	/**
	 * Adds the given diagram to the overlayer.
	 * @param diagram a given diagram.
	 * @return True, if the diagram has been added successfully. Otherwise, false.
	 */
	public boolean addDiagram(IDiagram diagram);
	/**
	 * Removes the diagram at the given index.
	 * @param index the index of the diagram to be removed.
	 * @return True, if the diagram has been removed successfully. Otherwise, false.
	 */
	public boolean removeDiagram(int index);
	/**
	 * @param indices an array of indices.
	 * @return The diagram that is the result of overlaying the diagrams at the given indices.
	 */
	public IDiagram overlay(int[] indices);
	/**
	 * @param diagrams an array of diagrams.
	 * @return The diagram that is the result of overlaying the given diagrams.
	 */
	public IDiagram overlay(IDiagram[] diagrams);
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

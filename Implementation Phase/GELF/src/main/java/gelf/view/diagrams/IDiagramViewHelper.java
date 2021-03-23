package gelf.view.diagrams;

/**
 * An interface implemented by classes, which decorate their {@link IDiagram}.
 * @author Alp Torac Genc
 *
 */
public interface IDiagramViewHelper {
	/**
	 * Removes the view helper from the {@link IDiagram} it is attached to.
	 */
	public void remove();
	/**
	 * Shows the visuals of the view helper.
	 */
	public void show();
	/**
	 * Hides the visuals of the view helper.
	 */
	public void hide();
}

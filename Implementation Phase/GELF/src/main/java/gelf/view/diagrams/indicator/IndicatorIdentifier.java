package gelf.view.diagrams.indicator;

/**
 * An enum with unique identifiers for each defined concrete {@link DiagramViewHelper}.
 * @author Alp Torac Genc
 */
public enum IndicatorIdentifier {
	/**
	 * The unique identifier for the {@link DiagramViewHelper} responsible for displaying
	 * the minimum value in an {@link gelf.view.diagrams.IDiagram IDiagram}.
	 */
	MIN,
	/**
	 * The unique identifier for the {@link DiagramViewHelper} responsible for displaying
	 * the maximum value in an {@link gelf.view.diagrams.IDiagram IDiagram}.
	 */
	MAX,
	/**
	 * The unique identifier for the {@link DiagramViewHelper} responsible for displaying
	 * the average value in an {@link gelf.view.diagrams.IDiagram IDiagram}.
	 */
	AVG,
	/**
	 * The unique identifier for the {@link DiagramViewHelper} responsible for displaying
	 * the median value in an {@link gelf.view.diagrams.IDiagram IDiagram}.
	 */
	MED
}

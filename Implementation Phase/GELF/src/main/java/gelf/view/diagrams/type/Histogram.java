package gelf.view.diagrams.type;

import gelf.view.diagrams.components.DiagramAxis;
import gelf.view.diagrams.components.DiagramComponent;
import gelf.view.diagrams.components.DiagramValueDisplayComponent;
import gelf.view.diagrams.data.DiagramData;

/**
 * The class that represents a histogram.
 * <p>
 * A histogram's {@link Diagram#data data} has one array of indices and
 * one array of values on its own.
 * @author Alp Torac Genc
 */
public class Histogram extends Diagram {
	public Histogram(DiagramData data, DiagramAxis[] axes,
			DiagramValueDisplayComponent[] valueDisplayComponents,
			DiagramComponent[] nonValueDisplayComponents) {
		super(data, axes, valueDisplayComponents, nonValueDisplayComponents);
	}
}

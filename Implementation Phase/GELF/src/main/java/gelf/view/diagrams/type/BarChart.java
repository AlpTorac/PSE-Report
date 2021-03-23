package gelf.view.diagrams.type;

import gelf.view.diagrams.components.DiagramAxis;
import gelf.view.diagrams.components.DiagramComponent;
import gelf.view.diagrams.components.DiagramValueDisplayComponent;
import gelf.view.diagrams.data.DiagramData;

/**
 * The class that represents a bar chart.
 * <p>
 * A bar chart's {@link Diagram#data data} has no indices and only one
 * array of values on its own.
 * @author Alp Torac Genc
 */
public class BarChart extends Diagram {
	public BarChart(DiagramData data, DiagramAxis[] axes,
			DiagramValueDisplayComponent[] valueDisplayComponents,
			DiagramComponent[] nonValueDisplayComponents) {
		super(data, axes, valueDisplayComponents, nonValueDisplayComponents);
	}
}

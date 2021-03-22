package gelf.view.diagrams.type;

import gelf.view.diagrams.components.DiagramAxis;
import gelf.view.diagrams.components.DiagramComponent;
import gelf.view.diagrams.components.DiagramValueDisplayComponent;
import gelf.view.diagrams.data.DiagramData;

/**
 * The class that represents a bar chart.
 * @author Alp Torac Genc
 */
public class BarChart extends Diagram {
	public BarChart(DiagramData data, DiagramAxis[] axes,
			DiagramValueDisplayComponent[] valueDisplayComponents,
			DiagramComponent[] nonValueDisplayComponents) {
		super(data, axes, valueDisplayComponents, nonValueDisplayComponents);
	}
}

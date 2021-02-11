package gelf.view.diagrams.type;

import java.awt.Container;

import gelf.view.diagrams.components.DiagramAxis;
import gelf.view.diagrams.components.DiagramComponent;
import gelf.view.diagrams.components.DiagramValueDisplayComponent;
import gelf.view.diagrams.data.DiagramData;

public class BarChart extends Diagram {
	public BarChart(DiagramData data, DiagramAxis[] axes,
			DiagramValueDisplayComponent[] valueDisplayComponents,
			DiagramComponent[] nonValueDisplayComponents,
			Container containingElement) {
		super(data, axes, valueDisplayComponents, nonValueDisplayComponents,
				containingElement);
	}
}

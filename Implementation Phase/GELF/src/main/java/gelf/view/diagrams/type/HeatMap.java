package gelf.view.diagrams.type;

import gelf.view.diagrams.components.DiagramAxis;
import gelf.view.diagrams.components.DiagramComponent;
import gelf.view.diagrams.components.DiagramValueDisplayComponent;
import gelf.view.diagrams.data.DiagramData;

public class HeatMap extends Diagram {
	public HeatMap(DiagramData data, DiagramAxis[] axes,
			DiagramValueDisplayComponent[] valueDisplayComponents,
			DiagramComponent[] nonValueDisplayComponents) {
		super(data, axes, valueDisplayComponents, nonValueDisplayComponents);
	}
}

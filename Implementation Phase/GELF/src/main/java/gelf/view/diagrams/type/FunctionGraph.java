package gelf.view.diagrams.type;

import java.awt.Container;

import gelf.view.diagrams.components.DiagramAxis;
import gelf.view.diagrams.components.DiagramComponent;
import gelf.view.diagrams.components.DiagramValueDisplayComponent;
import gelf.view.diagrams.data.DiagramData;

public class FunctionGraph extends Diagram {
	public FunctionGraph(DiagramData data, DiagramAxis[] axes,
			DiagramValueDisplayComponent[] valueDisplayComponents,
			DiagramComponent[] nonValueDisplayComponents,
			Container containingElement) {
		super(data, axes, valueDisplayComponents, nonValueDisplayComponents,
				containingElement);
	}
}
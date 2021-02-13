package gelf.view.diagrams.builder;

import java.awt.Container;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.components.DiagramAxis;
import gelf.view.diagrams.components.DiagramComponent;
import gelf.view.diagrams.components.DiagramValueDisplayComponent;
import gelf.view.diagrams.type.HeatMap;

public class HeatMapBuilder extends DiagramBuilder implements IHeatMapBuilder {

	public HeatMapBuilder(Container container) {
		super(container);
	}

	protected IDiagram makeDiagram(DiagramAxis[] axes, DiagramValueDisplayComponent[] valueDisplayComponents,
			DiagramComponent[] diagramSpecificComponent) {
		return new HeatMap(this.getDiagramData(), axes, valueDisplayComponents, diagramSpecificComponent);
	}

	@Override
	protected float getXAxisMaxValue() {
		return this.getDiagramData().getMaximumIndexAt(0);
	}

	@Override
	protected float getYAxisMaxValue() {
		return this.getDiagramData().getMaximumIndexAt(1);
	}
}

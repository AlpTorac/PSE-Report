package gelf.view.diagrams.builder;

import java.awt.Container;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.components.DiagramAxis;
import gelf.view.diagrams.components.DiagramComponent;
import gelf.view.diagrams.components.DiagramValueDisplayComponent;
import gelf.view.diagrams.type.BarChart;

public class BarChartBuilder extends DiagramBuilder implements IBarChartBuilder {
	public BarChartBuilder(Container container) {
		super(container);
	}

	protected IDiagram makeDiagram(DiagramAxis[] axes, DiagramValueDisplayComponent[] valueDisplayComponents,
			DiagramComponent[] diagramSpecificComponent) {
		return new BarChart(this.getDiagramData(), axes, valueDisplayComponents, diagramSpecificComponent);
	}

	@Override
	protected float getXAxisMaxValue() {
		return this.getDiagramData().extractValues().get(0).length;
	}

	@Override
	protected float getYAxisMaxValue() {
		return this.getDiagramData().getMaximumValue();
	}

	@Override
	protected int getXAxisSteps() {
		return (int) this.getXAxisMaxValue();
	}

	@Override
	protected int getYAxisSteps() {
		return this.getDiagramData().extractValues().get(0).length;
	}
}

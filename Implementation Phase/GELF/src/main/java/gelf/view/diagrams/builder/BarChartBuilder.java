package gelf.view.diagrams.builder;

import java.awt.Container;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.SettingsProvider;
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
		return this.getDiagramData().extractValues().get(0).length + 1;
	}

	@Override
	protected float getYAxisMaxValue() {
		float maxVal = this.getDiagramData().getMaximumValue();
		
		if (maxVal <= 0) {
			return 0;
		} else {
			return maxVal;
		}
	}

	@Override
	protected int getXAxisSteps() {
		return (int) this.getXAxisMaxValue();
	}

	@Override
	protected int getYAxisSteps() {
		return SettingsProvider.getInstance().getStepsInYAxis();
	}
	
	@Override
	protected String[] getXAxisDescriptions() {
		if (this.getDiagramData().extractValueDescriptions() != null) {
			return this.getDiagramData().extractValueDescriptions().get(0);
		} else {
			return null;
		}
	}

	@Override
	protected float getYAxisMinValue() {
		float minVal = this.getDiagramData().getMinimumValue();
		
		if (minVal >= 0) {
			return 0;
		} else {
			return minVal;
		}
	}
}

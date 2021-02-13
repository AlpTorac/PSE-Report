package gelf.view.diagrams.builder;

import java.awt.Color;
import java.awt.Container;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.components.DiagramAxis;
import gelf.view.diagrams.components.DiagramComponent;
import gelf.view.diagrams.components.DiagramValueDisplayComponent;
import gelf.view.diagrams.components.PositionIn2DDiagram;
import gelf.view.diagrams.type.BarChart;

public class BarChartBuilder extends DiagramBuilder {

	public BarChartBuilder(Container container) {
		super(container);
	}

	@Override
	protected DiagramValueDisplayComponent[] buildValueDisplayComponents(DiagramAxis[] axes,
			DiagramComponent[] diagramSpecificComponent) {
		float[] values = this.data.extractValues().get(0);
		int dvdcCount = values.length;
		
		DiagramValueDisplayComponent[] dvdc = new DiagramValueDisplayComponent[dvdcCount];
		
		Color barColor = settingsProvider.getValueDisplayComponentColorAt(0);
		int thickness = settingsProvider.getBarBorderThickness();
		
		for (int i = 0; i < dvdc.length; i++) {
			PositionIn2DDiagram topLeft = factory.makePositionInDiagram(axes[0], i, axes[1], values[i]);
			PositionIn2DDiagram bottomRight = factory.makePositionInDiagram(axes[0], i + 1, axes[1], 0);
			
			dvdc[i] = factory.createBarChartBar(barColor, values[i], topLeft, bottomRight, thickness);
		}
		
		return dvdc;
	}

	@Override
	public IDiagram buildDiagram() {
		DiagramAxis[] axes = this.buildAxes();
		DiagramValueDisplayComponent[] dvdc = this.buildValueDisplayComponents(axes, null);
		return new BarChart(this.data, axes, dvdc, null);
	}

	@Override
	protected float getXAxisMaxValue() {
		return this.data.extractValues().get(0).length;
	}

	@Override
	protected float getYAxisMaxValue() {
		return this.data.getMaximumValue();
	}

	@Override
	protected int getXAxisSteps() {
		return (int) this.getXAxisMaxValue();
	}

	@Override
	protected int getYAxisSteps() {
		return this.data.extractValues().get(0).length;
	}

}

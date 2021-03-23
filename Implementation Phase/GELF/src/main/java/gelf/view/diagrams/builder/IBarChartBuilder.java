package gelf.view.diagrams.builder;

import java.awt.Color;

import gelf.view.diagrams.SettingsProvider;
import gelf.view.diagrams.components.DiagramAxis;
import gelf.view.diagrams.components.DiagramComponent;
import gelf.view.diagrams.components.DiagramValueDisplayComponent;
import gelf.view.diagrams.components.PositionIn2DDiagram;
import gelf.view.diagrams.data.DiagramData;

/**
 * An interface implemented by the classes responsible for building {@link gelf.view.diagrams.type.BarChart BarChart}.
 * @author Alp Torac Genc
 *
 */
public interface IBarChartBuilder extends IDiagramBuilder {
	/**
	 * {@link IDiagramBuilder#buildValueDisplayComponentsForOneDiagram buildValueDisplayComponentsForOneDiagram}
	 */
	@Override
	public default DiagramValueDisplayComponent[] buildValueDisplayComponentsForOneDiagram(DiagramData data, int orderInSameDiagram, DiagramAxis[] axes,
			DiagramComponent[] diagramSpecificComponent) {
		float[] values = data.extractValues().get(0);
		int dvdcCount = values.length;
		
		DiagramValueDisplayComponent[] dvdc = new DiagramValueDisplayComponent[dvdcCount];
		
		Color barColor = this.getColorForDiagram(orderInSameDiagram);
		int thickness = this.getSettingsProvider().getBarBorderThickness();
		
		double halfBarWidthInSteps = this.getBarWidth() / 2d;
		
		for (int i = 1; i <= dvdc.length; i++) {
			PositionIn2DDiagram topLeft = this.getDiagramComponentFactory().makePositionInDiagram(axes[0], i - halfBarWidthInSteps, axes[1], values[i - 1]);
			PositionIn2DDiagram bottomRight = this.getDiagramComponentFactory().makePositionInDiagram(axes[0], i + halfBarWidthInSteps, axes[1], 0);
			
			dvdc[i - 1] = this.getDiagramComponentFactory().createBarChartBar(barColor, values[i - 1], topLeft, bottomRight, thickness);
		}
		
		return dvdc;
	}
	/**
	 * {@link IDiagramBuilder#getXAxisSteps getXAxisSteps}
	 */
	@Override
	public default int getXAxisSteps() {
		return (int) this.getXAxisMaxValue();
	}
	/**
	 * {@link IDiagramBuilder#getYAxisSteps getYAxisSteps}
	 */
	@Override
	public default int getYAxisSteps() {
		return SettingsProvider.getInstance().getStepsInYAxis();
	}
	/**
	 * @return The bar width of a {@link gelf.view.diagrams.components.BarChartBar BarChartBar}.
	 */
	public default double getBarWidth() {
		return this.getSettingsProvider().getBarChartBarWidthInSteps();
	}
	/**
	 * {@link IDiagramBuilder#buildDiagramSpecificComponentForOneDiagram buildDiagramSpecificComponentForOneDiagram}
	 */
	@Override
	public default DiagramComponent[] buildDiagramSpecificComponentForOneDiagram(DiagramData data) {
		return null;
	}
}

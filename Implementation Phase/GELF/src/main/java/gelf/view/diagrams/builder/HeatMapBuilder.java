package gelf.view.diagrams.builder;

import java.awt.Container;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.components.DiagramAxis;
import gelf.view.diagrams.components.DiagramComponent;
import gelf.view.diagrams.components.DiagramValueDisplayComponent;
import gelf.view.diagrams.type.HeatMap;

/**
 * This class is the concrete implementation of {@link DiagramBuilder} and {@link IHeatMapBuilder}.
 * It is both a part of the builder pattern (builder to {@link gelf.view.diagrams.DiagramDirector DiagramDirector})
 * and the template method pattern (template method {@link DiagramBuilder#buildDiagram buildDiagram}).
 * <p>
 * This class overrides the methods in {@link IDiagramBuilder}, which need to be different to
 * build {@link gelf.view.diagrams.type.HeatMap HeatMap} instances.
 * @author Alp Torac Genc
 *
 */
public class HeatMapBuilder extends DiagramBuilder implements IHeatMapBuilder {

	public HeatMapBuilder(Container container) {
		super(container);
	}
	/**
	 * {@link IDiagramBuilder#makeDiagram makeDiagram}
	 */
	protected IDiagram makeDiagram(DiagramAxis[] axes, DiagramValueDisplayComponent[] valueDisplayComponents,
			DiagramComponent[] diagramSpecificComponent) {
		return new HeatMap(this.getDiagramData(), axes, valueDisplayComponents, diagramSpecificComponent);
	}
	/**
	 * {@link IDiagramBuilder#getXAxisMaxValue getXAxisMaxValue}
	 */
	@Override
	public float getXAxisMaxValue() {
		return this.getDiagramData().getMaximumIndexAt(0);
	}
	/**
	 * {@link IDiagramBuilder#getYAxisMaxValue getYAxisMaxValue}
	 */
	@Override
	public float getYAxisMaxValue() {
		return this.getDiagramData().getMaximumIndexAt(1);
	}
	/**
	 * {@link IDiagramBuilder#getYAxisMinValue getYAxisMinValue}
	 */
	@Override
	public float getYAxisMinValue() {
		return 0;
	}
}

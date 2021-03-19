package gelf.view.diagrams.builder;

import java.awt.Container;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.components.DiagramAxis;
import gelf.view.diagrams.components.DiagramComponent;
import gelf.view.diagrams.components.DiagramValueDisplayComponent;
import gelf.view.diagrams.type.Histogram;

/**
 * This class is the concrete implementation of {@link DiagramBuilder} and {@link IHistogramBuilder}.
 * It is both a part of the builder pattern (builder to {@link gelf.view.diagrams.DiagramDirector DiagramDirector})
 * and the template method pattern (template method {@link DiagramBuilder#buildDiagram buildDiagram}).
 * <p>
 * This class overrides the methods in {@link IDiagramBuilder}, which need to be different to
 * build {@link gelf.view.diagrams.type.Histogram Histogram} instances.
 * @author Alp Torac Genc
 *
 */
public class HistogramBuilder extends DiagramBuilder implements IHistogramBuilder {

	public HistogramBuilder(Container container) {
		super(container);
	}
	/**
	 * {@link IDiagramBuilder#makeDiagram makeDiagram}
	 */
	protected IDiagram makeDiagram(DiagramAxis[] axes, DiagramValueDisplayComponent[] valueDisplayComponents,
			DiagramComponent[] diagramSpecificComponent) {
		return new Histogram(this.getDiagramData(), axes, valueDisplayComponents, diagramSpecificComponent);
	}
	/**
	 * {@link IDiagramBuilder#getXAxisMaxValue getXAxisMaxValue}
	 */
	@Override
	public float getXAxisMaxValue() {
		return this.getDiagramData().getMaximumIndex();
	}
	/**
	 * {@link IDiagramBuilder#getYAxisMinValue getYAxisMinValue}
	 */
	@Override
	public float getYAxisMinValue() {
		float minVal = this.getDiagramData().getMinimumValue();
		
		if (minVal >= 0) {
			return 0;
		} else {
			return minVal;
		}
	}
	/**
	 * {@link IDiagramBuilder#getYAxisMaxValue getYAxisMaxValue}
	 */
	@Override
	public float getYAxisMaxValue() {
		float maxVal = this.getDiagramData().getMaximumValue();
		
		if (maxVal <= 0) {
			return 0;
		} else {
			return maxVal;
		}
	}

}

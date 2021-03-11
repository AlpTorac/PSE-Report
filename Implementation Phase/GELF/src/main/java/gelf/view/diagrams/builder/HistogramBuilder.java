package gelf.view.diagrams.builder;

import java.awt.Container;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.components.DiagramAxis;
import gelf.view.diagrams.components.DiagramComponent;
import gelf.view.diagrams.components.DiagramValueDisplayComponent;
import gelf.view.diagrams.type.Histogram;

public class HistogramBuilder extends DiagramBuilder implements IHistogramBuilder {

	public HistogramBuilder(Container container) {
		super(container);
	}
	
	protected IDiagram makeDiagram(DiagramAxis[] axes, DiagramValueDisplayComponent[] valueDisplayComponents,
			DiagramComponent[] diagramSpecificComponent) {
		return new Histogram(this.getDiagramData(), axes, valueDisplayComponents, diagramSpecificComponent);
	}

	@Override
	protected float getXAxisMaxValue() {
		return this.getDiagramData().getMaximumIndex();
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
	
	@Override
	protected float getYAxisMaxValue() {
		float maxVal = this.getDiagramData().getMaximumValue();
		
		if (maxVal <= 0) {
			return 0;
		} else {
			return maxVal;
		}
	}

}

package gelf.view.diagrams.indicator;

import java.awt.Color;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.components.DiagramAxis;
import gelf.view.diagrams.components.PositionIn2DDiagram;

public class XCoordinateIndicatorLineDisplayer extends CoordinateIndicatorLineDisplayer {
	protected XCoordinateIndicatorLineDisplayer(IDiagram diagram, Color color, int thickness) {
		super(diagram, color, thickness, IndicatorIdentifier.X_COORDINATE_INDICATOR);
		this.axes = diagram.getDiagramAxisPrototypes();
	}

	@Override
	protected DiagramAxis getAxisToIndicate() {
		return axes[0];
	}

	@Override
	protected PositionIn2DDiagram getStartPosForValue(float value) {
		return factory.makePositionInDiagram(this.getAxisToIndicate(), value, this.getParallelAxis(), 0);
	}

	@Override
	protected PositionIn2DDiagram getEndPosForValue(PositionIn2DDiagram startPos) {
		return factory.makePositionInDiagram(startPos, 0, this.getParallelAxis().getMax());
	}

	@Override
	protected DiagramAxis getParallelAxis() {
		return axes[1];
	}
}

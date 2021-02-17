package gelf.view.diagrams.indicator;

import java.awt.Color;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.components.DiagramAxis;
import gelf.view.diagrams.components.PositionIn2DDiagram;

public class YCoordinateIndicatorLineDisplayer extends CoordinateIndicatorLineDisplayer {
	protected YCoordinateIndicatorLineDisplayer(IDiagram diagram, Color color, int thickness) {
		super(diagram, color, thickness, IndicatorIdentifier.Y_COORDINATE_INDICATOR);
		this.axes = diagram.getDiagramAxisPrototypes();
		this.initViewHelperComponents();
	}

	@Override
	protected DiagramAxis getAxisToIndicate() {
		return this.axes[1];
	}

	@Override
	protected DiagramAxis getParallelAxis() {
		return this.axes[0];
	}

	@Override
	protected PositionIn2DDiagram getStartPosForValue(float value) {
		return factory.makePositionInDiagram(this.getParallelAxis(), 0, this.getAxisToIndicate(), value);
	}

	@Override
	protected PositionIn2DDiagram getEndPosForValue(PositionIn2DDiagram startPos) {
		return factory.makePositionInDiagram(startPos, this.getParallelAxis().getMax(), 0);
	}

}

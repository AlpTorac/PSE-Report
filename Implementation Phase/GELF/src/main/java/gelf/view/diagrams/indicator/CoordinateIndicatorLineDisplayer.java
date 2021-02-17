package gelf.view.diagrams.indicator;

import java.awt.Color;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.SettingsProvider;
import gelf.view.diagrams.components.DiagramAxis;
import gelf.view.diagrams.components.PositionIn2DDiagram;

public abstract class CoordinateIndicatorLineDisplayer extends HelperLineDisplayer {
	protected DiagramAxis[] axes;
	
	protected CoordinateIndicatorLineDisplayer(IDiagram diagram, Color color, int thickness, IndicatorIdentifier id) {
		super(diagram, SettingsProvider.getInstance().getDiagramViewHelperDisplayLayer(), thickness, id);
	}
	
	@Override
	protected void generateHelperComponents() {
		DiagramAxis axisToIndicate = this.getAxisToIndicate();
		
		float stepLength = (axisToIndicate.getMax() - axisToIndicate.getMin()) / axisToIndicate.getSteps();
		float currentVal = 0;
		
		for (int i = 0; i < axisToIndicate.getSteps(); i++) {
			currentVal += stepLength;
			PositionIn2DDiagram start = this.getStartPosForValue(currentVal);
			PositionIn2DDiagram end = this.getEndPosForValue(start);
			
			CoordinateIndicatorLine line = new CoordinateIndicatorLine(start.toPositionInFrame(), end.toPositionInFrame(),
					this.getColor(), this.getThickness());
			
			ViewHelperComponent vhc = new ViewHelperComponent(line, this);
			this.addViewHelperComponent(vhc);
		}
	}
	
	protected abstract DiagramAxis getAxisToIndicate();
	protected abstract DiagramAxis getParallelAxis();
	protected abstract PositionIn2DDiagram getStartPosForValue(float value);
	protected abstract PositionIn2DDiagram getEndPosForValue(PositionIn2DDiagram startPos);
}

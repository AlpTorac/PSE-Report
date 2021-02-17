package gelf.view.diagrams.indicator;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.SettingsProvider;
import gelf.view.diagrams.components.DiagramAxis;
import gelf.view.diagrams.components.PositionIn2DDiagram;
import gelf.view.diagrams.data.DiagramData;

public class ValueLineDisplayer extends HelperLineDisplayer {
	private DiagramAxis[] axes;
	private float value;
	
	protected ValueLineDisplayer(IDiagram diagram, int thickness, IndicatorIdentifier id) {
		super(diagram, SettingsProvider.getInstance().getDiagramViewHelperDisplayLayer(), thickness, id);
		this.axes = diagram.getDiagramAxisPrototypes();
		this.value = this.getValueToDisplay();
		this.initViewHelperComponents();
	}
	
	private float getValueToDisplay() {
		DiagramData data = diagram.cloneDiagramData();
		
		switch(this.getID()) {
		case MIN:
			return data.getMinimumValue();
		case MAX:
			return data.getMaximumValue();
		case AVG:
			return data.getAverageValue();
		case MED:
			return data.getValueMedian();
		default:
			throw new IllegalStateException("Unexpected IndicatorIdentifier: " + this.getID().toString());
		}
	}
	
	@Override
	protected void generateHelperComponents() {
		DiagramAxis parallelAxis = this.axes[0];
		DiagramAxis axisOfValue = this.axes[1];
		
			PositionIn2DDiagram start = factory.makePositionInDiagram(parallelAxis, 0, axisOfValue, this.getValue());
			PositionIn2DDiagram end = factory.makePositionInDiagram(start, parallelAxis.getMax(), 0);
			
			ValueLine line = new ValueLine(start.toPositionInFrame(), end.toPositionInFrame(),
					this.getValue(), this.getColor(), this.getThickness());
			
			ViewHelperComponent vhc = new ViewHelperComponent(line, this);
			this.addViewHelperComponent(vhc);
	}

	public float getValue() {
		return value;
	}
}

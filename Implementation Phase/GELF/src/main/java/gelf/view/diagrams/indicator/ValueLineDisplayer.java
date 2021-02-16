package gelf.view.diagrams.indicator;

import java.awt.Color;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.SettingsProvider;
import gelf.view.diagrams.components.DiagramAxis;

public class ValueLineDisplayer extends HelperLineDisplayer {
	private DiagramAxis axis;
	private Color color;
	private float thickness;
	private float value;
	
	protected ValueLineDisplayer(IDiagram diagram, DiagramAxis axis, Color color, float thickness,
			float value, IndicatorIdentifier id) {
		super(diagram, SettingsProvider.getInstance().getDiagramViewHelperDisplayLayer(), id);
	}
	
	private void createValueLine() {
		
	}
	
	@Override
	protected void generateHelperComponents() {

	}

}

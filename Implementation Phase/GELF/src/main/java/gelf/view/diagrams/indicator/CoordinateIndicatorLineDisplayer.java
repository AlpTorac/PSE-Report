package gelf.view.diagrams.indicator;

import java.awt.Color;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.SettingsProvider;
import gelf.view.diagrams.components.DiagramAxis;

public class CoordinateIndicatorLineDisplayer extends HelperLineDisplayer {
	private DiagramAxis axis;
	private Color color;
	private float thickness;
	
	protected CoordinateIndicatorLineDisplayer(IDiagram diagram,
			DiagramAxis axis, Color color, float thickness, IndicatorIdentifier id) {
		super(diagram, SettingsProvider.getInstance().getDiagramViewHelperDisplayLayer(), id);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void generateHelperComponents() {
		// TODO Auto-generated method stub
		
	}

}

package gelf.view.diagrams.indicator;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.SettingsProvider;
import gelf.view.diagrams.components.DiagramColorScale;

public class ValueScaleColorDisplayer extends HelperComponentDisplayer {
	private DiagramColorScale colorScale;
	
	protected ValueScaleColorDisplayer(IDiagram diagram,
			DiagramColorScale colorScale, IndicatorIdentifier id) {
		super(diagram, SettingsProvider.getInstance().getDiagramViewHelperDisplayLayer(), id);
		this.colorScale = colorScale;
	}

}

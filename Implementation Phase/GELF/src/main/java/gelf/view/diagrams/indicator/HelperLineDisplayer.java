package gelf.view.diagrams.indicator;

import gelf.view.diagrams.IDiagram;

public abstract class HelperLineDisplayer extends DiagramViewHelper {

	protected HelperLineDisplayer(IDiagram diagram, int layer, IndicatorIdentifier id) {
		super(diagram, layer, id);
	}

	protected abstract void generateHelperComponents();
}

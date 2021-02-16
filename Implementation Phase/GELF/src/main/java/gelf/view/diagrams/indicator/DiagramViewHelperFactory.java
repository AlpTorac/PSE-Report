package gelf.view.diagrams.indicator;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.components.DiagramAxis;

public class DiagramViewHelperFactory {
	private static DiagramViewHelperFactory instance;
	
	private DiagramViewHelperFactory() {
		
	}
	
	public static DiagramViewHelperFactory getInstance() {
		if (instance == null) {
			instance = new DiagramViewHelperFactory();
		}
		return instance;
	}
	
	public HelperComponentDisplayer createValueColorDisplayer(IDiagram diagram,
			IndicatorIdentifier id) {
		return null;
	}
	
	public HelperLineDisplayer createCoordinateGridDisplayer(IDiagram diagram,
			DiagramAxis axis, IndicatorIdentifier id) {
		return null;
	}
	
	public HelperLineDisplayer createValueLineDisplayer(IDiagram diagram,
			DiagramAxis axis, IndicatorIdentifier id) {
		return null;
	}
}

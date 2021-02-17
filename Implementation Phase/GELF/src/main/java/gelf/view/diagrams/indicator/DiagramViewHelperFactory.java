package gelf.view.diagrams.indicator;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.SettingsProvider;

public class DiagramViewHelperFactory {
	private static DiagramViewHelperFactory instance;
	private static SettingsProvider sp = SettingsProvider.getInstance();
	
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
	
	public HelperLineDisplayer createXCoordinateGridDisplayer(IDiagram diagram) {
		return new XCoordinateIndicatorLineDisplayer(diagram, sp.getAxisColor(), sp.getAxisThickness());
	}
	
	public HelperLineDisplayer createYCoordinateGridDisplayer(IDiagram diagram) {
		return new YCoordinateIndicatorLineDisplayer(diagram, sp.getAxisColor(), sp.getAxisThickness());
	}
	
	public HelperLineDisplayer createValueLineDisplayer(IDiagram diagram, IndicatorIdentifier id) {
		return new ValueLineDisplayer(diagram, sp.getAxisThickness(), id);
	}
}

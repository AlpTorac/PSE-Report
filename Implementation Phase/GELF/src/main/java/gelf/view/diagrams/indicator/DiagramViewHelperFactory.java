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
	
	public HelperLineDisplayer createMinLineDisplayer(IDiagram diagram) {
		return new ValueLineDisplayer(diagram, sp.getAxisThickness(), IndicatorIdentifier.MIN);
	}
	
	public HelperLineDisplayer createMaxLineDisplayer(IDiagram diagram) {
		return new ValueLineDisplayer(diagram, sp.getAxisThickness(), IndicatorIdentifier.MAX);
	}
	
	public HelperLineDisplayer createAvgLineDisplayer(IDiagram diagram) {
		return new ValueLineDisplayer(diagram, sp.getAxisThickness(), IndicatorIdentifier.AVG);
	}
	
	public HelperLineDisplayer createMedLineDisplayer(IDiagram diagram) {
		return new ValueLineDisplayer(diagram, sp.getAxisThickness(), IndicatorIdentifier.MED);
	}
}

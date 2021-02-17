package gelf.view.diagrams.indicator;

import java.awt.Color;
import java.util.TreeMap;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.SettingsProvider;

public class ValueFixColorDisplayer extends HelperComponentDisplayer {
	private TreeMap<Float, Color> mapping;
	
	protected ValueFixColorDisplayer(IDiagram diagram, 
			TreeMap<Float, Color> mapping, IndicatorIdentifier id) {
		super(diagram, SettingsProvider.getInstance().getDiagramViewHelperDisplayLayer(), id);
		this.mapping = mapping;
	}

	@Override
	protected void generateHelperComponents() {
		// TODO Auto-generated method stub
		
	}

}

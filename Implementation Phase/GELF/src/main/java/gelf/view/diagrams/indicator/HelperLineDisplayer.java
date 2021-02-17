package gelf.view.diagrams.indicator;

import java.awt.Color;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.SettingsProvider;
import gelf.view.diagrams.components.DiagramComponentFactory;

public abstract class HelperLineDisplayer extends DiagramViewHelper {
	protected static DiagramComponentFactory factory = DiagramComponentFactory.getDiagramComponentFactory();
	private Color color = SettingsProvider.getInstance().getDiagramCoordinateLineColor();
	private int thickness;
	
	protected HelperLineDisplayer(IDiagram diagram, int layer, int thickness, IndicatorIdentifier id) {
		super(diagram, layer, id);
		this.thickness = thickness;
	}

	public Color getColor() {
		return this.color;
	}

	public int getThickness() {
		return this.thickness;
	}
}

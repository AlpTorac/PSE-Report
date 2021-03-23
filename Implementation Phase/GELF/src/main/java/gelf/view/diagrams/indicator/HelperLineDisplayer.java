package gelf.view.diagrams.indicator;

import java.awt.Color;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.SettingsProvider;
import gelf.view.diagrams.components.DiagramComponentFactory;

/**
 * The super class of classes, which are responsible for displaying lines on an {@link gelf.view.diagrams.IDiagram IDiagram}.
 * @author Alp Torac Genc
 */
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

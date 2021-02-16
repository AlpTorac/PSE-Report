package gelf.view.diagrams.indicator;

import java.awt.Color;

import gelf.view.diagrams.SettingsProvider;
import gelf.view.diagrams.components.DiagramAxis;
import gelf.view.diagrams.components.DiagramComponent;
import gelf.view.diagrams.components.DiagramLine;

public class CoordinateIndicatorLine extends DiagramLine {

	protected CoordinateIndicatorLine(DiagramAxis axis, float value, Color color, int thickness) {
		super(null, null, color, thickness, SettingsProvider.getInstance().getDiagramViewHelperDisplayLayer());
	}

	@Override
	public DiagramComponent clone() {
		return null;
	}
}

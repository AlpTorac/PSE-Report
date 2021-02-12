package gelf.view.diagrams.indicator;

import java.awt.Color;

import gelf.view.diagrams.SettingsProvider;
import gelf.view.diagrams.components.DiagramComponent;
import gelf.view.diagrams.components.DiagramLine;
import gelf.view.diagrams.components.PositionInFrame;

public class ValueLine extends DiagramLine {

	protected ValueLine(PositionInFrame start, PositionInFrame end, Color color, int thickness) {
		super(start, end, color, thickness, SettingsProvider.getInstance().getDiagramViewHelperDisplayLayer());
	}

	@Override
	public DiagramComponent clone() {
		return null;
	}
}

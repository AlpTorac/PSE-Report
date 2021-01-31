package gelf.view.diagrams.components;

import java.awt.Color;

public class HistogramBar extends DiagramBar {
	protected HistogramBar(Color color, float value, PositionIn2DDiagram topLeft, PositionIn2DDiagram bottomRight,
			int borderThickness) {
		super(color, value, topLeft, bottomRight, borderThickness);
	}

	@Override
	public DiagramComponent clone() {
		// TODO Auto-generated method stub
		return null;
	}
}

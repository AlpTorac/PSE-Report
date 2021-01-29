package gelf.view.diagrams.components;

import java.awt.Color;

public class ValueDisplayPoint extends DiagramPoint {
	protected ValueDisplayPoint(Color color, double value, double size, PositionIn2DDiagram position) {
		super(position, color, value, size);
	}

	@Override
	public DiagramComponent clone() {
		return null;
	}

	@Override
	public void refreshValueRelevantAttributes() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initVisualElement() {
		// TODO Auto-generated method stub

	}
}

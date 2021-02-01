package gelf.view.diagrams.components;

import java.awt.Color;

public class SolidLine extends DiagramLine {
	protected SolidLine(PositionInFrame start, PositionInFrame end, Color color, int thickness) {
		super(start, end, color, thickness);
	}

	@Override
	public DiagramComponent clone() {
		return null;
	}

	@Override
	protected void initVisualElement() {
		// TODO Auto-generated method stub

	}
}

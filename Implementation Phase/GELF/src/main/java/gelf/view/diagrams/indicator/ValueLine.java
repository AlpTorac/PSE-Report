package gelf.view.diagrams.indicator;

import java.awt.Color;
import java.awt.Container;

import gelf.view.diagrams.components.DiagramComponent;
import gelf.view.diagrams.components.DiagramLine;
import gelf.view.diagrams.components.PositionInFrame;

public class ValueLine extends DiagramLine {

	protected ValueLine(PositionInFrame start, PositionInFrame end, Color color, int thickness, Container containingElement) {
		super(start, end, color, thickness, containingElement);
		// TODO Auto-generated constructor stub
	}

	@Override
	public DiagramComponent clone() {
		// TODO Auto-generated method stub
		return null;
	}
}

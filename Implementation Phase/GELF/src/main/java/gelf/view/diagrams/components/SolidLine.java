package gelf.view.diagrams.components;

import java.awt.Color;
import java.awt.geom.Line2D;

public class SolidLine extends DiagramLine {
	protected SolidLine(PositionInFrame start, PositionInFrame end, Color color, Number thickness) {
		super(start, end, color, thickness);
	}
	
	@Override
	public void show() {
		
	}
	@Override
	public void hide() {
		
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

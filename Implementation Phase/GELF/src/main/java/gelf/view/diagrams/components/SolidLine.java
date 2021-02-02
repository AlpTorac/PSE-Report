package gelf.view.diagrams.components;

import java.awt.Color;
import java.awt.Container;

public class SolidLine extends DiagramLine {
	protected SolidLine(PositionInFrame start, PositionInFrame end, Color color, int thickness, Container containingElement) {
		super(start, end, color, thickness, containingElement);
	}

	@Override
	public SolidLine clone() {
		Color newColor = new Color(this.getColor().getRGB());
		return new SolidLine(this.getStartInFrame().clone(), this.getEndInFrame().clone(), newColor, this.getThickness(), this.containingElement);
	}

	@Override
	protected void initVisualElement() {
		// TODO Auto-generated method stub

	}
}

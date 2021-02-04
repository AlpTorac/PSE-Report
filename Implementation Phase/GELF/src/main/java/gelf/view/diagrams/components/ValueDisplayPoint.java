package gelf.view.diagrams.components;

import java.awt.Color;
import java.awt.Container;

public class ValueDisplayPoint extends DiagramPoint {
	protected ValueDisplayPoint(Color color, float value, float size, PositionIn2DDiagram position, Container containingElement) {
		super(position, color, value, size, containingElement);
	}

	@Override
	public ValueDisplayPoint clone() {
		Color newColor = new Color(this.getColor().getRGB());
		return new ValueDisplayPoint(newColor, this.getValue(), this.getSize(),
				this.getPositionInDiagram().clone(), this.containingElement);
	}
}

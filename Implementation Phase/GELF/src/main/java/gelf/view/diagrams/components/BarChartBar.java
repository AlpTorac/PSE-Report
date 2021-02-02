package gelf.view.diagrams.components;

import java.awt.Color;
import java.awt.Container;

public class BarChartBar extends DiagramBar {

	protected BarChartBar(Color color, float value, PositionIn2DDiagram topLeft, PositionIn2DDiagram bottomRight,
			int borderThickness, Container containingElement) {
		super(color, value, topLeft, bottomRight, borderThickness, containingElement);
	}

	@Override
	public BarChartBar clone() {
		Color newColor = new Color(this.getColor().getRGB());
		return new BarChartBar(newColor, this.getValue(),
				this.getTopLeftInDiagram().clone(),
				this.getBottomRightInDiagram().clone(),
				this.getBorderThickness(),
				this.containingElement);
	}
}

package gelf.view.diagrams.components;

import java.awt.Color;

public class HistogramBar extends DiagramBar {
	protected HistogramBar(Color color, float value, PositionIn2DDiagram topLeft, PositionIn2DDiagram bottomRight,
			int borderThickness) {
		super(color, value, topLeft, bottomRight, borderThickness);
	}

	@Override
	public HistogramBar clone() {
		Color newColor = new Color(this.getColor().getRGB());
		return new HistogramBar(newColor,
			this.getValue(),
			this.getTopLeftInDiagram().clone(),
			this.getBottomRightInDiagram().clone(),
			this.getBorderThickness());
	}
}

package gelf.view.diagrams.components;

import java.awt.Color;

public class BarChartBar extends DiagramBar {

	protected BarChartBar(Color color, float value, PositionIn2DDiagram topLeft, PositionIn2DDiagram bottomRight,
			int borderThickness) {
		super(color, value, topLeft, bottomRight, borderThickness);
		// TODO Auto-generated constructor stub
	}

	@Override
	public DiagramComponent clone() {
		return new BarChartBar(this.getColor(), this.getValue(), this.getTopLeftInDiagram(), this.getBottomRightInDiagram(), this.getBorderThickness());
	}
}

package gelf.view.diagrams.components;

import java.awt.Color;

public class BarChartBar extends DiagramBar {

	protected BarChartBar(Color color, float value, PositionIn2DDiagram topLeft, PositionIn2DDiagram bottomRight,
			int borderThickness) {
		super(color, value, topLeft, bottomRight, borderThickness);
	}

	@Override
	public BarChartBar clone() {
		Color newColor = new Color(this.getColor().getRGB());
		return new BarChartBar(newColor, this.getValue(),
				this.getTopLeftInDiagram().clone(),
				this.getBottomRightInDiagram().clone(),
				this.getBorderThickness());
	}
	
	@Override
	protected String getRoundedPositionInDiagramString() {
		String result = "bar ";
		
		int index = ((int) this.getBottomRightInDiagram().getXCoordinate());
		
		String[] xAxisDisplays = this.getBottomRightInDiagram().getAxes()[0].getStepDisplays();
		
		if (index < xAxisDisplays.length) {
			result += this.getBottomRightInDiagram().getAxes()[0].getStepDisplays()[index];
		}
		
		return result;
	}
}

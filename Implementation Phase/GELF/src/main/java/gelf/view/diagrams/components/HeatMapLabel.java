package gelf.view.diagrams.components;

import java.awt.Container;

public class HeatMapLabel extends DiagramValueLabel {

	private DiagramColorScale colorScale;
	
	protected HeatMapLabel(PositionIn2DDiagram topLeft, PositionIn2DDiagram bottomRight, DiagramColorScale colorScale, float value,
			int borderThickness, Container containingElement) {
		super(topLeft, bottomRight, colorScale.valueToColor(value), value, borderThickness, containingElement);
		
		this.colorScale = colorScale;
	}


	@Override
	public void refreshValueRelevantAttributes() {
		this.setColor(this.colorScale.valueToColor(this.getValue()));
	}

	@Override
	public HeatMapLabel clone() {
		return new HeatMapLabel(this.getTopLeftInDiagram().clone(),
				this.getBottomRightInDiagram().clone(),
				this.colorScale,
				this.getValue(),
				this.getBorderThickness(),
				this.containingElement);
	}
}

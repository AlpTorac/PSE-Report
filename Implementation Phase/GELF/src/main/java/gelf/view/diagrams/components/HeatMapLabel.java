package gelf.view.diagrams.components;

/**
 * The class that represents a label in a {@link gelf.view.diagrams.type.HeatMap HeatMap}.
 * <p>
 * This component changes its color based on its value and {@link #colorScale}.
 * @author Alp Torac Genc
 */
public class HeatMapLabel extends DiagramValueLabel {

	private DiagramColorScale colorScale;
	
	protected HeatMapLabel(PositionIn2DDiagram topLeft, PositionIn2DDiagram bottomRight, DiagramColorScale colorScale, float value,
			int borderThickness) {
		super(topLeft, bottomRight, colorScale.valueToColor(value), value, borderThickness);
		
		this.colorScale = colorScale;
	}


	@Override
	public void refreshValueRelevantAttributes() {
		super.refreshValueRelevantAttributes();
		this.setColor(this.colorScale.valueToColor(this.getValue()));
	}

	@Override
	public HeatMapLabel clone() {
		return new HeatMapLabel(this.getTopLeftInDiagram().clone(),
				this.getBottomRightInDiagram().clone(),
				this.colorScale,
				this.getValue(),
				this.getBorderThickness());
	}
}

package gelf.view.diagrams.components;

import java.awt.Color;
import java.util.ArrayList;

/**
 * The class that represents a bar of a {@link gelf.view.diagrams.type.BarChart BarChart}.
 * @author Alp Torac Genc
 */
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
		if (this.diagram != null) {
			ArrayList<String[]> descs = this.diagram.cloneDiagramData().extractValueDescriptions();
			int indexPosition = this.diagram.getIndexPositionsOfComponent(this)[0];
			int i = 0;
			while (i < descs.size() && descs.get(i) != null &&
					descs.get(i).length <= indexPosition) {
				indexPosition -= descs.get(i).length;
				i++;
			}
			
			String description = (descs != null) ? 
					descs.get(i)[indexPosition]:
					String.valueOf(indexPosition);
			String result = "bar " + description;
			
			return result;
		} else {
			return "";
		}
	}
}

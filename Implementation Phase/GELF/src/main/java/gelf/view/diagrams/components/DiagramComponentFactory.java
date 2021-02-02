package gelf.view.diagrams.components;

import java.awt.Color;
import java.awt.Container;

public class DiagramComponentFactory {
	private static DiagramComponentFactory instance = new DiagramComponentFactory();

	private DiagramComponentFactory() {

	}

	public static DiagramComponentFactory getDiagramComponentFactory() {
		return DiagramComponentFactory.instance;
	}
	
	public SolidLine createSolidLine(PositionInFrame start, PositionInFrame end, Color color, int thickness, Container containingElement) {
		return new SolidLine(start, end, color, thickness, containingElement);
	}
	public BiColorScale createBiColorScale(PositionInFrame topLeft, PositionInFrame bottomRight, Color borderColor,
			float minVal, float maxVal, Color minValColor, Color maxValColor, int borderThickness, Container containingElement) {
		return new BiColorScale(topLeft, bottomRight, borderColor, minVal, maxVal, minValColor, maxValColor, borderThickness, containingElement);
	}
	public ValueDisplayPoint createValueDisplayPoint(Color color, float value, float size, PositionIn2DDiagram position, Container containingElement) {
		return new ValueDisplayPoint(color, value, size, position, containingElement);
	}
	public HistogramBar createHistogramBar(Color color, float value, PositionIn2DDiagram topLeft, PositionIn2DDiagram bottomRight,
			int borderThickness, Container containingElement) {
		return new HistogramBar(color, value, topLeft, bottomRight, borderThickness, containingElement);
	}
	public BarChartBar createBarChartBar(Color color, float value, PositionIn2DDiagram topLeft, PositionIn2DDiagram bottomRight,
			int borderThickness, Container containingElement) {
		return new BarChartBar(color, value, topLeft, bottomRight, borderThickness, containingElement);
	}
	public SolidAxis createSolidAxis(PositionInFrame start, PositionInFrame end, float min, float max, int steps, Color color, int thickness, Container containingElement) {
		SolidLine l = createSolidLine(start, end, color, thickness, containingElement);
		return new SolidAxis(l, min, max, steps, containingElement);
	}
	public HeatMapLabel createHeatMapLabel(PositionIn2DDiagram topLeft, PositionIn2DDiagram bottomRight, DiagramColorScale colorScale, float value,
			int borderThickness, Container containingElement) {
		return new HeatMapLabel(topLeft, bottomRight, colorScale, value, borderThickness, containingElement);
	}
	public DescriptionLabel createDescriptionLabel(PositionInFrame topLeft, PositionInFrame bottomRight, Color color, String caption,
			int borderThickness, Container containingElement) {
		return new DescriptionLabel(topLeft, bottomRight, color, caption, borderThickness, containingElement);
	}
	
}

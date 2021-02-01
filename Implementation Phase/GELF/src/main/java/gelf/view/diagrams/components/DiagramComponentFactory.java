package gelf.view.diagrams.components;

import java.awt.Color;

public class DiagramComponentFactory {
	private static DiagramComponentFactory instance = new DiagramComponentFactory();

	private DiagramComponentFactory() {

	}

	public static DiagramComponentFactory getDiagramComponentFactory() {
		return DiagramComponentFactory.instance;
	}
	
	public SolidLine createSolidLine(PositionInFrame start, PositionInFrame end, Color color, int thickness) {
		return new SolidLine(start, end, color, thickness);
	}
	public BiColorScale createBiColorScale(PositionInFrame topLeft, PositionInFrame bottomRight, Color borderColor,
			float minVal, float maxVal, Color minValColor, Color maxValColor, int borderThickness) {
		return new BiColorScale(topLeft, bottomRight, borderColor, minVal, maxVal, minValColor, maxValColor, borderThickness);
	}
	public ValueDisplayPoint createValueDisplayPoint(Color color, float value, float size, PositionIn2DDiagram position) {
		return new ValueDisplayPoint(color, value, size, position);
	}
	public HistogramBar createHistogramBar(Color color, float value, PositionIn2DDiagram topLeft, PositionIn2DDiagram bottomRight,
			int borderThickness) {
		return new HistogramBar(color, value, topLeft, bottomRight, borderThickness);
	}
	public BarChartBar createBarChartBar(Color color, float value, PositionIn2DDiagram topLeft, PositionIn2DDiagram bottomRight,
			int borderThickness) {
		return new BarChartBar(color, value, topLeft, bottomRight, borderThickness);
	}
	public SolidAxis createSolidAxis(PositionInFrame start, PositionInFrame end, float min, float max, int steps, Color color, int thickness) {
		DiagramLine l = new SolidLine(start, end, color, thickness);
		return new SolidAxis(l, min, max, steps);
	}
	public HeatMapLabel createHeatMapLabel(PositionIn2DDiagram topLeft, PositionIn2DDiagram bottomRight, DiagramColorScale colorScale, float value,
			int borderThickness) {
		return new HeatMapLabel(topLeft, bottomRight, colorScale, value, borderThickness);
	}
	public DescriptionLabel createDescriptionLabel(PositionInFrame topLeft, PositionInFrame bottomRight, Color color, String caption,
			int borderThickness) {
		return new DescriptionLabel(topLeft, bottomRight, color, caption, borderThickness);
	}
	
}

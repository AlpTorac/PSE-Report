package gelf.view.diagrams.components;

import java.awt.Color;

/**
 * The singleton facade class that manages access to methods, which create {@link DiagramComponent},
 * {@link PositionInFrame} and {@link PositionInDiagram}.
 * @author Alp Torac Genc
 */
public class DiagramComponentFactory {
	private static DiagramComponentFactory instance = new DiagramComponentFactory();

	private DiagramComponentFactory() {}

	public static DiagramComponentFactory getDiagramComponentFactory() {
		return DiagramComponentFactory.instance;
	}
	
	public PositionInFrame makePositionInFrame(double x, double y) {
		return new PositionInFrame(x, y);
	}
	
	public PositionIn2DDiagram makePositionInDiagram(DiagramAxis xAxis, double xCoordinate,
			DiagramAxis yAxis, double yCoordinate) {
		return new PositionIn2DDiagram(xAxis, xCoordinate, yAxis, yCoordinate);
	}
	
	public PositionInFrame makePositionInFrame(PositionInFrame referencePoint, double x, double y) {
		return new PositionInFrame(referencePoint, x, y);
	}
	
	public PositionIn2DDiagram makePositionInDiagram(PositionIn2DDiagram referencePoint, double xCoordinate, double yCoordinate) {
		return new PositionIn2DDiagram(referencePoint, xCoordinate, yCoordinate);
	}
	
	public SolidLine createSolidLine(PositionInFrame start, PositionInFrame end, Color color, int thickness) {
		return new SolidLine(start, end, color, thickness);
	}
	public BiColorScale createBiColorScale(PositionInFrame topLeft, PositionInFrame bottomRight, Color borderColor,
			float minVal, float maxVal, Color minValColor, Color maxValColor, int borderThickness) {
		return new BiColorScale(topLeft, bottomRight, borderColor, minVal, maxVal, minValColor, maxValColor, borderThickness);
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
		SolidLine l = createSolidLine(start, end, color, thickness);
		return new SolidAxis(l, min, max, steps);
	}
	public SolidAxis createSolidAxis(PositionInFrame start, PositionInFrame end, float min, float max, int steps, Color color, int thickness, String[] stepDisplays) {
		SolidLine l = createSolidLine(start, end, color, thickness);
		return new SolidAxis(l, min, max, steps, stepDisplays);
	}
	public HeatMapLabel createHeatMapLabel(PositionIn2DDiagram topLeft, PositionIn2DDiagram bottomRight, DiagramColorScale colorScale, float value,
			int borderThickness) {
		return new HeatMapLabel(topLeft, bottomRight, colorScale, value, borderThickness);
	}
}

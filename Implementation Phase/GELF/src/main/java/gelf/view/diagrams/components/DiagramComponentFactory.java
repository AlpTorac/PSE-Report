package gelf.view.diagrams.components;

import java.awt.Color;

public class DiagramComponentFactory {
	private static DiagramComponentFactory instance = new DiagramComponentFactory();

	private DiagramComponentFactory() {

	}

	public static DiagramComponentFactory getDiagramComponentFactory() {
		return DiagramComponentFactory.instance;
	}

	public DiagramPoint createPoint(double value, PositionIn2DDiagram position, double size) {
		return null;
	}

	public DiagramValueLabel createValueLabel(double value, PositionIn2DDiagram topLeft,
			PositionIn2DDiagram bottomRight, double borderThickness) {
		return null;
	}

	public DiagramBar createBar(double value, PositionIn2DDiagram topLeft, PositionIn2DDiagram bottomRight,
			double borderThickness) {
		return null;
	}

	public DiagramLabel createLabel(PositionInFrame topLeft, PositionInFrame bottomRight, Color color, String caption,
			double borderThickness) {
		return null;
	}

	public DiagramAxis createAxis(DiagramLine axisLine, double min, double max, int steps) {
		return null;
	}

	public DiagramColorScale createColorScale(PositionInFrame topLeft, PositionInFrame bottomRight, Color borderColor,
			double[] values, Color[] valueColors, double borderThickness) {
		return null;
	}

	public DiagramLine createLine(PositionInFrame start, PositionInFrame end, Color color, double thickness) {
		return null;
	}
}

package gelf.view.diagrams.components;

import java.awt.Color;

public class DiagramComponentFactory {
	private static DiagramComponentFactory instance = new DiagramComponentFactory();

	private DiagramComponentFactory() {

	}

	public static DiagramComponentFactory getDiagramComponentFactory() {
		return DiagramComponentFactory.instance;
	}

	public DiagramPoint createPoint(float value, PositionIn2DDiagram position, float size) {
		return null;
	}

	public DiagramValueLabel createValueLabel(float value, PositionIn2DDiagram topLeft,
			PositionIn2DDiagram bottomRight, float borderThickness) {
		return null;
	}

	public DiagramBar createBar(float value, PositionIn2DDiagram topLeft, PositionIn2DDiagram bottomRight,
			float borderThickness) {
		return null;
	}

	public DiagramLabel createLabel(PositionInFrame topLeft, PositionInFrame bottomRight, Color color, String caption,
			float borderThickness) {
		return null;
	}

	public DiagramAxis createAxis(DiagramLine axisLine, float min, float max, int steps) {
		return null;
	}

	public DiagramColorScale createColorScale(PositionInFrame topLeft, PositionInFrame bottomRight, Color borderColor,
			float[] values, Color[] valueColors, float borderThickness) {
		return null;
	}

	public DiagramLine createLine(PositionInFrame start, PositionInFrame end, Color color, float thickness) {
		return null;
	}
}

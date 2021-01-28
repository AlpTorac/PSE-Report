package gelf.view.diagrams.components;

import java.awt.Color;

public class DiagramComponentFactory {
	private static DiagramComponentFactory instance = new DiagramComponentFactory();
	
	private DiagramComponentFactory() {
		
	}

	public static DiagramComponentFactory getDiagramComponentFactory() {
		return DiagramComponentFactory.instance;
	}
	
	public DiagramPoint createPoint(Number value, PositionIn2DDiagram position, Number size) {
		return null;
	}
	
	public DiagramValueLabel createValueLabel(Number value, PositionIn2DDiagram bottomLeft, PositionIn2DDiagram
			topRight, Number borderThickness) {
		return null;
	}
	
	public DiagramBar createBar(Number value, PositionIn2DDiagram bottomLeft, PositionIn2DDiagram
			topRight, Number borderThickness) {
		return null;
	}
	
	public DiagramLabel createLabel(PositionInFrame bottomLeft, PositionInFrame topRight, Color
			color, String caption, Number borderThickness) {
		return null;
	}
	
	public DiagramAxis createAxis(DiagramLine axisLine, Number min, Number max, int steps) {
		return null;
	}
	
	public DiagramColorScale createColorScale(PositionInFrame bottomLeft, PositionInFrame topRight,
			Color borderColor, Number[] values, Color[] valueColors, Number borderThickness) {
		return null;
	}
	
	public DiagramLine createLine(PositionInFrame start, PositionInFrame end, Color color, Number
			thickness) {
		return null;
	}
}

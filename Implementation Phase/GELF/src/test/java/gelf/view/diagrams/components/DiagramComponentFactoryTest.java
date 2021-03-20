package gelf.view.diagrams.components;

import java.awt.Color;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import gelf.view.diagrams.TestCase;

class DiagramComponentFactoryTest implements TestCase {
	private static DiagramComponentFactory f = DiagramComponentFactory.getDiagramComponentFactory();
	
	private static Color c = Color.BLACK;
	
	private static Color minValColor = Color.RED;
	private static Color maxValColor = Color.BLUE;
	
	private static PositionInFrame start = new PositionInFrame(500, 500);
	private static PositionInFrame endX = new PositionInFrame(900, 500);
	private static PositionInFrame endY = new PositionInFrame(500, 100);
	
	private static PositionInFrame posFrameStart = new PositionInFrame(100, 100);
	private static PositionInFrame posFrameEnd = new PositionInFrame(300, 200);
	private static DiagramAxis xAxis = f.createSolidAxis(start, endX, 0, 10, 20, c, 1);
	private static DiagramAxis yAxis = f.createSolidAxis(start, endY, 0, 10, 20, c, 1);
	private static PositionIn2DDiagram startPosInAxis = new PositionIn2DDiagram(xAxis, 2, yAxis, 7);
	private static PositionIn2DDiagram endPosInAxis = new PositionIn2DDiagram(xAxis, 4, yAxis, 0);
	
	private static DiagramColorScale cs = f.createBiColorScale(posFrameStart, posFrameEnd, c, 0, 20, minValColor, maxValColor, 1);
	
	private static DiagramComponent[] diagramComponents = new DiagramComponent[6];
	
	@BeforeAll
	static void addPreMadesToDiagramComponents() {
		diagramComponents[0] = xAxis;
		diagramComponents[1] = cs;
	}
	
	@Test
	void createBarChartBarTest() {
		diagramComponents[2] = f.createBarChartBar(c, 2, startPosInAxis, endPosInAxis, 1);
	}
	
	@Test
	void createHeatMapLabelTest() {
		diagramComponents[3] = f.createHeatMapLabel(startPosInAxis, endPosInAxis, cs, 10, 1);
	}
	
	@Test
	void createHistogramBarTest() {
		diagramComponents[4] = f.createHistogramBar(c, 2, startPosInAxis, endPosInAxis, 1);
	}
	
	@Test
	void createSolidLineTest() {
		diagramComponents[5] = f.createSolidLine(posFrameStart, posFrameEnd, c, 1);
	}

	@AfterAll
	static void cloneTest() {
		DiagramComponent[] clones = new DiagramComponent[diagramComponents.length];
		
		for (int i = 0; i < diagramComponents.length; i++) {
			clones[i] = diagramComponents[i].clone();
		}
		
		for (int index = 0; index < clones.length; index++) {
			DiagramComponent clone = clones[index];
			DiagramComponent actual = diagramComponents[index];
			
			Assertions.assertNotEquals(clone, actual);
			Assertions.assertNotEquals(clone.visualElement, actual.visualElement);
		}
	}
	
}

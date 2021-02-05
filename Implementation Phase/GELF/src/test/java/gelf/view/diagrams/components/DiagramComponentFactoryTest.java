package gelf.view.diagrams.components;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.awt.Container;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DiagramComponentFactoryTest implements TestCase {

	private static Container container = new TestFrame().getContentPane();
	
	private static DiagramComponentFactory f = DiagramComponentFactory.getDiagramComponentFactory();
	
	private static Color c = Color.BLACK;
	
	private static Color minValColor = Color.RED;
	private static Color maxValColor = Color.BLUE;
	
	private static PositionInFrame start = new PositionInFrame(500, 500);
	private static PositionInFrame endX = new PositionInFrame(900, 500);
	private static PositionInFrame endY = new PositionInFrame(500, 100);
	
	private static PositionInFrame posFrameStart = new PositionInFrame(100, 100);
	private static PositionInFrame posFrameEnd = new PositionInFrame(300, 200);
	private static DiagramAxis xAxis = f.createSolidAxis(start, endX, 0, 10, 20, c, 1, container);
	private static DiagramAxis yAxis = f.createSolidAxis(start, endY, 0, 10, 20, c, 1, container);
	private static PositionIn2DDiagram startPosInAxis = new PositionIn2DDiagram(xAxis, 2, yAxis, 7);
	private static PositionIn2DDiagram endPosInAxis = new PositionIn2DDiagram(xAxis, 4, yAxis, 0);
	
	private static DiagramColorScale cs = f.createBiColorScale(posFrameStart, posFrameEnd, c, 0, 20, minValColor, maxValColor, 1, container);;
	
	@Test
	void createBarChartBarTest() {
		f.createBarChartBar(c, 2, startPosInAxis, endPosInAxis, 1, container);
	}
	
	@Test
	void createDescriptionLabelTest() {
		f.createDescriptionLabel(posFrameStart, posFrameEnd, c, "Testing DescriptionLabel", 1, container);
	}
	
	@Test
	void createHeatMapLabelTest() {
		f.createHeatMapLabel(startPosInAxis, endPosInAxis, cs, 10, 1, container);
	}
	
	@Test
	void createHistogramBarTest() {
		f.createHistogramBar(c, 2, startPosInAxis, endPosInAxis, 1, container);
	}
	
	@Test
	void createSolidLineTest() {
		f.createSolidLine(posFrameStart, posFrameEnd, c, 1, container);
	}
	
	@Test
	void createValueDisplayPointTest() {
		f.createValueDisplayPoint(c, 2, 1, startPosInAxis, container);
	}

}

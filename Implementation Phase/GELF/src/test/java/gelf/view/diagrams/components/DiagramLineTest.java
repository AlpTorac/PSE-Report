package gelf.view.diagrams.components;

import java.awt.Color;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gelf.view.diagrams.TestCase;
import gelf.view.diagrams.TestFrame;
import gelf.view.diagrams.type.BarChart;

class DiagramLineTest implements TestCase {
	private static TestFrame frame = new TestFrame();
	
	private static void initAndShow(DiagramComponent[] lines) {
		BarChart diagram = new BarChart(null, null,
				null, lines);
		
		diagram.attachToContainer(frame);
		TestCase.showStatic(frame, TestCase.SHOW_DURATION);
		diagram.removeFromContainer();
	}
	
	@Test
	void initialisationTest() {
		PositionInFrame start = new PositionInFrame(100, 100);
		PositionInFrame end = new PositionInFrame(400, 400);
		DiagramLine line = DiagramComponentFactory.getDiagramComponentFactory().createSolidLine(start, end, Color.BLACK, 2);
		initAndShow(new DiagramComponent[] {line});
	}
	
	@Test
	void lineIntersectionTest() {
		PositionInFrame start1 = new PositionInFrame(100, 100);
		PositionInFrame end1 = new PositionInFrame(400, 400);
		PositionInFrame start2 = new PositionInFrame(100, 400);
		PositionInFrame end2 = new PositionInFrame(400, 100);
		DiagramLine line2 = DiagramComponentFactory.getDiagramComponentFactory().createSolidLine(start2, end2, Color.BLACK, 2);
		DiagramLine line1 = DiagramComponentFactory.getDiagramComponentFactory().createSolidLine(start1, end1, Color.BLACK, 2);
		initAndShow(new DiagramComponent[] {line1, line2});
	}
	
	@Test
	void straightLinesTest() {
		PositionInFrame origin = new PositionInFrame(400, 400);
		PositionInFrame endX = new PositionInFrame(600, 400);
		PositionInFrame endY = new PositionInFrame(400, 200);
		DiagramLine xLine = DiagramComponentFactory.getDiagramComponentFactory().createSolidLine(origin, endX, Color.BLACK, 2);
		DiagramLine yLine = DiagramComponentFactory.getDiagramComponentFactory().createSolidLine(origin, endY, Color.BLACK, 2);
		initAndShow(new DiagramComponent[] {xLine, yLine});
	}
	
	@Test
	void lengthTest() {
		PositionInFrame corner = new PositionInFrame(200, 200);
		PositionInFrame endX = new PositionInFrame(203, 200);
		PositionInFrame endY = new PositionInFrame(200, 196);
		
		DiagramLine horizontalLine = DiagramComponentFactory.getDiagramComponentFactory().createSolidLine(corner, endX, Color.BLACK, 1);
		DiagramLine verticalLine = DiagramComponentFactory.getDiagramComponentFactory().createSolidLine(corner, endY, Color.BLACK, 1);
		DiagramLine hypotenuse = DiagramComponentFactory.getDiagramComponentFactory().createSolidLine(endY, endX, Color.BLACK, 1);
		
		Assertions.assertEquals(3, horizontalLine.calculateLength());
		Assertions.assertEquals(4, verticalLine.calculateLength());
		Assertions.assertEquals(5, hypotenuse.calculateLength());
	}
	
	@Test
	void horizontalLengthTest() {
		PositionInFrame corner = new PositionInFrame(200, 200);
		PositionInFrame endX = new PositionInFrame(203, 200);
		PositionInFrame endY = new PositionInFrame(200, 196);
		
		DiagramLine horizontalLine = DiagramComponentFactory.getDiagramComponentFactory().createSolidLine(corner, endX, Color.BLACK, 1);
		DiagramLine verticalLine = DiagramComponentFactory.getDiagramComponentFactory().createSolidLine(corner, endY, Color.BLACK, 1);
		DiagramLine hypotenuse = DiagramComponentFactory.getDiagramComponentFactory().createSolidLine(endY, endX, Color.BLACK, 1);
		
		Assertions.assertEquals(3, horizontalLine.calculateHorizontalLength());
		Assertions.assertEquals(0, verticalLine.calculateHorizontalLength());
		Assertions.assertEquals(3, hypotenuse.calculateHorizontalLength());
	}
	
	@Test
	void verticalLengthTest() {
		PositionInFrame corner = new PositionInFrame(200, 200);
		PositionInFrame endX = new PositionInFrame(203, 200);
		PositionInFrame endY = new PositionInFrame(200, 196);
		
		DiagramLine horizontalLine = DiagramComponentFactory.getDiagramComponentFactory().createSolidLine(corner, endX, Color.BLACK, 1);
		DiagramLine verticalLine = DiagramComponentFactory.getDiagramComponentFactory().createSolidLine(corner, endY, Color.BLACK, 1);
		DiagramLine hypotenuse = DiagramComponentFactory.getDiagramComponentFactory().createSolidLine(endY, endX, Color.BLACK, 1);
		
		Assertions.assertEquals(0, horizontalLine.calculateVerticalLength());
		Assertions.assertEquals(4, verticalLine.calculateVerticalLength());
		Assertions.assertEquals(4, hypotenuse.calculateVerticalLength());
	}

	@Test
	void setStartTest() {
		int thickness = 2;
		
		PositionInFrame start = new PositionInFrame(100, 100);
		PositionInFrame end = new PositionInFrame(400, 400);
		DiagramLine line = DiagramComponentFactory.getDiagramComponentFactory().createSolidLine(start, end, Color.BLACK, thickness);
		
		line.setStartInFrame(321, 54);
		
		Assertions.assertEquals(321, line.getStartInFrame().getXPos());
		Assertions.assertEquals(54, line.getStartInFrame().getYPos());
		Assertions.assertEquals(400, line.getEndInFrame().getXPos());
		Assertions.assertEquals(400, line.getEndInFrame().getYPos());
		
		double visualElementMinX = line.visualElement.getBounds().getMinX();
		double visualElementMinY = line.visualElement.getBounds().getMinY();
		double visualElementMaxX = line.visualElement.getBounds().getMaxX();
		double visualElementMaxY = line.visualElement.getBounds().getMaxY();
		
		Assertions.assertEquals(321, visualElementMinX);
		Assertions.assertEquals(54, visualElementMinY);
		Assertions.assertEquals(400 + thickness, visualElementMaxX);
		Assertions.assertEquals(400 + thickness, visualElementMaxY);
	}
	
	@Test
	void setEndTest() {
		int thickness = 2;
		
		PositionInFrame start = new PositionInFrame(100, 100);
		PositionInFrame end = new PositionInFrame(400, 400);
		DiagramLine line = DiagramComponentFactory.getDiagramComponentFactory().createSolidLine(start, end, Color.BLACK, thickness);
		
		line.setEndInFrame(321, 54);
		
		Assertions.assertEquals(100, line.getStartInFrame().getXPos());
		Assertions.assertEquals(100, line.getStartInFrame().getYPos());
		Assertions.assertEquals(321, line.getEndInFrame().getXPos());
		Assertions.assertEquals(54, line.getEndInFrame().getYPos());
		
		double visualElementMinX = line.visualElement.getBounds().getMinX();
		double visualElementMinY = line.visualElement.getBounds().getMinY();
		double visualElementMaxX = line.visualElement.getBounds().getMaxX();
		double visualElementMaxY = line.visualElement.getBounds().getMaxY();
		
		Assertions.assertEquals(100, visualElementMinX);
		Assertions.assertEquals(54, visualElementMinY);
		Assertions.assertEquals(321 + thickness, visualElementMaxX);
		Assertions.assertEquals(100 + thickness, visualElementMaxY);
	}
	
	@Test
	void setThicknessTest() {
		int thickness = 2;
		
		PositionInFrame start = new PositionInFrame(100, 100);
		PositionInFrame end = new PositionInFrame(400, 400);
		DiagramLine line = DiagramComponentFactory.getDiagramComponentFactory().createSolidLine(start, end, Color.BLACK, thickness);
		
		line.setThickness(4);
		
		Assertions.assertEquals(line.getThickness(), 4);
	}
	
	@Test
	void showAndHideTest() {
		int thickness = 5;
		
		PositionInFrame start = new PositionInFrame(100, 100);
		PositionInFrame end = new PositionInFrame(400, 400);
		DiagramLine line = DiagramComponentFactory.getDiagramComponentFactory().createSolidLine(start, end, Color.BLACK, thickness);
		
		initAndShow(new DiagramComponent[] {line});
	}
}

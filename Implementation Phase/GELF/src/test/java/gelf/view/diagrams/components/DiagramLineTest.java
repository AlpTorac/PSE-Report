package gelf.view.diagrams.components;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DiagramLineTest implements TestCase {
	
	@Test
	void initialisationTest() {
		TestFrame frame = new TestFrame();
		
		PositionInFrame start = new PositionInFrame(100, 100);
		PositionInFrame end = new PositionInFrame(400, 400);
		DiagramLine line = DiagramComponentFactory.getDiagramComponentFactory().createSolidLine(start, end, Color.BLACK, 2, frame.getContentPane());
		show(frame, line, TestCase.SHOW_DURATION);
	}
	
	@Test
	void lineIntersectionTest() {
		TestFrame frame = new TestFrame();
		
		PositionInFrame start1 = new PositionInFrame(100, 100);
		PositionInFrame end1 = new PositionInFrame(400, 400);
		PositionInFrame start2 = new PositionInFrame(100, 400);
		PositionInFrame end2 = new PositionInFrame(400, 100);
		DiagramLine line2 = DiagramComponentFactory.getDiagramComponentFactory().createSolidLine(start2, end2, Color.BLACK, 2, frame.getContentPane());
		DiagramLine line1 = DiagramComponentFactory.getDiagramComponentFactory().createSolidLine(start1, end1, Color.BLACK, 2, frame.getContentPane());
		show(frame, TestCase.SHOW_DURATION);
	}
	
	@Test
	void straightLinesTest() {
		TestFrame frame = new TestFrame();
		
		PositionInFrame origin = new PositionInFrame(400, 400);
		PositionInFrame endX = new PositionInFrame(600, 400);
		PositionInFrame endY = new PositionInFrame(400, 200);
		DiagramLine xLine = DiagramComponentFactory.getDiagramComponentFactory().createSolidLine(origin, endX, Color.BLACK, 2, frame.getContentPane());
		DiagramLine yLine = DiagramComponentFactory.getDiagramComponentFactory().createSolidLine(origin, endY, Color.BLACK, 2, frame.getContentPane());
		show(frame, TestCase.SHOW_DURATION);
	}
	
	@Test
	void lengthTest() {
		TestFrame frame = new TestFrame();
		PositionInFrame corner = new PositionInFrame(200, 200);
		PositionInFrame endX = new PositionInFrame(203, 200);
		PositionInFrame endY = new PositionInFrame(200, 196);
		
		DiagramLine horizontalLine = DiagramComponentFactory.getDiagramComponentFactory().createSolidLine(corner, endX, Color.BLACK, 1, frame.getContentPane());
		DiagramLine verticalLine = DiagramComponentFactory.getDiagramComponentFactory().createSolidLine(corner, endY, Color.BLACK, 1, frame.getContentPane());
		DiagramLine hypotenuse = DiagramComponentFactory.getDiagramComponentFactory().createSolidLine(endY, endX, Color.BLACK, 1, frame.getContentPane());
		
		Assertions.assertEquals(3, horizontalLine.calculateLength());
		Assertions.assertEquals(4, verticalLine.calculateLength());
		Assertions.assertEquals(5, hypotenuse.calculateLength());
	}
	
	@Test
	void horizontalLengthTest() {
		TestFrame frame = new TestFrame();
		PositionInFrame corner = new PositionInFrame(200, 200);
		PositionInFrame endX = new PositionInFrame(203, 200);
		PositionInFrame endY = new PositionInFrame(200, 196);
		
		DiagramLine horizontalLine = DiagramComponentFactory.getDiagramComponentFactory().createSolidLine(corner, endX, Color.BLACK, 1, frame.getContentPane());
		DiagramLine verticalLine = DiagramComponentFactory.getDiagramComponentFactory().createSolidLine(corner, endY, Color.BLACK, 1, frame.getContentPane());
		DiagramLine hypotenuse = DiagramComponentFactory.getDiagramComponentFactory().createSolidLine(endY, endX, Color.BLACK, 1, frame.getContentPane());
		
		Assertions.assertEquals(3, horizontalLine.calculateHorizontalLength());
		Assertions.assertEquals(0, verticalLine.calculateHorizontalLength());
		Assertions.assertEquals(3, hypotenuse.calculateHorizontalLength());
	}
	
	@Test
	void verticalLengthTest() {
		TestFrame frame = new TestFrame();
		PositionInFrame corner = new PositionInFrame(200, 200);
		PositionInFrame endX = new PositionInFrame(203, 200);
		PositionInFrame endY = new PositionInFrame(200, 196);
		
		DiagramLine horizontalLine = DiagramComponentFactory.getDiagramComponentFactory().createSolidLine(corner, endX, Color.BLACK, 1, frame.getContentPane());
		DiagramLine verticalLine = DiagramComponentFactory.getDiagramComponentFactory().createSolidLine(corner, endY, Color.BLACK, 1, frame.getContentPane());
		DiagramLine hypotenuse = DiagramComponentFactory.getDiagramComponentFactory().createSolidLine(endY, endX, Color.BLACK, 1, frame.getContentPane());
		
		Assertions.assertEquals(0, horizontalLine.calculateVerticalLength());
		Assertions.assertEquals(4, verticalLine.calculateVerticalLength());
		Assertions.assertEquals(4, hypotenuse.calculateVerticalLength());
	}

	@Test
	void setStartTest() {
		TestFrame frame = new TestFrame();
		
		int thickness = 2;
		
		PositionInFrame start = new PositionInFrame(100, 100);
		PositionInFrame end = new PositionInFrame(400, 400);
		DiagramLine line = DiagramComponentFactory.getDiagramComponentFactory().createSolidLine(start, end, Color.BLACK, thickness, frame.getContentPane());
		
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
		TestFrame frame = new TestFrame();
		
		int thickness = 2;
		
		PositionInFrame start = new PositionInFrame(100, 100);
		PositionInFrame end = new PositionInFrame(400, 400);
		DiagramLine line = DiagramComponentFactory.getDiagramComponentFactory().createSolidLine(start, end, Color.BLACK, thickness, frame.getContentPane());
		
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
		TestFrame frame = new TestFrame();
		
		int thickness = 2;
		
		PositionInFrame start = new PositionInFrame(100, 100);
		PositionInFrame end = new PositionInFrame(400, 400);
		DiagramLine line = DiagramComponentFactory.getDiagramComponentFactory().createSolidLine(start, end, Color.BLACK, thickness, frame.getContentPane());
		
		line.setThickness(4);
		
		Assertions.assertEquals(line.getThickness(), 4);
	}
	
	@Test
	void showAndHideTest() {
		TestFrame frame = new TestFrame();
		
		int thickness = 5;
		
		PositionInFrame start = new PositionInFrame(100, 100);
		PositionInFrame end = new PositionInFrame(400, 400);
		DiagramLine line = DiagramComponentFactory.getDiagramComponentFactory().createSolidLine(start, end, Color.BLACK, thickness, frame.getContentPane());
		
		show(frame, line, 50);
	}
}
package gelf.view.diagrams.components;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.Test;

class DiagramLineTest implements TestCase {
	
	@Test
	void initialisationTest() {
		TestFrame frame = new TestFrame();
		
		PositionInFrame start = new PositionInFrame(100, 100);
		PositionInFrame end = new PositionInFrame(400, 400);
		DiagramLine line = DiagramComponentFactory.getDiagramComponentFactory().createSolidLine(start, end, Color.BLACK, 2, frame.getContentPane());
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
	}
	
	@Test
	void straightLinesTest() {
		TestFrame frame = new TestFrame();
		
		PositionInFrame origin = new PositionInFrame(400, 400);
		PositionInFrame endX = new PositionInFrame(600, 400);
		PositionInFrame endY = new PositionInFrame(400, 200);
		DiagramLine xLine = DiagramComponentFactory.getDiagramComponentFactory().createSolidLine(origin, endX, Color.BLACK, 2, frame.getContentPane());
		DiagramLine yLine = DiagramComponentFactory.getDiagramComponentFactory().createSolidLine(origin, endY, Color.BLACK, 2, frame.getContentPane());
		
//		show(frame);
	}

}
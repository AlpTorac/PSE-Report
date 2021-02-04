package gelf.view.diagrams.components;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.Test;

class DiagramAxisTest implements TestCase {

	@Test
	void initialisationTest() {
		TestFrame frame = new TestFrame();
		
		PositionInFrame start = new PositionInFrame(400, 400);
		PositionInFrame endX = new PositionInFrame(800, 400);
		DiagramAxis xAxis = DiagramComponentFactory.getDiagramComponentFactory().createSolidAxis(start, endX, 0, 20, 10, Color.black, 1, frame.getContentPane());
		xAxis.showValues();
//		show(frame);
	}
	
	@Test
	void verticalAxisTest() {
		TestFrame frame = new TestFrame();
		
		PositionInFrame start = new PositionInFrame(400, 400);
		PositionInFrame endX = new PositionInFrame(400, 200);
		DiagramAxis yAxis = DiagramComponentFactory.getDiagramComponentFactory().createSolidAxis(start, endX, 0, 20, 10, Color.black, 1, frame.getContentPane());
		yAxis.showValues();
//		show(frame);
	}
	
	@Test
	void coordinateSystemTest() {
		TestFrame frame = new TestFrame();
		
//		System.out.println("xAxis calculation - start");
		
		PositionInFrame start = new PositionInFrame(500, 500);
		
		PositionInFrame endX = new PositionInFrame(900, 500);
		DiagramAxis xAxis = DiagramComponentFactory.getDiagramComponentFactory().createSolidAxis(start, endX, 0, 20, 10, Color.black, 3, frame.getContentPane());
		xAxis.showValues();
		
//		System.out.println("xAxis calculation - end");
//		System.out.println("yAxis calculation - start");
		
		PositionInFrame endY = new PositionInFrame(500, 100);
		DiagramAxis yAxis = DiagramComponentFactory.getDiagramComponentFactory().createSolidAxis(start, endY, 0, 20, 10, Color.black, 3, frame.getContentPane());
		yAxis.showValues();
		
//		System.out.println("yAxis calculation - end");
//		System.out.println("xyAxis calculation - start");
		
		PositionInFrame endXY = new PositionInFrame(783, 217);
		DiagramAxis xyAxis = DiagramComponentFactory.getDiagramComponentFactory().createSolidAxis(start, endXY, 0, 20, 10, Color.black, 3, frame.getContentPane());
		xyAxis.showValues();
		
//		System.out.println("xyAxis calculation - end");
		
		show(frame);
	}

}

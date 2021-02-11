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
		show(frame, xAxis, 500);
	}
	
	@Test
	void verticalAxisTest() {
		TestFrame frame = new TestFrame();
		
		PositionInFrame start = new PositionInFrame(400, 400);
		PositionInFrame endX = new PositionInFrame(400, 200);
		DiagramAxis yAxis = DiagramComponentFactory.getDiagramComponentFactory().createSolidAxis(start, endX, 0, 20, 10, Color.black, 1, frame.getContentPane());
		yAxis.showValues();
		show(frame, yAxis, 500);
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
		
		show(frame, 500);
	}
	
	@Test
	void axisBloomTest() {
		TestFrame frame = new TestFrame();
		
		PositionInFrame start = new PositionInFrame(500, 500);
		
		PositionInFrame axis1End = new PositionInFrame(900, 500);
		DiagramAxis axis1 = DiagramComponentFactory.getDiagramComponentFactory().createSolidAxis(start, axis1End, 0, 20, 10, Color.black, 3, frame.getContentPane());
		axis1.showValues();
		axis1.showValuesUnderAxis();
		
		PositionInFrame axis2End = new PositionInFrame(783, 217);
		DiagramAxis axis2 = DiagramComponentFactory.getDiagramComponentFactory().createSolidAxis(start, axis2End, 0, 20, 10, Color.black, 3, frame.getContentPane());
		axis2.showValues();
		axis2.showValuesUnderAxis();
		
		PositionInFrame axis3End = new PositionInFrame(500, 100);
		DiagramAxis axis3 = DiagramComponentFactory.getDiagramComponentFactory().createSolidAxis(start, axis3End, 0, 20, 10, Color.black, 3, frame.getContentPane());
		axis3.showValues();
		axis3.showValuesAboveAxis();
		
		PositionInFrame axis4End = new PositionInFrame(217, 217);
		DiagramAxis axis4 = DiagramComponentFactory.getDiagramComponentFactory().createSolidAxis(start, axis4End, 0, 20, 10, Color.black, 3, frame.getContentPane());
		axis4.showValues();
		axis4.showValuesAboveAxis();
		
		PositionInFrame axis5End = new PositionInFrame(100, 500);
		DiagramAxis axis5 = DiagramComponentFactory.getDiagramComponentFactory().createSolidAxis(start, axis5End, 0, 20, 10, Color.black, 3, frame.getContentPane());
		axis5.showValues();
		axis5.showValuesAboveAxis();
		
		PositionInFrame axis6End = new PositionInFrame(217, 783);
		DiagramAxis axis6 = DiagramComponentFactory.getDiagramComponentFactory().createSolidAxis(start, axis6End, 0, 20, 10, Color.black, 3, frame.getContentPane());
		axis6.showValues();
		axis6.showValuesAboveAxis();
		
		PositionInFrame axis7End = new PositionInFrame(500, 900);
		DiagramAxis axis7 = DiagramComponentFactory.getDiagramComponentFactory().createSolidAxis(start, axis7End, 0, 20, 10, Color.black, 3, frame.getContentPane());
		axis7.showValues();
		axis7.showValuesUnderAxis();
		
		PositionInFrame axis8End = new PositionInFrame(783, 783);
		DiagramAxis axis8 = DiagramComponentFactory.getDiagramComponentFactory().createSolidAxis(start, axis8End, 0, 20, 10, Color.black, 3, frame.getContentPane());
		axis8.showValues();
		axis8.showValuesUnderAxis();
		
		show(frame, 500);
	}

}

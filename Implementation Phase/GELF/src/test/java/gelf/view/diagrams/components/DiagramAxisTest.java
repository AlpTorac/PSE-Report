package gelf.view.diagrams.components;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gelf.view.diagrams.TestCase;
import gelf.view.diagrams.TestFrame;

class DiagramAxisTest implements TestCase {

	private static TestFrame frame = new TestFrame();
	
	private static float startPosX = 400;
	private static float startPosY = 400;
	private static float endPosX = 800;
	private static float endPosY = 400;
	
	private static PositionInFrame start = new PositionInFrame(startPosX, startPosY);
	private static PositionInFrame endX = new PositionInFrame(endPosX, endPosY);
	
	private static int thickness = 1;
	private static Color color = Color.BLACK;
	
	private static float minVal = 0;
	private static float maxVal = 20;
	private static int steps = 10;
	
	private static DiagramAxis xAxis = DiagramComponentFactory.getDiagramComponentFactory().createSolidAxis(start, endX, minVal, maxVal, steps, color, thickness, frame.getContentPane());
	
	@Test
	void initialisationTest() {
		show(frame, xAxis, TestCase.SHOW_DURATION);
	}
	
	@Test
	void verticalAxisTest() {
		TestFrame frame = new TestFrame();
		
		PositionInFrame start = new PositionInFrame(400, 400);
		PositionInFrame endX = new PositionInFrame(400, 200);
		DiagramAxis yAxis = DiagramComponentFactory.getDiagramComponentFactory().createSolidAxis(start, endX, 0, 20, 10, Color.black, 1, frame.getContentPane());
		yAxis.showValues();
		show(frame, yAxis, TestCase.SHOW_DURATION);
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
		
		show(frame, TestCase.SHOW_DURATION);
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
		
		show(frame, TestCase.SHOW_DURATION);
	}

	@Test
	void lineLengthTest() {
		double hLen = endX.getXPos() - start.getXPos();
		double vLen = endX.getYPos() - start.getYPos();
		
		double length = Math.sqrt(hLen * hLen + vLen * vLen);
		
		Assertions.assertEquals(length, xAxis.getLineLength());
	}
	
	@Test
	void setLineThicknessTest() {
		int newThickness = 5;
		
		xAxis.setLineThickness(newThickness);
		
		Assertions.assertEquals(newThickness, xAxis.getLineThickness());
		Assertions.assertEquals(newThickness, xAxis.axisLine.getThickness());
		
		show(frame, xAxis, TestCase.SHOW_DURATION);
		
		xAxis.setLineThickness(thickness);
	}
	
	@Test
	void setColorTest() {
		Color newColor = Color.PINK;
		
		xAxis.setColor(newColor);
		
		Assertions.assertEquals(newColor, xAxis.getColor());
		Assertions.assertEquals(color, xAxis.axisLine.getColor());
		
		show(frame, xAxis, TestCase.SHOW_DURATION);
		
		xAxis.setColor(color);
	}
	
	@Test
	void setLineColorTest() {
		Color newColor = Color.PINK;
		
		xAxis.setLineColor(newColor);
		
		Assertions.assertEquals(color, xAxis.getColor());
		Assertions.assertEquals(newColor, xAxis.axisLine.getColor());
		
		show(frame, xAxis, TestCase.SHOW_DURATION);
		
		xAxis.setLineColor(color);
	}
	
	@Test
	void setLinePosTest() {
		float newStartPosX = 100;
		float newStartPosY = 100;
		float newEndPosX = 800;
		float newEndPosY = 100;
		
		xAxis.setLineByPos(newStartPosX, newStartPosY, newEndPosX, newEndPosY);
		
		Assertions.assertEquals(newStartPosX, xAxis.getLineStart().getXPos());
		Assertions.assertEquals(newStartPosY, xAxis.getLineStart().getYPos());
		Assertions.assertEquals(newEndPosX, xAxis.getLineEnd().getXPos());
		Assertions.assertEquals(newEndPosY, xAxis.getLineEnd().getYPos());
		
		show(frame, xAxis, TestCase.SHOW_DURATION);
		
		xAxis.setLineByPos(startPosX, startPosY, endPosX, endPosY);
	}
	
	@Test
	void setAxisLinePropertiesTest() {
		float newMinVal = 5;
		float newMaxVal = 10;
		int newSteps = 5;
		
		xAxis.setMin(newMinVal);
		xAxis.setMax(newMaxVal);
		xAxis.setSteps(newSteps);
		
		Assertions.assertEquals(newMinVal, xAxis.getMin());
		Assertions.assertEquals(newMaxVal, xAxis.getMax());
		Assertions.assertEquals(newSteps, xAxis.getSteps());
		
		float valueOnAxis = 6;
		float diffFromStart = valueOnAxis - xAxis.getMin();
		
		double hDiff = xAxis.getLineEnd().getXPos() - xAxis.getLineStart().getXPos();
		double vDiff = xAxis.getLineEnd().getYPos() - xAxis.getLineStart().getYPos();
		
		PositionInFrame valuePosInFrame = new PositionInFrame(xAxis.getLineStart(),
				diffFromStart * hDiff / ((float) xAxis.getSteps()),
				diffFromStart * vDiff / ((float) xAxis.getSteps()));
		
		Assertions.assertTrue(compareFloatingPoint(valuePosInFrame.getYPos(), xAxis.valueToCoordinate(valueOnAxis).getYPos()));
		Assertions.assertTrue(compareFloatingPoint(valuePosInFrame.getXPos(), xAxis.valueToCoordinate(valueOnAxis).getXPos()));

		show(frame, xAxis, TestCase.SHOW_DURATION);
		
		xAxis.setMin(minVal);
		xAxis.setMax(maxVal);
		xAxis.setSteps(steps);
	}

}

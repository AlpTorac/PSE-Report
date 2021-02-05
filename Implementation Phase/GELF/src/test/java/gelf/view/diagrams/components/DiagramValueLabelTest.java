package gelf.view.diagrams.components;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DiagramValueLabelTest implements TestCase {

	private static TestFrame frame = new TestFrame();
	
	private static PositionInFrame start = new PositionInFrame(400, 400);
	private static PositionInFrame endX = new PositionInFrame(800, 400);
	private static PositionInFrame endY = new PositionInFrame(400, 100);

	private static DiagramAxis yAxis = DiagramComponentFactory.getDiagramComponentFactory().createSolidAxis(start, endY, 0, 20, 10, Color.black, 1, frame.getContentPane());
	private static DiagramAxis xAxis = DiagramComponentFactory.getDiagramComponentFactory().createSolidAxis(start, endX, 0, 20, 10, Color.black, 1, frame.getContentPane());
	
	private static PositionIn2DDiagram topLeftVL = new PositionIn2DDiagram(xAxis, 5, yAxis, 5);
	private static PositionIn2DDiagram bottomRightVL = new PositionIn2DDiagram(xAxis, 10, yAxis, 0);
	
	private static PositionInFrame topLeftCS = new PositionInFrame(400, 400);
	private static PositionInFrame bottomRightCS = new PositionInFrame(700, 600);
	
	private static DiagramColorScale cs = DiagramComponentFactory.getDiagramComponentFactory().createBiColorScale(topLeftCS, bottomRightCS, Color.BLACK, 0, 20, Color.RED, Color.BLUE, 1, frame.getContentPane());
	
	private static DiagramValueLabel label = DiagramComponentFactory.getDiagramComponentFactory().createHeatMapLabel(topLeftVL, bottomRightVL, cs, 5, 1, frame.getContentPane());
	
	
	@Test
	void initialisationTest() {
		yAxis.showValues();
		xAxis.showValues();
		show(frame, TestCase.SHOW_DURATION);
	}
	
	@Test
	void setPositionTest() {
		double oldTopLeftX = 5;
		double oldTopLeftY = 5;
		double oldBottomRightX = 10;
		double oldBottomRightY = 0;
		
		double topLeftX = 1;
		double topLeftY = 10;
		double bottomRightX = 5;
		double bottomRightY = 0;
		
		PositionIn2DDiagram topL = new PositionIn2DDiagram(xAxis, topLeftX, yAxis, topLeftY);
		PositionIn2DDiagram bottomR = new PositionIn2DDiagram(xAxis, bottomRightX, yAxis, bottomRightY);
		
		label.setTopLeftInDiagram(topLeftX, topLeftY);
		label.setBottomRightInDiagram(bottomRightX, bottomRightY);
		
		Assertions.assertEquals(topLeftX, label.getTopLeftInDiagram().getXCoordinate());
		Assertions.assertEquals(topLeftY, label.getTopLeftInDiagram().getYCoordinate());
		Assertions.assertEquals(bottomRightX, label.getBottomRightInDiagram().getXCoordinate());
		Assertions.assertEquals(bottomRightY, label.getBottomRightInDiagram().getYCoordinate());
		
		Assertions.assertTrue(compareFloatingPoint(topL.toPositionInFrame().getXPos(), label.visualElement.getBounds().getMinX()));
		Assertions.assertTrue(compareFloatingPoint(topL.toPositionInFrame().getYPos(), label.visualElement.getBounds().getMinY()));
		Assertions.assertTrue(compareFloatingPoint(bottomR.toPositionInFrame().getXPos(), label.visualElement.getBounds().getMaxX()));
		Assertions.assertTrue(compareFloatingPoint(bottomR.toPositionInFrame().getYPos(), label.visualElement.getBounds().getMaxY()));
		
//		show(frame, label, TestCase.SHOW_DURATION);
		
		label.setTopLeftInDiagram(oldTopLeftX, oldTopLeftY);
		label.setBottomRightInDiagram(oldBottomRightX, oldBottomRightY);
		
//		show(frame, label, TestCase.SHOW_DURATION);
	}

	@Test
	void setValueTest() {
		float oldValue = label.getValue();
		float newValue = 10;
		
		label.setValue(newValue);
		
		Assertions.assertEquals(newValue, label.getValue());
		
		show(frame, label, TestCase.SHOW_DURATION);
		
		label.setValue(oldValue);
		
		show(frame, label, TestCase.SHOW_DURATION);
	}
	
	@Test
	void setThicknessTest() {
		int oldThickness = label.getBorderThickness();
		int newThickness = 5;
		
		label.setBorderThickness(newThickness);
		
//		show(frame, label, TestCase.SHOW_DURATION);
		
		label.setBorderThickness(oldThickness);
	}
	
}

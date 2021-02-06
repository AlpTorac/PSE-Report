package gelf.view.diagrams.components;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DiagramPointTest implements TestCase {

	private static TestFrame frame = new TestFrame();
	
	private static PositionInFrame start = new PositionInFrame(400, 400);
	private static PositionInFrame endX = new PositionInFrame(800, 400);
	private static PositionInFrame endY = new PositionInFrame(400, 100);

	private static DiagramAxis yAxis = DiagramComponentFactory.getDiagramComponentFactory().createSolidAxis(start, endY, 0, 20, 10, Color.black, 1, frame.getContentPane());
	private static DiagramAxis xAxis = DiagramComponentFactory.getDiagramComponentFactory().createSolidAxis(start, endX, 0, 20, 10, Color.black, 1, frame.getContentPane());

	private static PositionIn2DDiagram pos = new PositionIn2DDiagram(xAxis, 10, yAxis, 10);
	
	private static float value = 10f;
	private static float size = 20f;
	
	private static Color color = new Color(255,0,0,100);
	
	private static DiagramPoint p = DiagramComponentFactory.getDiagramComponentFactory().createValueDisplayPoint(color, value, size, pos, frame.getContentPane());
	
	@Test
	void initialisationTest() {
		xAxis.showValues();
		yAxis.showValues();
		show(frame, p, TestCase.SHOW_DURATION);
	}
	
	@Test
	void setXPositionTest() {
		double oldXPos = pos.getXCoordinate();
		double newXPos = 2;
		
		PositionIn2DDiagram newPos = new PositionIn2DDiagram(xAxis, newXPos, yAxis, pos.getYCoordinate());
		
		p.setXPositionInDiagram(newXPos);
		
		Assertions.assertEquals(newXPos, p.getPositionInDiagram().getXCoordinate());
		Assertions.assertEquals(value, p.getValue());
		
		Assertions.assertTrue(compareFloatingPoint(newPos.toPositionInFrame().getXPos(), p.visualElement.getBounds().getMinX()));
		Assertions.assertEquals(newPos.toPositionInFrame().getYPos(), p.visualElement.getBounds().getMinY());
		Assertions.assertEquals(Math.ceil(newPos.toPositionInFrame().getXPos() + size / 2f), p.visualElement.getBounds().getMaxX());
		Assertions.assertEquals(Math.ceil(newPos.toPositionInFrame().getYPos() + size / 2f), p.visualElement.getBounds().getMaxY());
		
		show(frame, p, TestCase.SHOW_DURATION);
		
		p.setXPositionInDiagram(oldXPos);
	}
	
	@Test
	void setValueTest() {
		float newValue = 2f;
		PositionIn2DDiagram newPos = new PositionIn2DDiagram(xAxis, pos.getXCoordinate(), yAxis, newValue);
		
		p.setValue(newValue);
		
		Assertions.assertEquals(pos.getXCoordinate(), p.getPositionInDiagram().getXCoordinate());
		Assertions.assertEquals(newValue, p.getValue());
		
		Assertions.assertEquals(Math.floor(newPos.toPositionInFrame().getXPos()), p.visualElement.getBounds().getMinX());
		Assertions.assertEquals(Math.floor(newPos.toPositionInFrame().getYPos()), p.visualElement.getBounds().getMinY());
		Assertions.assertEquals(Math.ceil(newPos.toPositionInFrame().getXPos() + size / 2f), p.visualElement.getBounds().getMaxX());
		Assertions.assertEquals(Math.ceil(newPos.toPositionInFrame().getYPos() + size / 2f), p.visualElement.getBounds().getMaxY());
		
		show(frame, p, TestCase.SHOW_DURATION);
		
		p.setValue(value);
	}
	
	@Test
	void setSizeTest() {
		float newSize = 10;
		
		p.setSize(newSize);
		
		Assertions.assertEquals(Math.floor(pos.toPositionInFrame().getXPos()), p.visualElement.getBounds().getMinX());
		Assertions.assertEquals(Math.floor(pos.toPositionInFrame().getYPos()), p.visualElement.getBounds().getMinY());
		Assertions.assertEquals(Math.ceil(pos.toPositionInFrame().getXPos() + newSize / 2f), p.visualElement.getBounds().getMaxX());
		Assertions.assertEquals(Math.ceil(pos.toPositionInFrame().getYPos() + newSize / 2f), p.visualElement.getBounds().getMaxY());
		
		show(frame, p, TestCase.SHOW_DURATION);
		
		p.setSize(size);
	}
}
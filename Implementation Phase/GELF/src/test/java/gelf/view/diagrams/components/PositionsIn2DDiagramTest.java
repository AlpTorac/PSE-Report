package gelf.view.diagrams.components;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.awt.Container;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gelf.view.diagrams.TestCase;
import gelf.view.diagrams.TestFrame;

class PositionsIn2DDiagramTest implements TestCase {

	private TestFrame frame = new TestFrame();
	private Container container = frame.getContentPane();
	
	private PositionInFrame origin = new PositionInFrame(400, 400);
	private PositionInFrame xAxisEnd = new PositionInFrame(500, 400);
	private PositionInFrame yAxisEnd = new PositionInFrame(400, 300);
	
	private DiagramAxis xAxis = DiagramComponentFactory.getDiagramComponentFactory().createSolidAxis(origin, xAxisEnd, 0, 20, 10, Color.BLACK, 10);
	private DiagramAxis yAxis = DiagramComponentFactory.getDiagramComponentFactory().createSolidAxis(origin, yAxisEnd, 0, 20, 10, Color.BLACK, 10);
	
	@Test
	void initialisationTest() {
		PositionIn2DDiagram pos = new PositionIn2DDiagram(xAxis, 5, yAxis, 10);
		
		Assertions.assertEquals(pos.getXCoordinate(), 5);
		Assertions.assertEquals(pos.getYCoordinate(), 10);
	}
	
	@Test
	void settingXTest() {
		PositionIn2DDiagram pos = new PositionIn2DDiagram(xAxis, 15, yAxis, 20);
		
		pos.setXCoordinate(5);
		
		Assertions.assertEquals(pos.getXCoordinate(), 5);
		Assertions.assertEquals(pos.getYCoordinate(), 20);
	}
	
	@Test
	void settingYTest() {
		PositionIn2DDiagram pos = new PositionIn2DDiagram(xAxis, 2, yAxis, 3);
		
		pos.setYCoordinate(5);
		
		Assertions.assertEquals(pos.getXCoordinate(), 2);
		Assertions.assertEquals(pos.getYCoordinate(), 5);
	}
	
	@Test
	void settingXYTest() {
		PositionInDiagram pos = new PositionIn2DDiagram(xAxis, 2, yAxis, 3);
		
		pos.setAxisCoordinates(new double[] {5, 8});
		
		Assertions.assertEquals(pos.getAxisPos(0), 5);
		Assertions.assertEquals(pos.getAxisPos(1), 8);
	}

	@Test
	void cloneTest() {
		PositionIn2DDiagram pos = new PositionIn2DDiagram(xAxis, 1, yAxis, 1);
		PositionIn2DDiagram posClone = pos.clone();
		
		Assertions.assertEquals(pos.getXCoordinate(), posClone.getXCoordinate());
		Assertions.assertEquals(pos.getYCoordinate(), posClone.getYCoordinate());
	}
	
	@Test
	void framePosTranslationTest() {
		PositionIn2DDiagram pos = new PositionIn2DDiagram(xAxis, 5, yAxis, 10);
		PositionInFrame framePos = pos.toPositionInFrame();
		
		Assertions.assertEquals(framePos.getXPos(), 425);
		Assertions.assertEquals(framePos.getYPos(), 350);
	}
}

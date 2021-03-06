package gelf.view.diagrams.components;

import java.awt.Color;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gelf.view.diagrams.TestCase;
import gelf.view.diagrams.TestFrame;
import gelf.view.diagrams.type.BarChart;

class BarChartBarTest implements TestCase {

	private static TestFrame frame = new TestFrame();
	
	private static PositionInFrame start = new PositionInFrame(400, 400);
	private static PositionInFrame endX = new PositionInFrame(800, 400);
	private static PositionInFrame endY = new PositionInFrame(400, 100);

	private static DiagramAxis yAxis = DiagramComponentFactory.getDiagramComponentFactory().createSolidAxis(start, endY, 0, 20, 10, Color.black, 1);

	private static DiagramAxis xAxis = DiagramComponentFactory.getDiagramComponentFactory().createSolidAxis(start, endX, 0, 20, 10, Color.black, 1);

	private static double tlXpos = 5;
	private static double tlYpos = 10;
	private static double brXpos = 7;
	private static double brYpos = 0;
	
	private static PositionIn2DDiagram topLeft = new PositionIn2DDiagram(xAxis, tlXpos, yAxis, tlYpos);
	private static PositionIn2DDiagram bottomRight = new PositionIn2DDiagram(xAxis, brXpos, yAxis, brYpos);
	
	private static int thickness = 1;
	private static float value = 10f;
	private static Color color = new Color(255, 0, 0, 100);
	
	private static BarChartBar bar = DiagramComponentFactory.getDiagramComponentFactory().createBarChartBar(color, value, topLeft, bottomRight, thickness);
	
	private static void initAndShow() {
		BarChart diagram = new BarChart(null, new DiagramAxis[] {xAxis, yAxis},
				new DiagramValueDisplayComponent[] {bar}, null);
		
		diagram.attachToContainer(frame);
		yAxis.showValues();
		xAxis.showValues();
		TestCase.showStatic(frame, TestCase.SHOW_DURATION);
		diagram.removeFromContainer();
	}
	
	@Test
	void initialisationTest() {
		initAndShow();
	}
	
	@Test
	void setThicknessTest() {
		int newThickness = 5;
		
		bar.setBorderThickness(newThickness);
		
		Assertions.assertEquals(newThickness, bar.getBorderThickness());

		initAndShow();
		
		bar.setBorderThickness(thickness);
	}
	
	@Test
	void setPositionTest() {
		double newTLxPos = 10;
		double newBRxPos = 20;
		double newBRyPos = 0;
		
		PositionIn2DDiagram newTopLeft = new PositionIn2DDiagram(xAxis, newTLxPos, yAxis, tlYpos);
		PositionIn2DDiagram newBottomRight = new PositionIn2DDiagram(xAxis, newBRxPos, yAxis, brYpos);
		
		bar.setPositionInDiagram(newTLxPos, newBRxPos, newBRyPos);
		
		Assertions.assertEquals(newTopLeft.toPositionInFrame().getXPos(), bar.getTopLeftInDiagram().toPositionInFrame().getXPos());
		Assertions.assertEquals(newTopLeft.toPositionInFrame().getYPos(), bar.getTopLeftInDiagram().toPositionInFrame().getYPos());
		Assertions.assertEquals(newBottomRight.toPositionInFrame().getXPos(), bar.getBottomRightInDiagram().toPositionInFrame().getXPos());
		Assertions.assertEquals(newBottomRight.toPositionInFrame().getYPos(), bar.getBottomRightInDiagram().toPositionInFrame().getYPos());
	
		Assertions.assertEquals(newTopLeft.toPositionInFrame().getXPos(), bar.visualElement.getBounds().getMinX());
		Assertions.assertEquals(newTopLeft.toPositionInFrame().getYPos(), bar.visualElement.getBounds().getMinY());
		Assertions.assertEquals(newBottomRight.toPositionInFrame().getXPos(), bar.visualElement.getBounds().getMaxX());
		Assertions.assertEquals(newBottomRight.toPositionInFrame().getYPos(), bar.visualElement.getBounds().getMaxY());
		
		initAndShow();
		
		bar.setPositionInDiagram(tlXpos, brXpos, brYpos);
	}
	
	@Test
	void getHeightTest() {
		Assertions.assertEquals(value, bar.getHeight());
	}
	
	@Test
	void getWidthTest() {
		double difference = bottomRight.getXCoordinate() - topLeft.getXCoordinate();
		
		Assertions.assertTrue(compareFloatingPoint(difference, bar.getWidth()));
	}

}

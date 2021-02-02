package gelf.view.diagrams.components;

import java.awt.Color;

import org.junit.jupiter.api.Test;

class BarChartBarTest {

	@Test
	void initialisationTest() {
		TestFrame frame = new TestFrame();
		
		PositionInFrame start = new PositionInFrame(400, 400);
		PositionInFrame endX = new PositionInFrame(500, 400);
		PositionInFrame endY = new PositionInFrame(400, 300);
		DiagramAxis xAxis = DiagramComponentFactory.getDiagramComponentFactory().createSolidAxis(start, endX, 0, 20, 10, Color.BLACK, 10, frame.getContentPane());
		DiagramAxis yAxis = DiagramComponentFactory.getDiagramComponentFactory().createSolidAxis(start, endY, 0, 20, 10, Color.BLACK, 10, frame.getContentPane());
		
		xAxis.hide();
		yAxis.hide();
		
		PositionIn2DDiagram topLeft = new PositionIn2DDiagram(xAxis, 5, yAxis, 10);
		PositionIn2DDiagram bottomRight = new PositionIn2DDiagram(xAxis, 7, yAxis, 0);
		
		@SuppressWarnings("unused")
		BarChartBar bar = new BarChartBar(Color.WHITE, 10, topLeft, bottomRight, 2, frame.getContentPane());
		while (true) {
			
		}
	}

}

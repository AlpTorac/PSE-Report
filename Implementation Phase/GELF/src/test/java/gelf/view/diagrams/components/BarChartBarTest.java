package gelf.view.diagrams.components;

import java.awt.Color;

import org.junit.jupiter.api.Test;

class BarChartBarTest {

	@Test
	void initialisationTest() {
		PositionInFrame start = new PositionInFrame(400, 400);
		PositionInFrame endX = new PositionInFrame(500, 400);
		PositionInFrame endY = new PositionInFrame(400, 300);
		DiagramAxis xAxis = DiagramComponentFactory.getDiagramComponentFactory().createSolidAxis(start, endX, 0, 20, 10, Color.BLACK, 10);
		DiagramAxis yAxis = DiagramComponentFactory.getDiagramComponentFactory().createSolidAxis(start, endY, 0, 20, 10, Color.BLACK, 10);
		
		PositionIn2DDiagram topLeft = new PositionIn2DDiagram(xAxis, 5, yAxis, 10);
		PositionIn2DDiagram bottomRight = new PositionIn2DDiagram(xAxis, 7, yAxis, 0);
		
		BarChartBar bar = new BarChartBar(Color.WHITE, 10, topLeft, bottomRight, 2);
		
		TestFrame frame = new TestFrame();
		frame.add(bar.visualElement);
		while (true) {
			
		}
	}

}

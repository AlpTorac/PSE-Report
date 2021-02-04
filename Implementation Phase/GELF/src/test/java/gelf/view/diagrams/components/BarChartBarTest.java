package gelf.view.diagrams.components;

import java.awt.Color;

import org.junit.jupiter.api.Test;

class BarChartBarTest implements TestCase {

	@Test
	void initialisationTest() {
		TestFrame frame = new TestFrame();
		
		PositionInFrame start = new PositionInFrame(400, 400);
		PositionInFrame endX = new PositionInFrame(800, 400);
		PositionInFrame endY = new PositionInFrame(400, 100);

		DiagramAxis yAxis = DiagramComponentFactory.getDiagramComponentFactory().createSolidAxis(start, endY, 0, 20, 10, Color.black, 1, frame.getContentPane());
		yAxis.showValues();
		DiagramAxis xAxis = DiagramComponentFactory.getDiagramComponentFactory().createSolidAxis(start, endX, 0, 20, 10, Color.black, 1, frame.getContentPane());
		xAxis.showValues();
		PositionIn2DDiagram topLeft = new PositionIn2DDiagram(xAxis, 5, yAxis, 10);
		PositionIn2DDiagram bottomRight = new PositionIn2DDiagram(xAxis, 7, yAxis, 0);
		
		BarChartBar bar = new BarChartBar(new Color(255, 0, 0, 100), 10, topLeft, bottomRight, 1, frame.getContentPane());
		
//		show(frame);
	}

}

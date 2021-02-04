package gelf.view.diagrams.components;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.Test;

class DiagramValueLabelTest implements TestCase {

	@Test
	void test() {
		TestFrame frame = new TestFrame();
		
		PositionInFrame start = new PositionInFrame(400, 400);
		PositionInFrame endX = new PositionInFrame(800, 400);
		PositionInFrame endY = new PositionInFrame(400, 100);

		DiagramAxis yAxis = DiagramComponentFactory.getDiagramComponentFactory().createSolidAxis(start, endY, 0, 20, 10, Color.black, 1, frame.getContentPane());
		yAxis.showValues();
		DiagramAxis xAxis = DiagramComponentFactory.getDiagramComponentFactory().createSolidAxis(start, endX, 0, 20, 10, Color.black, 1, frame.getContentPane());
		xAxis.showValues();
		PositionIn2DDiagram topLeftVL = new PositionIn2DDiagram(xAxis, 5, yAxis, 5);
		PositionIn2DDiagram bottomRightVL = new PositionIn2DDiagram(xAxis, 10, yAxis, 0);
		
		PositionInFrame topLeftCS = new PositionInFrame(400, 400);
		PositionInFrame bottomRightCS = new PositionInFrame(700, 600);
		
		DiagramColorScale cs = DiagramComponentFactory.getDiagramComponentFactory().createBiColorScale(topLeftCS, bottomRightCS, Color.BLACK, 0, 20, Color.RED, Color.BLUE, 1, frame.getContentPane());
		
		DiagramValueLabel label = DiagramComponentFactory.getDiagramComponentFactory().createHeatMapLabel(topLeftVL, bottomRightVL, cs, 5, 1, frame.getContentPane());
		
		show(frame);
	}

}

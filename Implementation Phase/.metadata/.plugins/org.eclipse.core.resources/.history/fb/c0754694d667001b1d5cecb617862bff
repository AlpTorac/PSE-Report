package gelf.view.diagrams.components;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.Test;

class DiagramColorScaleTest implements TestCase {

	@Test
	void initialisationTest() {
		TestFrame frame = new TestFrame();
		
		PositionInFrame topLeft = new PositionInFrame(400, 400);
		PositionInFrame bottomRight = new PositionInFrame(700, 600);
		
		DiagramColorScale cs = DiagramComponentFactory.getDiagramComponentFactory().createBiColorScale(topLeft, bottomRight, Color.BLACK, 0, 100, Color.RED, Color.BLUE, 1, frame.getContentPane());
		show(frame, cs, 500);
	}
}

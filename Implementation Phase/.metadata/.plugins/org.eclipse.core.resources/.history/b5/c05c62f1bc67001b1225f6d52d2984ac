package gelf.view.diagrams.components;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.Test;

class DiagramLabelTest implements TestCase{

	@Test
	void initialisationTest() {
		TestFrame frame = new TestFrame();
		
		PositionInFrame topLeft = new PositionInFrame(400, 400);
		PositionInFrame bottomRight = new PositionInFrame(600, 500);
		
		DiagramLabel label = DiagramComponentFactory.getDiagramComponentFactory().createDescriptionLabel(topLeft, bottomRight, Color.RED, "Testing \n DiagramLabel", 0, frame.getContentPane());
		
		show(frame);
	}

}

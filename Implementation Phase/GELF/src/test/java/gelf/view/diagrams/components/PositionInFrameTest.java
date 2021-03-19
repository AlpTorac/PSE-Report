package gelf.view.diagrams.components;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gelf.view.diagrams.TestCase;

class PositionInFrameTest implements TestCase {

	@Test
	void initialisationTest() {
		PositionInFrame pos = new PositionInFrame(21, 453);
		
		Assertions.assertEquals(pos.getXPos(), 21);
		Assertions.assertEquals(pos.getYPos(), 453);
	}
	
	@Test
	void initialisationTestWithReference() {
		PositionInFrame pos = new PositionInFrame(21, 453);
		PositionInFrame pos2 = new PositionInFrame(pos, 4, 7);
		
		Assertions.assertEquals(pos2.getXPos(), 25);
		Assertions.assertEquals(pos2.getYPos(), 460);
	}
	
	@Test
	void settingXTest() {
		PositionInFrame pos = new PositionInFrame(10, 20);
		
		pos.setXPos(5);
		
		Assertions.assertEquals(pos.getXPos(), 5);
		Assertions.assertEquals(pos.getYPos(), 20);
	}
	
	@Test
	void settingYTest() {
		PositionInFrame pos = new PositionInFrame(30, 45);
		
		pos.setYPos(5);
		
		Assertions.assertEquals(pos.getXPos(), 30);
		Assertions.assertEquals(pos.getYPos(), 5);
	}

	@Test
	void cloneTest() {
		PositionInFrame pos = new PositionInFrame(91, 21);
		PositionInFrame posClone = pos.clone();
		
		Assertions.assertEquals(pos.getXPos(), posClone.getXPos());
		Assertions.assertEquals(pos.getYPos(), posClone.getYPos());
		
		Assertions.assertTrue(pos.equals(posClone));
	}
}

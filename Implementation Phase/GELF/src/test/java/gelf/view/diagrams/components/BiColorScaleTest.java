package gelf.view.diagrams.components;

import java.awt.Color;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gelf.view.diagrams.TestCase;
import gelf.view.diagrams.TestFrame;
import gelf.view.diagrams.type.HeatMap;

class BiColorScaleTest implements TestCase {

	private static TestFrame frame = new TestFrame();
	
	private static Color borderColor = Color.BLACK;
	private static Color minValColor = Color.RED;
	private static Color maxValColor = Color.BLUE;
	
	private static float minVal = 0;
	private static float maxVal = 100;
	
	private static PositionInFrame topLeft = new PositionInFrame(400, 400);
	private static PositionInFrame bottomRight = new PositionInFrame(700, 600);
	
	private static BiColorScale cs = DiagramComponentFactory.getDiagramComponentFactory().createBiColorScale(topLeft, bottomRight, borderColor, minVal, maxVal, minValColor, maxValColor, 1);
	
	private static void initAndShow() {
		HeatMap diagram = new HeatMap(null, null,
				null, new DiagramComponent[] {cs});
		
		diagram.attachToContainer(frame);
		TestCase.showStatic(frame, TestCase.SHOW_DURATION);
		diagram.removeFromContainer();
	}
	
	@Test
	void showAndHideTest() {
		initAndShow();
	}
	
	@Test
	void setColorTest() {
		Color newMinValColor = Color.CYAN;
		Color newMaxValColor = Color.GRAY;
		
		cs.setMinValueColor(newMinValColor);
		cs.setMinValueColor(newMaxValColor);
		
		initAndShow();
		
		cs.setMinValueColor(minValColor);
		cs.setMaxValueColor(maxValColor);
	}
	
	@Test
	void setValueTest() {
		float newMinVal = 50;
		float newMaxVal = 60;
		
		cs.setMaxValue(newMaxVal);
		cs.setMinValue(newMinVal);
		
		initAndShow();
		
		Assertions.assertEquals(cs.valueToColor(55), Color.GREEN);
		
		cs.setMaxValue(maxVal);
		cs.setMinValue(minVal);
	}

}

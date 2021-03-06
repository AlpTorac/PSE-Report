package gelf.view.diagrams.components;

import java.awt.Color;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gelf.view.diagrams.TestCase;
import gelf.view.diagrams.TestFrame;
import gelf.view.diagrams.type.HeatMap;

class DiagramColorScaleTest implements TestCase {

	private static TestFrame frame = new TestFrame();
	
	private static Color borderColor = Color.BLACK;
	private static Color minValColor = Color.RED;
	private static Color maxValColor = Color.BLUE;
	
	private static float minVal = 0;
	private static float maxVal = 100;
	
	private static PositionInFrame topLeft = new PositionInFrame(400, 400);
	private static PositionInFrame bottomRight = new PositionInFrame(700, 600);
	
	private static int thickness = 1;
	
	private static DiagramColorScale cs = DiagramComponentFactory.getDiagramComponentFactory().createBiColorScale(topLeft, bottomRight, borderColor, minVal, maxVal, minValColor, maxValColor, thickness);
	
	private static void initAndShow() {
		HeatMap diagram = new HeatMap(null, null,
				null, new DiagramComponent[] {cs});
		
		diagram.attachToContainer(frame);
		TestCase.showStatic(frame, TestCase.SHOW_DURATION);
		frame.remove(diagram.getContainingElement());
	}
	
	@Test
	void initialisationTest() {
		initAndShow();
	}
	
	@Test
	void setValueTest() {
		float newMinVal = 50;
		float newMaxVal = 60;
		
		cs.setValue(1, newMaxVal);
		cs.setValue(0, newMinVal);
		
//		show(frame, cs, TestCase.SHOW_DURATION);
		
		Assertions.assertEquals(cs.valueToColor(55), Color.GREEN);
		
		cs.setValue(1, maxVal);
		cs.setValue(0, minVal);
	}
	
	@Test
	void setValuesTest() {
		float newMinVal = 50;
		float newMaxVal = 60;
		
		cs.setValues(new float[] {newMinVal, newMaxVal});
		
//		show(frame, cs, TestCase.SHOW_DURATION);
		
		Assertions.assertEquals(cs.valueToColor(55), Color.GREEN);
		
		cs.setValues(new float[] {minVal, maxVal});
	}
	
	@Test
	void getValueTest() {
		Assertions.assertEquals(minVal, cs.getValue(0));
		Assertions.assertEquals(maxVal, cs.getValue(1));
	}
	
	@Test
	void getValuesTest() {
		Assertions.assertArrayEquals(new float[] {minVal, maxVal}, cs.getValues());
	}
	
	@Test
	void boundReSetTest() {
		double oldTopLX = cs.getTopLeftInFrame().getXPos();
		double oldTopLY = cs.getTopLeftInFrame().getYPos();
		double oldBottomRX = cs.getBottomRightInFrame().getXPos();
		double oldBottomRY = cs.getBottomRightInFrame().getYPos();
		
		double newTopLX = 100;
		double newTopLY = 100;
		double newBottomRX = 300;
		double newBottomRY = 200;
		
		cs.setTopLeftInFrame(newTopLX, newTopLY);
		cs.setBottomRightInFrame(newBottomRX, newBottomRY);
		
		Assertions.assertEquals(newTopLX, cs.getTopLeftInFrame().getXPos());
		Assertions.assertEquals(newTopLY, cs.getTopLeftInFrame().getYPos());
		Assertions.assertEquals(newBottomRX, cs.getBottomRightInFrame().getXPos());
		Assertions.assertEquals(newBottomRY, cs.getBottomRightInFrame().getYPos());
		
		Assertions.assertEquals(newTopLX, cs.visualElement.getBounds().getMinX());
		Assertions.assertEquals(newTopLY, cs.visualElement.getBounds().getMinY());
		Assertions.assertEquals(newBottomRX, cs.visualElement.getBounds().getMaxX());
		Assertions.assertEquals(newBottomRY, cs.visualElement.getBounds().getMaxY());
		
		initAndShow();
		
		cs.setTopLeftInFrame(oldTopLX, oldTopLY);
		cs.setBottomRightInFrame(oldBottomRX, oldBottomRY);
	}

	@Test
	void getBorderColorTest() {
		Assertions.assertEquals(borderColor, cs.getColor());
	}
	
	@Test
	void getValueColorTest() {
		Assertions.assertEquals(minValColor, cs.getValueColor(0));
		Assertions.assertEquals(maxValColor, cs.getValueColor(1));
	}
	
	@Test
	void getValueColorsTest() {
		Assertions.assertArrayEquals(new Color[] {minValColor, maxValColor}, cs.getValueColors());
	}
	
	@Test
	void setValueColorTest() {
		Color newMinValColor = Color.PINK;
		Color newMaxValColor = Color.ORANGE;
		cs.setValueColor(0, newMinValColor);
		cs.setValueColor(1, newMaxValColor);
		
		Assertions.assertEquals(newMinValColor, cs.getValueColor(0));
		Assertions.assertEquals(newMaxValColor, cs.getValueColor(1));
		
		cs.setValueColor(0, minValColor);
		cs.setValueColor(1, maxValColor);
	}
	
	@Test
	void setValueColorsTest() {
		Color newMinValColor = Color.PINK;
		Color newMaxValColor = Color.ORANGE;
		cs.setValueColors(new Color[] {newMinValColor, newMaxValColor});
		
		Assertions.assertArrayEquals(new Color[] {newMinValColor, newMaxValColor}, cs.getValueColors());
		
		cs.setValueColor(0, minValColor);
		cs.setValueColor(1, maxValColor);
	}
	
	@Test
	void getThicknessTest() {
		Assertions.assertEquals(thickness, cs.getBorderThickness());
	}
	
	@Test
	void setThicknessTest() {
		int newThickness = 5;
		cs.setBorderThickness(newThickness);
		
		Assertions.assertEquals(newThickness, cs.getBorderThickness());
		
		initAndShow();
		
		cs.setBorderThickness(thickness);
	}
	
}

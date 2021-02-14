package gelf.view.diagrams.components;

import java.awt.Color;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gelf.view.diagrams.TestCase;
import gelf.view.diagrams.TestFrame;
import gelf.view.diagrams.type.BarChart;

class HoverLabelTest implements TestCase {
	
	private static HoverLabel label = HoverLabel.getHoverLabel();
	private static TestFrame frame = new TestFrame();
	
	private static BarChart diagram = new BarChart(null, null, null, null);
	
	private static void initAndShow() {
		diagram.attachToContainer(frame);
		label.removeFromDiagram();
		label.attachToDiagram(diagram);
		label.show();
		TestCase.showStatic(frame, TestCase.SHOW_DURATION);
		diagram.removeFromContainer();
	}
	
	@Test
	void singletonTest() {
		Assertions.assertEquals(HoverLabel.getHoverLabel(), label);
	}
	
	@Test
	void attachAndRemoveTest() {
		label.attachToDiagram(diagram);
		label.show();
		Assertions.assertNotNull(diagram.getContainingElement().getComponents()[0]);
		initAndShow();
		
		label.removeFromDiagram();
		try {
			System.out.println(diagram.getContainingElement().getComponents()[0].toString());
		} catch (ArrayIndexOutOfBoundsException e) {
			
		}
		initAndShow();
	}
	
	@Test
	void setSizeTest() {
		label.attachToDiagram(diagram);
		
		double oldWidth = label.getWidth();
		double oldHeight = label.getHeight();
		
		double newWidth = 500;
		double newHeight = 700;
		
		label.setWidth(newWidth);
		label.setHeight(newHeight);
		
		Assertions.assertEquals(newWidth, label.getWidth());
		Assertions.assertEquals(newHeight, label.getHeight());
		
		initAndShow();
		
		label.setWidth(oldWidth);
		label.setHeight(oldHeight);
		
		label.removeFromDiagram();
	}
	
	@Test
	void setColorTest() {
		label.attachToDiagram(diagram);
		
		Color oldColor = label.getColor();
		Color newColor = Color.BLUE;
		
		label.setColor(newColor);
		
		Assertions.assertEquals(newColor, label.getColor());
		
		initAndShow();
		
		label.setColor(oldColor);
		
		label.removeFromDiagram();
	}
	
	@Test
	void setCaptionTest() {
		label.attachToDiagram(diagram);
		
		String oldString = label.getCaption();
		String newString = "Testing HoverLabel";
		
		label.setCaption(newString);
		
		Assertions.assertEquals(newString, label.getCaption());
		
		initAndShow();
		
		label.setCaption(oldString);
		
		label.removeFromDiagram();
	}
}

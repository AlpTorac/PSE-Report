package gelf.view.diagrams.components;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.awt.Container;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gelf.view.diagrams.TestCase;
import gelf.view.diagrams.TestFrame;

class HoverLabelTest implements TestCase {
	private static HoverLabel label = HoverLabel.getHoverLabel();
	private static TestFrame frame = new TestFrame();
	private static Container container = frame.getContentPane();
	
	@Test
	void singletonTest() {
		Assertions.assertEquals(HoverLabel.getHoverLabel(), label);
	}
	
	@Test
	void attachAndRemoveTest() {
		label.attachToContainer(container);
		Assertions.assertNotNull(container.getComponents()[0]);
		show(frame, TestCase.SHOW_DURATION);
		
		label.removeFromContainer();
		try {
			System.out.println(container.getComponents()[0].toString());
		} catch (ArrayIndexOutOfBoundsException e) {
			
		}
		show(frame, TestCase.SHOW_DURATION);
	}
	
	@Test
	void setSizeTest() {
		label.attachToContainer(container);
		
		double oldWidth = label.getWidth();
		double oldHeight = label.getHeight();
		
		double newWidth = 500;
		double newHeight = 700;
		
		label.setWidth(newWidth);
		label.setHeight(newHeight);
		
		Assertions.assertEquals(newWidth, label.getWidth());
		Assertions.assertEquals(newHeight, label.getHeight());
		
		show(frame, TestCase.SHOW_DURATION);
		
		label.setWidth(oldWidth);
		label.setHeight(oldHeight);
		
		label.removeFromContainer();
	}
	
	@Test
	void setColorTest() {
		label.attachToContainer(container);
		
		Color oldColor = label.getColor();
		Color newColor = Color.BLUE;
		
		label.setColor(newColor);
		
		Assertions.assertEquals(newColor, label.getColor());
		
		show(frame, TestCase.SHOW_DURATION);
		
		label.setColor(oldColor);
		
		label.removeFromContainer();
	}
	
	@Test
	void setCaptionTest() {
		label.attachToContainer(container);
		
		String oldString = label.getCaption();
		String newString = "Testing HoverLabel";
		
		label.setCaption(newString);
		
		Assertions.assertEquals(newString, label.getCaption());
		
		show(frame, TestCase.SHOW_DURATION);
		
		label.setCaption(oldString);
		
		label.removeFromContainer();
	}
}

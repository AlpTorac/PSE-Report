package gelf.view.diagrams.components;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JLabel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import gelf.view.diagrams.TestCase;
import gelf.view.diagrams.TestFrame;

class DiagramLabelTest implements TestCase{

	private static TestFrame frame = new TestFrame();
	private static Container container = frame.getContentPane();
	
	private static PositionInFrame topLeft = new PositionInFrame(400, 400);
	private static PositionInFrame bottomRight = new PositionInFrame(600, 500);
	
	private static DiagramLabel label;
	
	@BeforeAll
	static void initialisationTest() {
		label = DiagramComponentFactory.getDiagramComponentFactory().createDescriptionLabel(topLeft, bottomRight, Color.RED, "Testing DiagramLabel", 0);
	}

	@Test
	void showAndHideTest() {
		show(frame, label, TestCase.SHOW_DURATION);
	}
	
	@Test
	void setCaptionTest() {
		String newCaption = "Different caption";
		
		label.setCaption(newCaption);
		Assertions.assertEquals(label.getCaption(), newCaption);
		
		show(frame, label, TestCase.SHOW_DURATION);
		
		Assertions.assertEquals(((JLabel) label.visualElement).getText(), newCaption);
	}
	
	@Test
	void setPositionsTest() {
		float startX = 25;
		float startY = 94;
		float endX = 162;
		float endY = 543;
		
		label.setTopLeftInDiagram(startX, startY);
		label.setBottomRightInDiagram(endX, endY);
		
		Assertions.assertEquals(label.getTopLeftInDiagram().getXPos(), startX);
		Assertions.assertEquals(label.getTopLeftInDiagram().getYPos(), startY);
		Assertions.assertEquals(label.getBottomRightInDiagram().getXPos(), endX);
		Assertions.assertEquals(label.getBottomRightInDiagram().getYPos(), endY);
		
		Assertions.assertEquals(label.visualElement.getBounds().getMinX(), startX);
		Assertions.assertEquals(label.visualElement.getBounds().getMinY(), startY);
		Assertions.assertEquals(label.visualElement.getBounds().getMaxX(), endX);
		Assertions.assertEquals(label.visualElement.getBounds().getMaxY(), endY);
		
		show(frame, label, TestCase.SHOW_DURATION);
		
		label.setTopLeftInDiagram(400, 400);
		label.setBottomRightInDiagram(600, 500);
		
		show(frame, label, TestCase.SHOW_DURATION);
	}
	
	@Test
	void setThicknessTest() {
		int thickness = 5;
		
		label.setBorderThickness(thickness);
		
		Assertions.assertEquals(label.getBorderThickness(), thickness);
	}
	
}

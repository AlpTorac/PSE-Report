package gelf.view.diagrams.components;

import java.awt.Color;

import javax.swing.JLabel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import gelf.view.diagrams.TestCase;
import gelf.view.diagrams.TestFrame;
import gelf.view.diagrams.type.BarChart;

class DiagramLabelTest implements TestCase{

	private static TestFrame frame = new TestFrame();
	
	private static PositionInFrame topLeft = new PositionInFrame(400, 400);
	private static PositionInFrame bottomRight = new PositionInFrame(600, 500);
	
	private static DiagramLabel label;
	private static void initAndShow() {
		BarChart diagram = new BarChart(null, null,
				null, new DiagramComponent[] {label});
		
		diagram.attachToContainer(frame);
		TestCase.showStatic(frame, TestCase.SHOW_DURATION);
		frame.remove(diagram.getContainingElement());
	}
	
	@BeforeAll
	static void initialisationTest() {
		label = DiagramComponentFactory.getDiagramComponentFactory().createDescriptionLabel(topLeft, bottomRight, Color.RED, "Testing DiagramLabel", 0);
	}

	@Test
	void showAndHideTest() {
		initAndShow();
	}
	
	@Test
	void setCaptionTest() {
		String newCaption = "Different caption";
		
		label.setCaption(newCaption);
		Assertions.assertEquals(newCaption, label.getCaption());
		
		initAndShow();
		
		Assertions.assertEquals(newCaption, ((JLabel) label.visualElement).getText());
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
		
		initAndShow();
		
		label.setTopLeftInDiagram(400, 400);
		label.setBottomRightInDiagram(600, 500);
		
		initAndShow();
	}
	
	@Test
	void setThicknessTest() {
		int thickness = 5;
		
		label.setBorderThickness(thickness);
		
		Assertions.assertEquals(label.getBorderThickness(), thickness);
	}
	
}

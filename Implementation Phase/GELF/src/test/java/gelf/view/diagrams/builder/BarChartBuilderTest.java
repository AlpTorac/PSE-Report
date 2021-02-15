package gelf.view.diagrams.builder;

import java.awt.Container;
import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.TestCase;
import gelf.view.diagrams.TestFrame;
import gelf.view.diagrams.TestPanel;
import gelf.view.diagrams.components.BarChartBar;
import gelf.view.diagrams.components.DiagramValueDisplayComponent;
import gelf.view.diagrams.components.PositionIn2DDiagram;

class BarChartBuilderTest implements TestCase {
	private static TestPanel panel;
	private static TestFrame frame = new TestFrame();
	private static Container container;
	private static ArrayList<float[]> data;
	
	private static BarChartBuilder builder;
	
	@BeforeAll
	private static void initialisePanel() {
		panel = new TestPanel();
		frame.add(panel);
		container = panel;
		builder = new BarChartBuilder(container);
	}
	
	@Test
	void buildTest() {
		data = new ArrayList<float[]>();
		float[] values = new float[] {6, 5, 1, 1, 2, 3};
		data.add(values);
		
		builder.receiveDiagramData(data, 0);
		IDiagram diagram = builder.buildDiagram();
		diagram.attachToContainer(container);
		
		DiagramValueDisplayComponent[] bars = diagram.getDiagramValueDisplayComponentPrototypes();
		int barCount = bars.length;
		
		Assertions.assertEquals(data.get(0).length, barCount);
		Assertions.assertNull(diagram.getNonValueDisplayDiagramComponentPrototype());
		
		for (int i = 0; i < barCount; i++) {
			BarChartBar bar = (BarChartBar) bars[i];
			
			Assertions.assertEquals(values[i], bar.getValue());
			
			PositionIn2DDiagram topLeft = bar.getTopLeftInDiagram();
			PositionIn2DDiagram bottomRight = bar.getBottomRightInDiagram();
			
			Assertions.assertEquals(values[i], topLeft.getYCoordinate());
			Assertions.assertEquals(i, topLeft.getXCoordinate());
			Assertions.assertEquals(0, bottomRight.getYCoordinate());
			Assertions.assertEquals(i + 1, bottomRight.getXCoordinate());
		}
		
		diagram.refresh();
		show(frame, TestCase.SHOW_DURATION);
	}
}

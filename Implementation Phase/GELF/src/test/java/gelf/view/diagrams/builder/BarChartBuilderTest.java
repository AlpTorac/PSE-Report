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
		
		data.add(new float[] {6, 5, 1, 1, 2, 3});
		
		builder.receiveDiagramData(data, 0);
		IDiagram diagram = builder.buildDiagram();
		diagram.attachToContainer(container);
		
		Assertions.assertEquals(data.get(0).length, diagram.getDiagramValueDisplayComponentPrototypes().length);
		Assertions.assertNull(diagram.getNonValueDisplayDiagramComponentPrototype());
		
		diagram.refresh();
		show(frame, TestCase.SHOW_DURATION);
	}
}

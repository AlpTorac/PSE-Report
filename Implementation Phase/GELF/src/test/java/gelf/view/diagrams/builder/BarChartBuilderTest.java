package gelf.view.diagrams.builder;

import static org.junit.jupiter.api.Assertions.*;

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
	private static TestPanel panel = new TestPanel();
	private static TestFrame frame = new TestFrame();
	private static Container container = panel;
	private static ArrayList<float[]> data;
	
	private static BarChartBuilder builder = new BarChartBuilder(container);
	
	@BeforeAll
	private static void initialisePanel() {
		frame.getContentPane().add(panel);
	}
	
	@Test
	void buildTest() {
		data = new ArrayList<float[]>();
		
		data.add(new float[] {6, 5, 1, 1, 2, 3});
		
		builder.receiveDiagramData(data, 0);
		IDiagram diagram = builder.buildDiagram();
		
		Assertions.assertEquals(data.get(0).length, diagram.getDiagramValueDisplayComponentPrototypes().length);
		Assertions.assertNull(diagram.getNonValueDisplayDiagramComponentPrototype());
		
		diagram.refresh();
		show(frame, TestCase.LONG_SHOW_DURATION);
	}
}

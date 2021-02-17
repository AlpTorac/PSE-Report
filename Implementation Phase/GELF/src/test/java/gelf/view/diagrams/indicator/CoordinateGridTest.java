package gelf.view.diagrams.indicator;

import java.awt.Container;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.TestCase;
import gelf.view.diagrams.TestFrame;
import gelf.view.diagrams.TestPanel;
import gelf.view.diagrams.builder.HistogramBuilder;

class CoordinateGridTest implements TestCase {
	private static DiagramViewHelperFactory factory = DiagramViewHelperFactory.getInstance();
	
	private static TestPanel panel = new TestPanel();
	private static TestFrame frame = new TestFrame();
	private static Container container = panel;
	private static ArrayList<float[]> data;
	
	private static HistogramBuilder builder = new HistogramBuilder(container);
	
	@BeforeAll
	private static void initialiseData() {
		frame.getContentPane().add(panel);
		
		data = new ArrayList<float[]>();
		
		data.add(new float[] {0.1f, 0.25f, 0.5f, 0.6f, 0.8f, 1f, 2f});
		data.add(new float[] {6, 5, 1, 9, 3, 4, 5});
	}
	
	@Test
	void buildTest() {
		builder.receiveDiagramData(data, 1);
		IDiagram diagram = builder.buildDiagram();
		diagram.attachToContainer(container);
		
		DiagramViewHelper xGrid = factory.createXCoordinateGridDisplayer(diagram);
		DiagramViewHelper yGrid = factory.createYCoordinateGridDisplayer(diagram);
		
		xGrid.show();
		yGrid.show();
		
		diagram.refresh();
		show(frame, TestCase.SHOW_DURATION);
	}

}
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
import gelf.view.diagrams.components.ValueDisplayPoint;

class FunctionGraphBuilderTest implements TestCase {

	private static TestPanel panel = new TestPanel();
	private static TestFrame frame = new TestFrame();
	private static Container container = panel;
	private static ArrayList<float[]> data;
	
	private static FunctionGraphBuilder builder = new FunctionGraphBuilder(container);
	
	private static void initialiseData() {
		frame.getContentPane().add(panel);
		
		data = new ArrayList<float[]>();
		
		data.add(new float[] {0.1f, 0.13f, 0.25f, 0.5f, 0.6f, 0.8f, 1f, 2f});
		data.add(new float[] {6, 6, 5, 1, 9, 3, 4, 5});
	}
	
	@Test
	void buildTest() {
		initialiseData();
		builder.receiveDiagramData(data, 1);
		IDiagram diagram = builder.buildDiagram();
		diagram.attachToContainer(container);
		
		DiagramValueDisplayComponent[] points = diagram.getDiagramValueDisplayComponentPrototypes();
		int pointCount = points.length;
		
		float[] indices = data.get(0);
		float[] values = data.get(1);
		
		Assertions.assertEquals(indices.length, pointCount);
		Assertions.assertNull(diagram.getNonValueDisplayDiagramComponentPrototype());
		
		for (int i = 0; i < pointCount; i++) {
			ValueDisplayPoint point = (ValueDisplayPoint) points[i];
			
			Assertions.assertEquals(values[i], point.getValue());
			
			PositionIn2DDiagram position = point.getPositionInDiagram();
			
			Assertions.assertEquals(values[i], position.getYCoordinate());
			Assertions.assertEquals(indices[i], position.getXCoordinate());
		}
		
		diagram.refresh();
		show(frame, TestCase.LONG_SHOW_DURATION);
	}

}

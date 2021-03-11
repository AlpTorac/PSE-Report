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
import gelf.view.diagrams.components.DiagramValueDisplayComponent;
import gelf.view.diagrams.components.HistogramBar;
import gelf.view.diagrams.components.PositionIn2DDiagram;

class HistogramBuilderTest implements TestCase {

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
		
		DiagramValueDisplayComponent[] bars = diagram.getDiagramValueDisplayComponentPrototypes();
		int barCount = bars.length;
		
		float[] indices = new float[] {0f, 0.1f, 0.25f, 0.5f, 0.6f, 0.8f, 1f, 2f};
		float[] values = data.get(1);
		
		Assertions.assertEquals(indices.length - 1, barCount);
		Assertions.assertNull(diagram.getNonValueDisplayDiagramComponentPrototype());
		
		for (int i = 0; i < barCount; i++) {
			HistogramBar bar = (HistogramBar) bars[i];
			
			Assertions.assertEquals(values[i], bar.getValue());
			
			PositionIn2DDiagram topLeft = bar.getTopLeftInDiagram();
			PositionIn2DDiagram bottomRight = bar.getBottomRightInDiagram();
			
			
			Assertions.assertTrue(compareFloatingPoint(values[i], topLeft.getYCoordinate()));
			Assertions.assertTrue(compareFloatingPoint(indices[i], topLeft.getXCoordinate()));
			Assertions.assertTrue(compareFloatingPoint(0, bottomRight.getYCoordinate()));
			float ind = indices[i + 1];
			double bRight = bottomRight.getXCoordinate();
			Assertions.assertTrue(compareFloatingPoint(ind, bRight));
		}
		
		diagram.refresh();
		show(frame, TestCase.SHOW_DURATION);
	}

}

package gelf.view.diagrams.builder;

import java.awt.Container;
import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import gelf.view.diagrams.DiagramWizard;
import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.IDiagramWizard;
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
	}
	
	@Test
	void buildTest() {
		data = new ArrayList<float[]>();
		
		data.add(new float[] {0.1f, 0.25f, 0.5f, 0.6f, 0.8f, 1f, 2f});
		data.add(new float[] {6, 5, 1, 9, 3, 4, 5});
		
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
		diagram.removeFromContainer();
		container.repaint();
	}

	@Test
	void buildWithWizardTest() {
		IDiagramWizard wiz = new DiagramWizard();
		
		data = new ArrayList<float[]>();
		
		data.add(new float[] {0.5f, 0.55f, 0.61f, 0.63f, 0.72f, 0.83f, 0.99f});
		data.add(new float[] {1, 9, 3, 2, 1, 4, 7});
		
		IDiagram diagram = wiz.makeAndAttachHistogram(container, data);
		
		DiagramValueDisplayComponent[] bars = diagram.getDiagramValueDisplayComponentPrototypes();
		int barCount = bars.length;
		
		float[] values = data.get(1);
		ArrayList<Float> indices = new ArrayList<Float>();
		indices.add(0f);
		for (float f : data.get(0)) {
			indices.add(f);
		}
		
		Assertions.assertEquals(values.length, barCount);
		Assertions.assertNull(diagram.getNonValueDisplayDiagramComponentPrototype());
		
		for (int i = 1; i <= barCount; i++) {
			HistogramBar bar = (HistogramBar) bars[i - 1];
			
			Assertions.assertEquals(values[i - 1], bar.getValue());
			
			PositionIn2DDiagram topLeft = bar.getTopLeftInDiagram();
			PositionIn2DDiagram bottomRight = bar.getBottomRightInDiagram();
			
			Assertions.assertEquals(values[i - 1], topLeft.getYCoordinate());
			Assertions.assertEquals(indices.get(i - 1).doubleValue(), topLeft.getXCoordinate());
			Assertions.assertEquals(0, bottomRight.getYCoordinate());
			Assertions.assertEquals(indices.get(i).doubleValue(), bottomRight.getXCoordinate());
		}
		
		diagram.refresh();
		show(frame, TestCase.SHOW_DURATION);
		diagram.removeFromContainer();
		container.repaint();
	}
	
}

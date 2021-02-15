package gelf.view.diagrams.overlayer;

import java.awt.Container;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.TestCase;
import gelf.view.diagrams.TestFrame;
import gelf.view.diagrams.TestPanel;
import gelf.view.diagrams.builder.HistogramBuilder;

class HistogramOverlayStrategyTest implements TestCase {

	private static TestPanel panel = new TestPanel();
	private static TestFrame frame = new TestFrame();
	private static Container container = panel;
	private static ArrayList<float[]> data1;
	private static ArrayList<float[]> data2;
	
	private static DiagramOverlayer overlayer = new DiagramOverlayer();
	private static HistogramBuilder builder = new HistogramBuilder(container);
	
	@BeforeAll
	private static void initialiseData() {
		frame.getContentPane().add(panel);
		
		data1 = new ArrayList<float[]>();
		data2 = new ArrayList<float[]>();
		
		data1.add(new float[] {0.1f, 0.25f, 0.5f, 0.6f, 0.8f, 1f, 2f});
		data1.add(new float[] {6, 5, 1, 9, 3, 4, 5});
		data2.add(new float[] {0.2f, 0.3f, 0.5f, 0.55f, 0.75f, 0.99f, 2.01f});
		data2.add(new float[] {7, 0, 2, 10, 3.01f, 4, 10});
		
		overlayer.setOverlayStrategy(new HistogramOverlayStrategy());
	}
	
	@Test
	void buildTest() {
		builder.receiveDiagramData(data1, 1);
		IDiagram diagram1 = builder.buildDiagram();

		builder.receiveDiagramData(data2, 1);
		IDiagram diagram2 = builder.buildDiagram();
		
		IDiagram overlayDiagram = overlayer.overlay(new IDiagram[] {diagram1, diagram2});
		overlayDiagram.attachToContainer(container);
		
		overlayDiagram.refresh();
		show(frame, TestCase.LONG_SHOW_DURATION);
	}

}


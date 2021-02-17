package gelf.view.diagrams.overlayer;

import java.awt.Container;
import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.TestCase;
import gelf.view.diagrams.TestFrame;
import gelf.view.diagrams.TestPanel;
import gelf.view.diagrams.builder.HistogramBuilder;
import gelf.view.diagrams.components.DiagramValueDisplayComponent;
import gelf.view.diagrams.components.HistogramBar;

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
		
		DiagramValueDisplayComponent[] dvdcs = overlayDiagram.getDiagramValueDisplayComponentPrototypes();
		
		ArrayList<ArrayList<float[]>> datas = new ArrayList<ArrayList<float[]>>();
		
		datas.add(data1);
		datas.add(data2);
		
		float[] expectedIndices = new float[] {0f, 0.15f, 0.225f, 0.275f, 0.4f, 0.525f, 0.575f, 0.675f, 0.775f, 0.895f, 0.995f, 1.5f,
				2.005f, 2.211f, 0.15f, 0.225f, 0.275f, 0.4f, 0.525f, 0.575f, 0.675f, 0.775f, 0.895f, 0.995f, 1.5f, 2.005f};
		float[] expectedValues = new float[] {6, 5, 5, 1, 1, 9, 9, 3, 3, 4, 4, 5,
				0, 7, 7, 0, 0, 2, 10, 3.01f, 3.01f, 4, 4, 10, 10};
		
		for (int i = 0; i < expectedValues.length; i++) {
			HistogramBar bar = (HistogramBar) dvdcs[i];
			this.checkAssertionsForBar(expectedIndices, expectedValues, bar, i);
		}
		
		overlayDiagram.refresh();
		show(frame, TestCase.SHOW_DURATION);
	}
	
	private void checkAssertionsForBar(float[] expectedIndices, float[] expectedValues, HistogramBar bar, int barIndex) {
		float topLeftX;
		
		if (barIndex != 13) {
			topLeftX = expectedIndices[barIndex];
		} else {
			topLeftX = 0;
		}
		
		Assertions.assertEquals(expectedValues[barIndex], bar.getValue(), 1E-3);
		
		Assertions.assertEquals(expectedValues[barIndex], bar.getTopLeftInDiagram().getYCoordinate(), 1E-3);
		Assertions.assertEquals(topLeftX, bar.getTopLeftInDiagram().getXCoordinate(), 1E-3);
		Assertions.assertEquals(0, bar.getBottomRightInDiagram().getYCoordinate(), 1E-3);
		Assertions.assertEquals(expectedIndices[barIndex + 1], bar.getBottomRightInDiagram().getXCoordinate(), 1E-3);
	}
	
}

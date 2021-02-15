package gelf.view.diagrams.overlayer;

import java.awt.Container;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.TestCase;
import gelf.view.diagrams.TestFrame;
import gelf.view.diagrams.TestPanel;
import gelf.view.diagrams.builder.BarChartBuilder;
import gelf.view.diagrams.builder.DiagramBuilder;

class BarChartOverlayerStrategyTest implements TestCase {

	private static TestPanel panel;
	private static TestFrame frame = new TestFrame();
	private static Container container;
	
	private static ArrayList<float[]> data1;
	private static ArrayList<float[]> data2;
	
	private static DiagramOverlayer overlayer = new DiagramOverlayer();
	private static DiagramBuilder builder;
	
	@BeforeAll
	private static void init() {
		data1 = new ArrayList<float[]>();
		float[] values1 = new float[] {6, 5, 1, 1, 2, 3};
		data1.add(values1);
		
		data2 = new ArrayList<float[]>();
		float[] values2 = new float[] {7, 1, 1, 1, 3, 2};
		data2.add(values2);
		
		panel = new TestPanel();
		frame.add(panel);
		container = panel;
		builder = new BarChartBuilder(container);
		
		overlayer.setOverlayStrategy(new BarChartOverlayStrategy());
	}
	
	@Test
	void overlayTest() {
		builder.receiveDiagramData(data1, 0);
		IDiagram diagram1 = builder.buildDiagram();
		
		builder.receiveDiagramData(data2, 0);
		IDiagram diagram2 = builder.buildDiagram();
		
		IDiagram overlaidCharts = overlayer.overlay(new IDiagram[] {diagram1, diagram2});
		overlaidCharts.attachToContainer(container);
		
		overlaidCharts.refresh();
		show(frame, TestCase.LONG_SHOW_DURATION);
	}

}

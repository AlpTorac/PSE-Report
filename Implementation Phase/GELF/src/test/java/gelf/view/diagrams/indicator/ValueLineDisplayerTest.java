package gelf.view.diagrams.indicator;

import java.awt.Container;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.TestCase;
import gelf.view.diagrams.TestFrame;
import gelf.view.diagrams.TestPanel;
import gelf.view.diagrams.builder.HistogramBuilder;

class ValueLineDisplayerTest implements TestCase {
	private static DiagramViewHelperFactory factory = DiagramViewHelperFactory.getInstance();
	
	private static TestPanel panel = new TestPanel();
	private static TestFrame frame = new TestFrame();
	private static Container container = panel;
	private static ArrayList<float[]> data;
	
	private static HistogramBuilder builder = new HistogramBuilder(container);
	
	private static IDiagram chart;
	
	@BeforeAll
	private static void initialiseData() {
		frame.getContentPane().add(panel);
		
		data = new ArrayList<float[]>();
		
		data.add(new float[] {0.1f, 0.25f, 0.5f, 0.6f, 0.8f, 1f, 2f});
		data.add(new float[] {6, 5, 1, 9, 3, 4, 5});
		
		builder.receiveDiagramData(data, 1);
		IDiagram diagram = builder.buildDiagram();
		diagram.attachToContainer(container);
		
		chart = diagram;
	}
	
	@Test
	void minTest() {
		DiagramViewHelper min = factory.createMinLineDisplayer(chart);
		
		min.show();
		
		chart.refresh();
		show(frame, TestCase.SHOW_DURATION);
	}
	
	@Test
	void maxTest() {
		DiagramViewHelper max = factory.createMaxLineDisplayer(chart);
		
		show(frame, TestCase.SHOW_DURATION);
		
		max.remove();
	}
	
	@Test
	void avgTest() {
		DiagramViewHelper avg = factory.createAvgLineDisplayer(chart);
		
		avg.show();
		
		chart.refresh();
		show(frame, TestCase.SHOW_DURATION);
	}
	
	@Test
	void medTest() {
		DiagramViewHelper med = factory.createMedLineDisplayer(chart);
		
		med.show();
		
		chart.refresh();
		show(frame, TestCase.SHOW_DURATION);
	}

	@AfterAll
	private static void showAllTogether() {
		chart.refresh();
		TestCase.showStatic(frame, TestCase.SHOW_DURATION);
	}
	
}
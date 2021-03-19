package gelf.view.diagrams.indicator;

import java.awt.Container;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import gelf.view.diagrams.DiagramWizard;
import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.IDiagramWizard;
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
	
	private static IDiagramWizard wiz = new DiagramWizard();
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
		show(frame, TestCase.FAST_SHOW_DURATION);
		
		min.remove();
	}
	
	@Test
	void maxTest() {
		DiagramViewHelper max = factory.createMaxLineDisplayer(chart);
		
		max.show();
		
		chart.refresh();
		show(frame, TestCase.FAST_SHOW_DURATION);
		
		max.remove();
	}
	
	@Test
	void avgTest() {
		DiagramViewHelper avg = factory.createAvgLineDisplayer(chart);
		
		avg.show();
		
		chart.refresh();
		show(frame, TestCase.FAST_SHOW_DURATION);
		
		avg.remove();
	}
	
	@Test
	void medTest() {
		DiagramViewHelper med = factory.createMedLineDisplayer(chart);
		
		med.show();
		
		chart.refresh();
		show(frame, TestCase.FAST_SHOW_DURATION);
		
		med.remove();
	}
	
	@Test
	void minWithWizardTest() {
		wiz.addMinDisplayer(chart);
		
		chart.refresh();
		show(frame, TestCase.FAST_SHOW_DURATION);
		
		chart.hideDiagramViewHelper(IndicatorIdentifier.MIN);
		
		chart.refresh();
		show(frame, TestCase.FAST_SHOW_DURATION);
		
		chart.showDiagramViewHelper(IndicatorIdentifier.MIN);
		
		chart.refresh();
		show(frame, TestCase.FAST_SHOW_DURATION);
		
		chart.hideDiagramViewHelper(IndicatorIdentifier.MIN);
		chart.removeDiagramViewHelper(IndicatorIdentifier.MIN);
	}
	
	@Test
	void maxWithWizardTest() {
		wiz.addMaxDisplayer(chart);
		
		chart.refresh();
		show(frame, TestCase.FAST_SHOW_DURATION);
		
		chart.hideDiagramViewHelper(IndicatorIdentifier.MAX);
		
		chart.refresh();
		show(frame, TestCase.FAST_SHOW_DURATION);
		
		chart.showDiagramViewHelper(IndicatorIdentifier.MAX);
		
		chart.refresh();
		show(frame, TestCase.FAST_SHOW_DURATION);
		
		chart.hideDiagramViewHelper(IndicatorIdentifier.MAX);
		chart.removeDiagramViewHelper(IndicatorIdentifier.MAX);
	}
	
	@Test
	void avgWithWizardTest() {
		wiz.addAvgDisplayer(chart);
		
		chart.refresh();
		show(frame, TestCase.FAST_SHOW_DURATION);
		
		chart.hideDiagramViewHelper(IndicatorIdentifier.AVG);
		
		chart.refresh();
		show(frame, TestCase.FAST_SHOW_DURATION);
		
		chart.showDiagramViewHelper(IndicatorIdentifier.AVG);
		
		chart.refresh();
		show(frame, TestCase.FAST_SHOW_DURATION);
		
		chart.hideDiagramViewHelper(IndicatorIdentifier.AVG);
		chart.removeDiagramViewHelper(IndicatorIdentifier.AVG);
	}
	
	@Test
	void medWithWizardTest() {
		wiz.addMedDisplayer(chart);
		
		chart.refresh();
		show(frame, TestCase.FAST_SHOW_DURATION);
		
		chart.hideDiagramViewHelper(IndicatorIdentifier.MED);
		
		chart.refresh();
		show(frame, TestCase.FAST_SHOW_DURATION);
		
		chart.showDiagramViewHelper(IndicatorIdentifier.MED);
		
		chart.refresh();
		show(frame, TestCase.FAST_SHOW_DURATION);
		
		chart.hideDiagramViewHelper(IndicatorIdentifier.MED);
		chart.removeDiagramViewHelper(IndicatorIdentifier.MED);
	}
}
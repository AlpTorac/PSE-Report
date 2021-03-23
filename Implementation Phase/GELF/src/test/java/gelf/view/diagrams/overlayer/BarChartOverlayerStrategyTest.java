package gelf.view.diagrams.overlayer;

import java.awt.Container;
import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import gelf.view.diagrams.DiagramWizard;
import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.IDiagramWizard;
import gelf.view.diagrams.SettingsProvider;
import gelf.view.diagrams.TestCase;
import gelf.view.diagrams.TestFrame;
import gelf.view.diagrams.TestPanel;
import gelf.view.diagrams.builder.BarChartBuilder;
import gelf.view.diagrams.components.BarChartBar;
import gelf.view.diagrams.components.DiagramValueDisplayComponent;
import gelf.view.diagrams.type.BarChart;

class BarChartOverlayerStrategyTest implements TestCase {

	private static TestPanel panel;
	private static TestFrame frame = new TestFrame();
	private static Container container;
	
	private static ArrayList<float[]> data1;
	private static ArrayList<float[]> data2;
	
	private static DiagramOverlayer overlayer;
	private static BarChartBuilder builder;
	
	@BeforeAll
	private static void init() {
		panel = new TestPanel();
		frame.add(panel);
		container = panel;
		builder = new BarChartBuilder(container);
	}
	
	@Test
	void overlayTest() {
		data1 = new ArrayList<float[]>();
		float[] values1 = new float[] {6, 5, 1, 1, 2, 3};
		data1.add(values1);
		
		data2 = new ArrayList<float[]>();
		float[] values2 = new float[] {7, 1, 1, 1, 3, 2};
		data2.add(values2);
		
		builder.receiveDiagramData(data1, 0);
		BarChart diagram1 = (BarChart) builder.buildDiagram();
		
		builder.receiveDiagramData(data2, 0);
		BarChart diagram2 = (BarChart) builder.buildDiagram();
		overlayer = new DiagramOverlayer();
		overlayer.setOverlayStrategy(new BarChartOverlayStrategy(container, new BarChart[] {diagram1, diagram2}));
		IDiagram overlaidCharts = overlayer.overlay();
		overlaidCharts.attachToContainer(container);
		
		DiagramValueDisplayComponent[] dvdcs = overlaidCharts.getDiagramValueDisplayComponentPrototypes();
		
		ArrayList<ArrayList<float[]>> datas = new ArrayList<ArrayList<float[]>>();
		
		datas.add(data1);
		datas.add(data2);
		
		int dataCount = datas.size();
		
		double halfBarChartBarWidthInSteps = SettingsProvider.getInstance().getBarChartBarWidthInSteps() / 2d;
		
		for (int i = 0; i < dataCount; i++) {
			for (int j = 0; j < datas.get(i).get(0).length; j++) {
				int barIndex = i * datas.get(i).get(0).length + j;
				
				BarChartBar bar = (BarChartBar) dvdcs[barIndex];
				
				Assertions.assertEquals(datas.get(i).get(0)[j], bar.getValue(), 1E-3);
				
				Assertions.assertEquals(datas.get(i).get(0)[j], bar.getTopLeftInDiagram().getYCoordinate(), 1E-3);
				Assertions.assertEquals(j + 1 - halfBarChartBarWidthInSteps, bar.getTopLeftInDiagram().getXCoordinate(), 1E-3);
				Assertions.assertEquals(0, bar.getBottomRightInDiagram().getYCoordinate(), 1E-3);
				Assertions.assertEquals(j + 1 + halfBarChartBarWidthInSteps, bar.getBottomRightInDiagram().getXCoordinate(), 1E-3);
			}
		}
		
		overlaidCharts.refresh();
		show(frame, TestCase.SHOW_DURATION);
		overlaidCharts.removeFromContainer();
	}

	
	@Test
	void buildWithWizardTest() {
		data1 = new ArrayList<float[]>();
		float[] values1 = new float[] {6, 5, 1, 1, 2, 3};
		data1.add(values1);
		
		data2 = new ArrayList<float[]>();
		float[] values2 = new float[] {7, 1, 1, 1, 3, 2};
		data2.add(values2);
		
		builder.receiveDiagramData(data1, 0);
		BarChart diagram1 = (BarChart) builder.buildDiagram();
		
		builder.receiveDiagramData(data2, 0);
		BarChart diagram2 = (BarChart) builder.buildDiagram();
		
		IDiagramWizard wiz = new DiagramWizard();
		IDiagram overlaidCharts = wiz.overlayAndAttachBarCharts(container, new BarChart[] {diagram1, diagram2});
		
		DiagramValueDisplayComponent[] dvdcs = overlaidCharts.getDiagramValueDisplayComponentPrototypes();
		
		ArrayList<ArrayList<float[]>> datas = new ArrayList<ArrayList<float[]>>();
		
		datas.add(data1);
		datas.add(data2);
		
		int dataCount = datas.size();
		
		double halfBarChartBarWidthInSteps = SettingsProvider.getInstance().getBarChartBarWidthInSteps() / 2d;
		
		for (int i = 0; i < dataCount; i++) {
			for (int j = 0; j < datas.get(i).get(0).length; j++) {
				int barIndex = i * datas.get(i).get(0).length + j;
				
				BarChartBar bar = (BarChartBar) dvdcs[barIndex];
				
				Assertions.assertEquals(datas.get(i).get(0)[j], bar.getValue(), 1E-3);
				
				Assertions.assertEquals(datas.get(i).get(0)[j], bar.getTopLeftInDiagram().getYCoordinate(), 1E-3);
				Assertions.assertEquals(j + 1 - halfBarChartBarWidthInSteps, bar.getTopLeftInDiagram().getXCoordinate(), 1E-3);
				Assertions.assertEquals(0, bar.getBottomRightInDiagram().getYCoordinate(), 1E-3);
				Assertions.assertEquals(j + 1 + halfBarChartBarWidthInSteps, bar.getBottomRightInDiagram().getXCoordinate(), 1E-3);
			}
		}
		
		overlaidCharts.refresh();
		show(frame, TestCase.SHOW_DURATION);
		overlaidCharts.removeFromContainer();
	}
	
	
}

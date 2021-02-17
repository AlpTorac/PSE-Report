package gelf.view.diagrams.builder;

import java.awt.Container;
import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.SettingsProvider;
import gelf.view.diagrams.TestCase;
import gelf.view.diagrams.TestFrame;
import gelf.view.diagrams.TestPanel;
import gelf.view.diagrams.components.BarChartBar;
import gelf.view.diagrams.components.DiagramValueDisplayComponent;
import gelf.view.diagrams.components.PositionIn2DDiagram;

class BarChartBuilderTest implements TestCase {
	private static TestPanel panel;
	private static TestFrame frame = new TestFrame();
	private static Container container;
	private static ArrayList<float[]> data;
	private static ArrayList<String[]> desc;
	
	private static BarChartBuilder builder;
	
	@BeforeAll
	private static void initialisePanel() {
		panel = new TestPanel();
		frame.add(panel);
		container = panel;
		builder = new BarChartBuilder(container);
	}
	
	@Test
	void buildTest() {
		data = new ArrayList<float[]>();
		float[] values = new float[] {6, 5, 1, 1, 2, 3};
		data.add(values);
		
		desc = new ArrayList<String[]>();
		String[] descs = new String[] {"cell1", "cell2", "cell3", "cell4", "cell5", "cell6"};
		desc.add(descs);
		
		builder.receiveDiagramData(data, desc, 0);
		IDiagram diagram = builder.buildDiagram();
		diagram.attachToContainer(container);
		
		DiagramValueDisplayComponent[] bars = diagram.getDiagramValueDisplayComponentPrototypes();
		int barCount = bars.length;
		
		Assertions.assertEquals(data.get(0).length, barCount);
		Assertions.assertNull(diagram.getNonValueDisplayDiagramComponentPrototype());
		
		double halfBarWidthInSteps = SettingsProvider.getInstance().getBarChartBarWidthInSteps() / 2d;
		
		for (int i = 1; i <= barCount; i++) {
			BarChartBar bar = (BarChartBar) bars[i - 1];
			
			Assertions.assertEquals(values[i - 1], bar.getValue());
			
			PositionIn2DDiagram topLeft = bar.getTopLeftInDiagram();
			PositionIn2DDiagram bottomRight = bar.getBottomRightInDiagram();
			
			Assertions.assertEquals(values[i - 1], topLeft.getYCoordinate());
			Assertions.assertEquals(i - halfBarWidthInSteps, topLeft.getXCoordinate());
			Assertions.assertEquals(0, bottomRight.getYCoordinate());
			Assertions.assertEquals(i + halfBarWidthInSteps, bottomRight.getXCoordinate());
		}
		
		diagram.refresh();
		show(frame, TestCase.LONG_SHOW_DURATION);
	}
}

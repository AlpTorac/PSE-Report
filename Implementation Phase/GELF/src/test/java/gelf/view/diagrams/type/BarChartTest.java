package gelf.view.diagrams.type;

import java.awt.Container;
import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import edu.emory.mathcs.backport.java.util.Arrays;
import gelf.view.diagrams.DiagramWizard;
import gelf.view.diagrams.IDiagramWizard;
import gelf.view.diagrams.TestCase;
import gelf.view.diagrams.TestFrame;
import gelf.view.diagrams.TestPanel;
import gelf.view.diagrams.components.DiagramValueDisplayComponent;
import gelf.view.diagrams.data.DiagramData;

class BarChartTest implements TestCase {

	private static TestPanel panel = new TestPanel();
	private static TestFrame frame = new TestFrame();
	private static Container container = panel;
	private static ArrayList<float[]> data;
	private static ArrayList<String[]> descs;
	private static IDiagramWizard wiz = new DiagramWizard();
	
	@BeforeAll
	private static void initialiseData() {
		frame.getContentPane().add(panel);
		
		data = new ArrayList<float[]>();
		float[] values = new float[] {6, 5, 1, 1, 2, 3};
		
		descs = new ArrayList<String[]>();
		String[] desc = new String[] {"cell1","cell2","cell3","cell4","cell5","cell6"};
		
		data.add(values);
		descs.add(desc);
	}
	
	@Test
	void componentPositionTest() {
		BarChart diagram = wiz.makeAndAttachBarChart(container, data);
		DiagramValueDisplayComponent[] bars = diagram.getValueDisplayComponents();
		
		for (int i = 0, index = 0; i < data.get(0).length; i++, index++) {
			Assertions.assertArrayEquals(new int[] {i}, diagram.getIndexPositionsOfComponent(bars[index]));
		}
		diagram.removeFromContainer();
	}

	@Test
	void nonComponentPositionTest() {
		BarChart diagram = wiz.makeAndAttachBarChart(container, data);
		DiagramValueDisplayComponent[] bars = diagram.getDiagramValueDisplayComponentPrototypes();
		
		for (int i = 0, index = 0; i < data.get(0).length; i++, index++) {
			Assertions.assertArrayEquals(new int[] {-1}, diagram.getIndexPositionsOfComponent(bars[index]));
		}
		diagram.removeFromContainer();
	}
	
	@Test
	void cloneTest() {
		BarChart diagram = wiz.makeAndAttachBarChart(container, data);
		DiagramData clone = diagram.cloneDiagramData();
		
		Assertions.assertTrue(diagram.getDiagramData().equals(clone));
		
		diagram.removeFromContainer();
	}
	
	@Test
	void cloneValueIndexTest() {
		BarChart diagram = wiz.makeAndAttachBarChart(container, data);
		ArrayList<float[]> clonedValInd = diagram.cloneData();
		
		Assertions.assertTrue(data.equals(clonedValInd));
		
		diagram.removeFromContainer();
	}
	
	@Test
	void cloneDescsTest() {
		BarChart diagram = wiz.makeAndAttachBarChartWithDescriptions(container, data, descs);
		
		String[][] cloneDescs = diagram.cloneDescriptions().toArray(String[][]::new);
		Assertions.assertArrayEquals(descs.toArray(String[][]::new), cloneDescs);
		
		diagram.removeFromContainer();
	}
	
	@Test
	void showAndHideAxesTest() {
		BarChart diagram = wiz.makeAndAttachBarChartWithDescriptions(container, data, descs);
		
		show(frame, TestCase.SHOW_DURATION);
		diagram.hideAxes();
		show(frame, TestCase.SHOW_DURATION);
		diagram.showAxes();
		show(frame, TestCase.SHOW_DURATION);
		
		diagram.removeFromContainer();
	}
	
	@Test
	void showAndHideDiagramTest() {
		BarChart diagram = wiz.makeAndAttachBarChartWithDescriptions(container, data, descs);
		
		diagram.hide();
		show(frame, TestCase.SHOW_DURATION);
		diagram.show();
		show(frame, TestCase.SHOW_DURATION);
		diagram.hide();
		diagram.removeFromContainer();
	}
	
	@Test
	void getPrototypesTest() {
		BarChart diagram = wiz.makeAndAttachBarChartWithDescriptions(container, data, descs);
		
		Assertions.assertFalse(Arrays.deepEquals(diagram.getDiagramAxisPrototypes(), diagram.getDiagramAxisPrototypes()));
		Assertions.assertFalse(Arrays.deepEquals(diagram.getDiagramValueDisplayComponentPrototypes(), diagram.getDiagramValueDisplayComponentPrototypes()));
		Assertions.assertNull(diagram.getNonValueDisplayDiagramComponentPrototype());
		
		diagram.removeFromContainer();
	}
}

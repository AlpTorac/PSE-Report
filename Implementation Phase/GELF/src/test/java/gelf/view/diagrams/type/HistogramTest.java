package gelf.view.diagrams.type;

import java.awt.Container;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import gelf.view.diagrams.DiagramWizard;
import gelf.view.diagrams.IDiagramWizard;
import gelf.view.diagrams.TestCase;
import gelf.view.diagrams.TestFrame;
import gelf.view.diagrams.TestPanel;
import gelf.view.diagrams.components.DiagramValueDisplayComponent;
import gelf.view.diagrams.data.DiagramData;

class HistogramTest implements TestCase {

	private static TestPanel panel = new TestPanel();
	private static TestFrame frame = new TestFrame();
	private static Container container = panel;
	private static ArrayList<float[]> data;
	private static IDiagramWizard wiz = new DiagramWizard();
	
	@BeforeAll
	private static void initialiseData() {
		frame.getContentPane().add(panel);
		
		data = new ArrayList<float[]>();
		
		data.add(new float[] {0.1f, 0.25f, 0.5f, 0.6f, 0.8f, 1f, 2f});
		data.add(new float[] {6, 5, 1, 9, 3, 4, 5});
	}
	
	@Test
	void componentPositionTest() {
		Histogram diagram = wiz.makeAndAttachHistogram(container, data);
		DiagramValueDisplayComponent[] bars = diagram.getValueDisplayComponents();
		
		for (int i = 0, index = 0; i < data.get(0).length; i++, index++) {
			Assertions.assertArrayEquals(new int[] {i}, diagram.getIndexPositionsOfComponent(bars[index]));
		}
		diagram.removeFromContainer();
	}

	@Test
	void nonComponentPositionTest() {
		Histogram diagram = wiz.makeAndAttachHistogram(container, data);
		DiagramValueDisplayComponent[] bars = diagram.getDiagramValueDisplayComponentPrototypes();
		
		for (int i = 0, index = 0; i < data.get(0).length; i++, index++) {
			Assertions.assertArrayEquals(new int[] {-1}, diagram.getIndexPositionsOfComponent(bars[index]));
		}
		diagram.removeFromContainer();
	}
	
	@Test
	void cloneValueIndexTest() {
		Histogram diagram = wiz.makeAndAttachHistogram(container, data);
		ArrayList<float[]> clonedValInd = diagram.cloneData();
		
		Assertions.assertTrue(data.equals(clonedValInd));
		
		diagram.removeFromContainer();
	}

	@Test
	void cloneTest() {
		Histogram diagram = wiz.makeAndAttachHistogram(container, data);
		DiagramData clone = diagram.cloneDiagramData();
		
		Assertions.assertTrue(diagram.getDiagramData().equals(clone));
		
		diagram.removeFromContainer();
	}
	
	@Test
	void showAndHideAxesTest() {
		Histogram diagram = wiz.makeAndAttachHistogram(container, data);
		
		show(frame, TestCase.SHOW_DURATION);
		diagram.hideAxes();
		show(frame, TestCase.SHOW_DURATION);
		diagram.showAxes();
		show(frame, TestCase.SHOW_DURATION);
		
		diagram.removeFromContainer();
	}
	
	@Test
	void getPrototypesTest() {
		Histogram diagram = wiz.makeAndAttachHistogram(container, data);
		
		Assertions.assertFalse(Arrays.deepEquals(diagram.getDiagramAxisPrototypes(), diagram.getDiagramAxisPrototypes()));
		Assertions.assertFalse(Arrays.deepEquals(diagram.getDiagramValueDisplayComponentPrototypes(), diagram.getDiagramValueDisplayComponentPrototypes()));
		Assertions.assertNull(diagram.getNonValueDisplayDiagramComponentPrototype());
	}
}

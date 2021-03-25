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

class HeatMapTest implements TestCase {
	private static TestPanel panel = new TestPanel();
	private static TestFrame frame = new TestFrame();
	private static Container container = panel;
	private static ArrayList<float[]> data;
	private static IDiagramWizard wiz = new DiagramWizard();
	
	@BeforeAll
	private static void initialiseData() {
		frame.getContentPane().add(panel);
		data = new ArrayList<float[]>();
		data.add(new float[] {0.3f, 0.34f, 0.7f, 0.8f});
		data.add(new float[] {0.1f, 0.43f, 0.65f});
		data.add(new float[] {11f, 8f, 19f});
		data.add(new float[] {7f, 20f, 17f});
		data.add(new float[] {3f, 4f, 13f});
		data.add(new float[] {1f, 6f, 12f});
	}
	
	@Test
	void componentPositionTest() {
		HeatMap diagram = wiz.makeAndAttachHeatMap(container, data);
		DiagramValueDisplayComponent[] labels = diagram.getValueDisplayComponents();
		
		for (int i = 0, index = 0; i < data.get(0).length; i++) {
			for (int j = 0; j < data.get(1).length; j++, index++) {
				Assertions.assertArrayEquals(new int[] {i, j}, diagram.getIndexPositionsOfComponent(labels[index]));
			}
		}
		
		diagram.removeFromContainer();
	}

	@Test
	void nonComponentPositionTest() {
		HeatMap diagram = wiz.makeAndAttachHeatMap(container, data);
		DiagramValueDisplayComponent[] labels = diagram.getDiagramValueDisplayComponentPrototypes();
		
		for (int i = 0, index = 0; i < data.get(0).length; i++) {
			for (int j = 0; j < data.get(1).length; j++, index++) {
				Assertions.assertArrayEquals(new int[] {-1, -1}, diagram.getIndexPositionsOfComponent(labels[index]));
			}
		}
		
		diagram.removeFromContainer();
	}
	
	@Test
	void cloneValueIndexTest() {
		HeatMap diagram = wiz.makeAndAttachHeatMap(container, data);
		ArrayList<float[]> clonedValInd = diagram.cloneData();
		
		Assertions.assertTrue(data.equals(clonedValInd));
		
		diagram.removeFromContainer();
	}
	
	@Test
	void cloneTest() {
		HeatMap diagram = wiz.makeAndAttachHeatMap(container, data);
		DiagramData clone = diagram.cloneDiagramData();
		
		Assertions.assertTrue(diagram.getDiagramData().equals(clone));
		
		diagram.removeFromContainer();
	}
	
	@Test
	void showAndHideAxesTest() {
		HeatMap diagram = wiz.makeAndAttachHeatMap(container, data);
		
		show(frame, TestCase.SHOW_DURATION);
		diagram.hideAxes();
		show(frame, TestCase.SHOW_DURATION);
		diagram.showAxes();
		show(frame, TestCase.SHOW_DURATION);
		
		diagram.removeFromContainer();
	}
	
	
	@Test
	void getPrototypesTest() {
		HeatMap diagram = wiz.makeAndAttachHeatMap(container, data);
		
		Assertions.assertFalse(Arrays.deepEquals(diagram.getDiagramAxisPrototypes(), diagram.getDiagramAxisPrototypes()));
		Assertions.assertFalse(Arrays.deepEquals(diagram.getDiagramValueDisplayComponentPrototypes(), diagram.getDiagramValueDisplayComponentPrototypes()));
		Assertions.assertFalse(Arrays.deepEquals(diagram.getNonValueDisplayDiagramComponentPrototype(), diagram.getNonValueDisplayDiagramComponentPrototype()));
	}
}

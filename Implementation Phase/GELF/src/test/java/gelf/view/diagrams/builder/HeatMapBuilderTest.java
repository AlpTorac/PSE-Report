package gelf.view.diagrams.builder;

import java.awt.Container;
import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import gelf.view.diagrams.DiagramWizard;
import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.IDiagramWizard;
import gelf.view.diagrams.TestCase;
import gelf.view.diagrams.TestFrame;
import gelf.view.diagrams.TestPanel;
import gelf.view.diagrams.components.DiagramValueDisplayComponent;
import gelf.view.diagrams.components.HeatMapLabel;
import gelf.view.diagrams.components.PositionIn2DDiagram;

class HeatMapBuilderTest implements TestCase {
	private static TestPanel panel = new TestPanel();
	private static TestFrame frame = new TestFrame();
	private static Container container = panel;
	private static ArrayList<float[]> data;
	
	private static HeatMapBuilder builder = new HeatMapBuilder(container);
	
	@BeforeAll
	private static void initialisePanel() {
		frame.getContentPane().add(panel);
	}
	
	@Test
	void buildTest() {
		data = new ArrayList<float[]>();
		
		data.add(new float[] {0.15f, 0.5f, 1f});
		data.add(new float[] {1f, 2f, 3f});
		data.add(new float[] {1f, 2f, 3f});
		data.add(new float[] {4f, 5f, 6f});
		data.add(new float[] {7f, 8f, 9f});
		
		builder.receiveDiagramData(data, 2);
		IDiagram diagram = builder.buildDiagram();
		diagram.attachToContainer(container);
		
		DiagramValueDisplayComponent[] labels = diagram.getDiagramValueDisplayComponentPrototypes();
		int labelCount = labels.length;
		
		float[] indices1 = data.get(0);
		float[] indices2 = data.get(1);
		
		int index1Length = indices1.length;
		int index2Length = indices2.length;
		
		float[] values = new float[index1Length * index2Length];
		
		for (int row = 2; row < data.size(); row++) {
			for (int col = 0; col < data.get(row).length; col++) {
				values[(row - 2) * index2Length + col] = data.get(row)[col];
			}
		}
		
		int indexCount = indices1.length * indices2.length;
		
		Assertions.assertEquals(indexCount, labelCount);
		
		this.assertionsForOriginLabel(labels, indices1, indices2, values);
		this.assertionsForIndex1Null(labels, index1Length, index2Length, indices1, indices2, values);
		this.assertionsForIndex2Null(labels, index2Length, indices1, indices2, values);
		this.assertionsForLabel(labels, index1Length, index2Length, indices1, indices2, values);
		
		diagram.refresh();
		show(frame, TestCase.SHOW_DURATION);
		diagram.removeFromContainer();
	}
	
	@Test
	void buildWithWizardTest() {
		IDiagramWizard wiz = new DiagramWizard();
		
		data = new ArrayList<float[]>();
		
		data.add(new float[] {0.3f, 0.34f, 0.7f});
		data.add(new float[] {0.1f, 0.43f, 0.65f});
		data.add(new float[] {11f, 8f, 19f});
		data.add(new float[] {7f, 20f, 17f});
		data.add(new float[] {3f, 4f, 13f});
		
		IDiagram diagram = wiz.makeAndAttachHeatMap(container, data);
		
		DiagramValueDisplayComponent[] labels = diagram.getDiagramValueDisplayComponentPrototypes();
		int labelCount = labels.length;
		
		float[] indices1 = data.get(0);
		float[] indices2 = data.get(1);
		
		int index1Length = indices1.length;
		int index2Length = indices2.length;
		
		float[] values = new float[index1Length * index2Length];
		
		for (int row = 2; row < data.size(); row++) {
			for (int col = 0; col < data.get(row).length; col++) {
				values[(row - 2) * index2Length + col] = data.get(row)[col];
			}
		}
		
		int indexCount = indices1.length * indices2.length;
		
		Assertions.assertEquals(indexCount, labelCount);
		
		this.assertionsForOriginLabel(labels, indices1, indices2, values);
		this.assertionsForIndex1Null(labels, index1Length, index2Length, indices1, indices2, values);
		this.assertionsForIndex2Null(labels, index2Length, indices1, indices2, values);
		this.assertionsForLabel(labels, index1Length, index2Length, indices1, indices2, values);
		
		diagram.refresh();
		show(frame, TestCase.SHOW_DURATION);
		diagram.removeFromContainer();
		container.repaint();
	}
	
	private void assertionsForOriginLabel(DiagramValueDisplayComponent[] labels, float[] indices1, float[] indices2, float[] values) {
		HeatMapLabel label = (HeatMapLabel) labels[0];
		
		Assertions.assertEquals(values[0], label.getValue());
		
		PositionIn2DDiagram topLeft = label.getTopLeftInDiagram();
		PositionIn2DDiagram bottomRight = label.getBottomRightInDiagram();
		
		Assertions.assertEquals(indices2[0], topLeft.getYCoordinate());
		Assertions.assertEquals(0, topLeft.getXCoordinate());
		Assertions.assertEquals(0, bottomRight.getYCoordinate());
		Assertions.assertEquals(indices1[0], bottomRight.getXCoordinate());
	}
	
	private void assertionsForIndex1Null(DiagramValueDisplayComponent[] labels, int index1Length, int index2Length, float[] indices1, float[] indices2, float[] values) {
		for (int i = 1; i < index1Length; i++) {
				int currentIndex = i * index2Length;
				HeatMapLabel label = (HeatMapLabel) labels[currentIndex];
				
				Assertions.assertEquals(values[currentIndex], label.getValue());
				
				PositionIn2DDiagram topLeft = label.getTopLeftInDiagram();
				PositionIn2DDiagram bottomRight = label.getBottomRightInDiagram();
				
				Assertions.assertEquals(indices2[0], topLeft.getYCoordinate());
				Assertions.assertEquals(indices1[i - 1], topLeft.getXCoordinate());
				Assertions.assertEquals(0, bottomRight.getYCoordinate());
				Assertions.assertEquals(indices1[i], bottomRight.getXCoordinate());
		}
	}
	
	private void assertionsForIndex2Null(DiagramValueDisplayComponent[] labels, int index2Length, float[] indices1, float[] indices2, float[] values) {
		for (int j = 1; j < index2Length; j++) {
			HeatMapLabel label = (HeatMapLabel) labels[j];
			
			Assertions.assertEquals(values[j], label.getValue());
			
			PositionIn2DDiagram topLeft = label.getTopLeftInDiagram();
			PositionIn2DDiagram bottomRight = label.getBottomRightInDiagram();
			
			Assertions.assertEquals(indices2[j], topLeft.getYCoordinate());
			Assertions.assertEquals(0, topLeft.getXCoordinate());
			Assertions.assertEquals(indices2[j - 1], bottomRight.getYCoordinate());
			Assertions.assertEquals(indices1[0], bottomRight.getXCoordinate());
		}
	}
	
	private void assertionsForLabel(DiagramValueDisplayComponent[] labels, int index1Length, int index2Length, float[] indices1, float[] indices2, float[] values) {
		for (int i = 1; i < index1Length; i++) {
			for (int j = 1; j < index2Length; j++) {
				int currentIndex = i * index2Length + j;
				HeatMapLabel label = (HeatMapLabel) labels[currentIndex];
				
				Assertions.assertEquals(values[currentIndex], label.getValue());
				
				PositionIn2DDiagram topLeft = label.getTopLeftInDiagram();
				PositionIn2DDiagram bottomRight = label.getBottomRightInDiagram();
				
				Assertions.assertEquals(indices2[j], topLeft.getYCoordinate());
				Assertions.assertEquals(indices1[i - 1], topLeft.getXCoordinate());
				Assertions.assertEquals(indices2[j - 1], bottomRight.getYCoordinate());
				Assertions.assertEquals(indices1[i], bottomRight.getXCoordinate());
			}
		}
	}
}

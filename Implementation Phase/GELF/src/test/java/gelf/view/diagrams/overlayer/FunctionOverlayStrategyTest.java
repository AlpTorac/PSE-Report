package gelf.view.diagrams.overlayer;

import java.awt.Container;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.SettingsProvider;
import gelf.view.diagrams.TestCase;
import gelf.view.diagrams.TestFrame;
import gelf.view.diagrams.TestPanel;
import gelf.view.diagrams.builder.FunctionGraphBuilder;
import gelf.view.diagrams.components.DiagramValueDisplayComponent;
import gelf.view.diagrams.components.ValueDisplayPoint;

class FunctionOverlayStrategyTest implements TestCase {

	private static TestPanel panel = new TestPanel();
	private static TestFrame frame = new TestFrame();
	private static Container container = panel;
	private static ArrayList<float[]> data1;
	private static ArrayList<float[]> data2;
	
	private static DiagramOverlayer overlayer = new DiagramOverlayer();
	private static FunctionGraphBuilder builder = new FunctionGraphBuilder(container);
	
	@BeforeAll
	private static void initialiseData() {
		frame.getContentPane().add(panel);
		
		data1 = new ArrayList<float[]>();
		data2 = new ArrayList<float[]>();
		
		data1.add(new float[] {0.1f, 0.11f, 0.13f, 0.15f, 0.2f, 0.201f, 1f});
		data1.add(new float[] {1, 1.5f, 1.5f, 1.3f, 0.5f, 0.75f, 1.25f});
		
		data2.add(new float[] {0.1f, 0.12f, 0.125f, 0.1501f, 0.2f, 0.3f, 1f});
		data2.add(new float[] {1, 1.5f, 1.5f, 1.5f, 0.5f, 0.75f, 1.3f});
		
		overlayer.setOverlayStrategy(new FunctionGraphOverlayStrategy());
	}
	
	@Test
	void buildTest() {
		int oldSize = SettingsProvider.getInstance().getFunctionGraphPointSize();
		int newSize = 20;
		
		SettingsProvider.getInstance().setFunctionGraphPointSize(newSize);
		
		builder.receiveDiagramData(data1, 1);
		IDiagram diagram1 = builder.buildDiagram();

		builder.receiveDiagramData(data2, 1);
		IDiagram diagram2 = builder.buildDiagram();
		
		IDiagram overlayDiagram = overlayer.overlay(new IDiagram[] {diagram1, diagram2});
		overlayDiagram.attachToContainer(container);
		
		DiagramValueDisplayComponent[] dvdcs = overlayDiagram.getDiagramValueDisplayComponentPrototypes();
		
		for (int i = 0; i < 14; i++) {
			ValueDisplayPoint currentPoint = (ValueDisplayPoint) dvdcs[i];
			
			System.out.print("x on axis = " + currentPoint.getPositionInDiagram().getXCoordinate());
			System.out.print(", y on axis = " + currentPoint.getPositionInDiagram().getYCoordinate() + "\n");
			System.out.print("x on frame = " + currentPoint.getPositionInDiagram().toPositionInFrame().getXPos());
			System.out.print(", y on frame = " + currentPoint.getPositionInDiagram().toPositionInFrame().getYPos() + "\n");
			System.out.print("value = " + currentPoint.getValue() + "\n\n");
		}
		
		overlayDiagram.refresh();
		show(frame, TestCase.LONG_SHOW_DURATION);
		SettingsProvider.getInstance().setFunctionGraphPointSize(oldSize);
	}

}

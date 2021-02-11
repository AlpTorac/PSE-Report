package gelf.view.diagrams.builder;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Container;
import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.TestCase;
import gelf.view.diagrams.TestFrame;

class HeatMapBuilderTest implements TestCase {
	private static TestFrame frame = new TestFrame();
	private static Container container = frame;
	private static ArrayList<float[]> data;
	
	private static HeatMapBuilder builder = new HeatMapBuilder(container);
	
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
		
		show(frame, TestCase.LONG_SHOW_DURATION);
	}
}

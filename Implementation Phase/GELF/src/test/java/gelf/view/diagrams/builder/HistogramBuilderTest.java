package gelf.view.diagrams.builder;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Container;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.TestCase;
import gelf.view.diagrams.TestFrame;

class HistogramBuilderTest implements TestCase {

	private static TestFrame frame = new TestFrame();
	private static Container container = frame;
	private static ArrayList<float[]> data;
	
	private static HistogramBuilder builder = new HistogramBuilder(container);
	
	@BeforeAll
	private static void initialiseData() {
		data = new ArrayList<float[]>();
		
		data.add(new float[] {0.15f, 1f, 1.5f, 1.9f, 2.3f, 2.5f});
		data.add(new float[] {6, 5, 1, 1, 2, 3});
	}
	
	@Test
	void buildTest() {
		builder.receiveDiagramData(data, 1);
		IDiagram diagram = builder.buildDiagram();
		diagram.refresh();
		show(frame, TestCase.SHOW_DURATION);
	}

}

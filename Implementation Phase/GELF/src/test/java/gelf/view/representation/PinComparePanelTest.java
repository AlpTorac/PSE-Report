package gelf.view.representation;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import javax.swing.JFrame;

import org.junit.jupiter.api.Test;

import gelf.model.elements.Cell;
import gelf.model.elements.Element;
import gelf.model.elements.InputPin;
import gelf.model.elements.Library;
import gelf.model.elements.OutputPin;

class PinComparePanelTest {
	
	ArrayList<InputPin> inPins = new ArrayList<InputPin>();
	ArrayList<OutputPin> outPins = new ArrayList<OutputPin>();
	ArrayList<Cell> cells = new ArrayList<Cell>();
	ArrayList<Library> libraries = new ArrayList<Library>();
	Library library = new Library("library", new float[2], new float[2], null, cells);
	
	Cell cell = new Cell("cell", new float[2], new float[2], library, inPins, outPins, null, 0.1f);

	InputPin inPin = new InputPin("A1", cell, null);
	OutputPin outPin = new OutputPin("Z", cell, null, null);
	
	JFrame frame = new JFrame();
	
	@Test
	void initTest() {
		inPins.add(inPin);
		outPins.add(outPin);
		cells.add(cell);
		ArrayList<Element> elements = new ArrayList<Element>();
		elements.add(cell);
		elements.add(cell);
		PinComparePanel panel = new PinComparePanel(100, 100, null, null, elements);
		frame.add(panel);
	}
}

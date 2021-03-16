package gelf.view.representation;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import javax.swing.JFrame;

import org.junit.jupiter.api.Test;

import gelf.model.elements.Cell;
import gelf.model.elements.InputPin;
import gelf.model.elements.Library;
import gelf.model.elements.OutputPin;

class LibraryPanelTest {

	ArrayList<InputPin> inPins = new ArrayList<InputPin>();
	ArrayList<OutputPin> outPins = new ArrayList<OutputPin>();
	ArrayList<Cell> cells = new ArrayList<Cell>();
	
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
		LibraryPanel panel = new LibraryPanel(100, 100, library, null);
		frame.add(panel);
	}

}

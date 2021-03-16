package gelf.view.composites;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import javax.swing.JFrame;

import org.junit.jupiter.api.Test;

import gelf.model.elements.Cell;
import gelf.model.elements.Element;
import gelf.model.elements.InputPin;
import gelf.model.elements.Library;
import gelf.model.elements.OutputPin;
import gelf.model.elements.attributes.Leakage;

class ComparerTest {

	ArrayList<InputPin> inPins = new ArrayList<InputPin>();
	ArrayList<OutputPin> outPins = new ArrayList<OutputPin>();
	ArrayList<Cell> cells = new ArrayList<Cell>();
	
	Library library = new Library("library", new float[2], new float[2], null, cells);
	
	Cell cell = new Cell("cell", new float[2], new float[2], library, inPins, outPins, new Leakage(new float[2]), 0.1f);

	InputPin inPin = new InputPin("A1", cell, null);
	OutputPin outPin = new OutputPin("Z", cell, null, null);
	
	JFrame frame = new JFrame();
	
	@Test
	void test() {
		ArrayList<Element> compCells = new ArrayList<Element>();
		ArrayList<Element> compLibs = new ArrayList<Element>();
		ArrayList<Element> compIn = new ArrayList<Element>();
		ArrayList<Element> compOut = new ArrayList<Element>();
		compCells.add(cell);
		compCells.add(cell);
		compLibs.add(library);
		compLibs.add(library);
		compIn.add(inPin);
		compIn.add(inPin);
		compOut.add(outPin);
		compOut.add(outPin);
		Comparer c1 = new Comparer(compLibs, null, null, 100, 100);
		Comparer c2 = new Comparer(compCells, null, null, 100, 100);
		Comparer c3 = new Comparer(compIn, null, null, 100, 100);
		Comparer c4 = new Comparer(compOut, null, null, 100, 100);
		frame.add(c1);
		frame.add(c2);
		frame.add(c3);
		frame.add(c4);
	}

}

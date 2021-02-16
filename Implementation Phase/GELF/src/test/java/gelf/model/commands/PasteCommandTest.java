package gelf.model.commands;


import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gelf.model.elements.Cell;
import gelf.model.elements.InputPin;
import gelf.model.elements.Library;
import gelf.model.elements.OutputPin;
import gelf.model.elements.attributes.Leakage;

class PasteCommandTest {

	@Test
	void executeTest() {
		Library destinationLibrary = null;
		Library initialLibrary = null;
		ArrayList<InputPin> inPinList = new ArrayList<InputPin>();
		ArrayList<OutputPin> outPinList = new ArrayList<OutputPin>();
		float[] values = {1f, 2f};
		Leakage leakage = new Leakage(values);
		Cell cell1 = new Cell("cell1", null, null, initialLibrary, inPinList, outPinList, leakage, 0);
		Cell cell2 = new Cell("cell2", null, null, initialLibrary, inPinList, outPinList, leakage, 0);
		Cell cell3 = new Cell("cell3", null, null, initialLibrary, inPinList, outPinList, leakage, 0);
		Cell cell4 = new Cell("cell4", null, null, initialLibrary, inPinList, outPinList, leakage, 0);
		ArrayList<Cell> cellList1 = new ArrayList<Cell>();
		ArrayList<Cell> cellList2 = new ArrayList<Cell>();
		cellList1.add(cell1);
		cellList1.add(cell2);
		cellList2.add(cell3);
		cellList2.add(cell4);
		initialLibrary = new Library("library1", null, null, null, cellList1);
		destinationLibrary = new Library("library2", null, null, null, cellList2);
		
		PasteCommand paste = new PasteCommand(destinationLibrary, cellList1);
		paste.execute();
		
		Assertions.assertEquals(destinationLibrary.getCells().get(0).getName(), 
				"cell3");
		Assertions.assertEquals(destinationLibrary.getCells().get(1).getName(), 
				"cell4");
		Assertions.assertEquals(destinationLibrary.getCells().get(2).getName(), 
				"cell1");
		Assertions.assertEquals(destinationLibrary.getCells().get(3).getName(), 
				"cell2");
	}

}

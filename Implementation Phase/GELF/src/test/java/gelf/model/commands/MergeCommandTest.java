package gelf.model.commands;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import gelf.model.elements.Cell;
import gelf.model.elements.InputPin;
import gelf.model.elements.Library;
import gelf.model.elements.OutputPin;
import gelf.model.elements.attributes.Leakage;
import gelf.model.project.Model;

class MergeCommandTest {
	private static ArrayList<Library> initialLibraries;
	private final static float[] INDEX1 = new float[] {1f, 2f, 3f};
	private final static float[] INDEX2 = new float[] {1f, 3f, 4f};
	
	@BeforeAll
	static void buildUp() {
		Library initialLibrary1 = null;
		Library initialLibrary2 = null;
		ArrayList<InputPin> inPinList = new ArrayList<InputPin>();
		ArrayList<OutputPin> outPinList = new ArrayList<OutputPin>();
		float[] values = {1f, 2f};
		Leakage leakage = new Leakage(values);
		Cell cell1 = new Cell("cell1", INDEX1, INDEX2, initialLibrary1, inPinList, outPinList, leakage, 0);
		Cell cell2 = new Cell("cell2", INDEX1, INDEX2, initialLibrary1, inPinList, outPinList, leakage, 0);
		Cell cell3 = new Cell("cell3", INDEX2, INDEX1, initialLibrary2, inPinList, outPinList, leakage, 0);
		Cell cell4 = new Cell("cell4", INDEX2, INDEX1, initialLibrary2, inPinList, outPinList, leakage, 0);
		
		ArrayList<Cell> cellList1 = new ArrayList<Cell>();
		ArrayList<Cell> cellList2 = new ArrayList<Cell>();
		cellList1.add(cell1);
		cellList1.add(cell2);
		cellList2.add(cell3);
		cellList2.add(cell4);
		
		initialLibrary1 = new Library("library1", INDEX1, INDEX2, null, cellList1);
		initialLibrary2 = new Library("library2", INDEX2, INDEX1, null, cellList2);
		
		cell1.setParentLibrary(initialLibrary1);
		cell2.setParentLibrary(initialLibrary1);
		cell3.setParentLibrary(initialLibrary2);
		cell4.setParentLibrary(initialLibrary2);
		
		initialLibraries = new ArrayList<Library>();
		initialLibraries.add(initialLibrary1);
		initialLibraries.add(initialLibrary2);
	}

	@Test
	void executeTest() {
		
		MergeCommand merge = new MergeCommand("testName", initialLibraries);
		merge.execute();
		
		Library productLibrary = Model.getInstance().getCurrentProject().getLibraries().get(0);
		assertEquals("cell1", productLibrary.getCells().get(0).getName());
		assertEquals("cell2", productLibrary.getCells().get(1).getName());
		assertEquals("cell3", productLibrary.getCells().get(2).getName());
		assertEquals("cell4", productLibrary.getCells().get(3).getName());
		assertEquals("testName", productLibrary.getName());
		assertNotSame(initialLibraries.get(0).getCells().get(0), productLibrary.getCells().get(0));
		assertEquals(2, initialLibraries.get(0).getCells().size());
	}
	
	@Test
	void undoTest() {
		MergeCommand merge = new MergeCommand("testName", initialLibraries);
		merge.execute();
		
		merge.undo();
		
		assertEquals(0, Model.getInstance().getCurrentProject().getLibraries().size());
		
	}
	
	@AfterEach
	void buildDown() {
		Model.getInstance().getCurrentProject().setLibraries(new ArrayList<Library>());
	}
	

}

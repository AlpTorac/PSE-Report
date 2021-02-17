package gelf.model.commands;


import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gelf.model.elements.Cell;
import gelf.model.elements.Library;

class DeleteCommandTest {
	
	@Test
	void executeTest() {
		Library library = null;
		Cell cell1 = new Cell("cell1", null, null, library, null, null, null, 0);
		Cell cell2 = new Cell("cell2", null, null, library, null, null, null, 0);
		
		ArrayList<Cell> deletedCells = new ArrayList<Cell>();
		ArrayList<Cell> cellList = new ArrayList<Cell>();
		
		cellList.add(cell1);
		cellList.add(cell2);
		
		library = new Library("library", null, null, null, cellList);
		
		cell1.setParentLibrary(library);
		cell2.setParentLibrary(library);
		
		
		deletedCells.add(cell1);
		DeleteCommand delete = new DeleteCommand(deletedCells);
		delete.execute();
		
		boolean deleted = false;
		if (!library.getCells().contains(cell1)) {
			deleted = true;
		}
		Assertions.assertEquals(true, deleted);
		
		if (library.getCells().contains(cell2)) {
			deleted = false;
		}
		Assertions.assertEquals(false, deleted);
	}
	
	@Test
	void undoTest() {
		Library library = null;
		Cell cell1 = new Cell("cell1", null, null, library, null, null, null, 0);
		Cell cell2 = new Cell("cell2", null, null, library, null, null, null, 0);
		
		ArrayList<Cell> deletedCells = new ArrayList<Cell>();
		ArrayList<Cell> cellList = new ArrayList<Cell>();
		
		cellList.add(cell1);
		cellList.add(cell2);
		
		library = new Library("library", null, null, null, cellList);
		
		cell1.setParentLibrary(library);
		cell2.setParentLibrary(library);
		
		
		deletedCells.add(cell1);
		DeleteCommand delete = new DeleteCommand(deletedCells);
		delete.execute();
		delete.undo();
		
		boolean deleted = false;
		if (!library.getCells().contains(cell1)) {
			deleted = true;
		}
		Assertions.assertEquals(false, deleted);
		
		if (library.getCells().contains(cell2)) {
			deleted = false;
		}
		Assertions.assertEquals(false, deleted);
	}

}

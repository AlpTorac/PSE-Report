package gelf.model.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import gelf.model.elements.Cell;
import gelf.model.elements.Library;
import gelf.model.project.Model;

public class MoveCommand implements Command {
	
	private HashMap<Cell, Library> initialPositions;
    private ArrayList<Cell> deletedCells;
    private HashMap<Cell, String> renamedCellsOldNames;
    private Library destinationLibrary;
    private ArrayList<Cell> cellsToMove;
    private Model currentModel = Model.getInstance();
	
	public MoveCommand(ArrayList<Cell> cellsToMove, Library destinationLibrary) {
		this.destinationLibrary = destinationLibrary;
		this.cellsToMove = cellsToMove;
	}

	public void execute() {
		ArrayList<Cell> cellsToComp = new ArrayList<Cell>();
		Iterator<Cell> movCellIt = cellsToMove.iterator();
		ArrayList<Cell> destLibCells = destinationLibrary.getCells();
		Iterator<Cell> destLibCellsIt = destLibCells.iterator();
		
		while(movCellIt.hasNext()) {
			cellsToComp.add(movCellIt.next());
		}
		while(destLibCellsIt.hasNext()) {
			cellsToComp.add(destLibCellsIt.next());
		}
		
		NameConflictResolver conflictResolver = 
				new NameConflictResolver(cellsToComp);
		ArrayList<Cell> cells = conflictResolver.getCells();
		deletedCells = conflictResolver.getDeletedCells();
		renamedCellsOldNames = conflictResolver.getRenamedCells();
		
		Iterator<Cell> cellsIt = cells.iterator();
		boolean exists = false;
		
		while(cellsIt.hasNext()) {
			
			Cell curCell = cellsIt.next();
			while(destLibCellsIt.hasNext()) {
				Cell curLibCell = destLibCellsIt.next();
				if(curCell.equals(curLibCell)) {
					exists = true;
					break;
				}
			}
			/* Searches, if the cells are available in the destination library,
			 * if not removes them from their initial library and 
			 * adds them to the destination library.
			 */
			if(exists = false) {
				initialPositions.get(curCell).getCells().remove(curCell);
				deletedCells.add(curCell);
				curCell.setParentLibrary(destinationLibrary);
				destinationLibrary.getCells().add(curCell);
			}
		}
		currentModel.getCurrentProject().inform();
		currentModel.getCurrentCommandHistory().addCommand(this);
	}

	public void undo() {
		
	}

}

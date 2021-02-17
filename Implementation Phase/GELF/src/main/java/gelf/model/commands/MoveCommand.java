package gelf.model.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import gelf.model.elements.Cell;
import gelf.model.elements.Library;
import gelf.model.project.Model;

public class MoveCommand implements Command {
	
	private HashMap<Cell, Library> initialPositions = new HashMap<Cell, Library>();
    private ArrayList<Cell> deletedCells = new ArrayList<Cell>();
    private HashMap<Cell, String> renamedCellsOldNames = new HashMap<Cell, String>();
    private Library destinationLibrary;
    private ArrayList<Cell> cellsToMove = new ArrayList<Cell>();
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
		while(cellsIt.hasNext()) {
			boolean exists = false;
			
			Cell curCell = cellsIt.next();
			
			destLibCellsIt = destLibCells.iterator();
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
			if(exists == false) {
				curCell.getParentLibrary().getCells().remove(curCell);
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

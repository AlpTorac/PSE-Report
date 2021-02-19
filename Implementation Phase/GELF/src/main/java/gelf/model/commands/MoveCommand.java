package gelf.model.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import gelf.model.elements.Cell;
import gelf.model.elements.Library;
import gelf.model.project.Model;
/** 
 * Moves the selected cells to a selected library
 * @author Kerem Kara
 */
public class MoveCommand implements Command {
	
	private HashMap<Cell, Library> initialPositions = new HashMap<Cell, Library>();
    private Library destinationLibrary;
    private ArrayList<Cell> cellsToMove = new ArrayList<Cell>();
    private ArrayList<Cell> movedCells = new ArrayList<Cell>();
    private Model currentModel = Model.getInstance();
    private ArrayList<Cell> initialCells;
	/**
	 * Initializes the command
	 * @param cellsToMove The selected cells that are going to be moved
	 * @param destinationLibrary The selected library, which the cells are going to be moved to
	 */
	public MoveCommand(ArrayList<Cell> cellsToMove, Library destinationLibrary) {
		this.destinationLibrary = destinationLibrary;
		this.cellsToMove = cellsToMove;
	}
	/**
	 * Executes the moving command by also calling the NameConflictResolver
	 */
	public void execute() {
		initialCells = new ArrayList<Cell>();
		initialCells.addAll(cellsToMove);
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
				Cell clone = curCell.clone();
				
				initialPositions.put(curCell, curCell.getParentLibrary());
				curCell.getParentLibrary().getCells().remove(curCell);
				
				destinationLibrary.getCells().add(clone);
				clone.setParentLibrary(destinationLibrary);
				movedCells.add(clone);
			}
		}
		currentModel.getCurrentProject().inform();
		currentModel.getCurrentCommandHistory().addCommand(this);
	}
	/**
	 * Undoes the command by deleting the cells from the library that they are moved to and
	 * adds them back to their initial library
	 */
	public void undo() {
		Iterator<Cell> movedCellsIt = movedCells.iterator();
		while(movedCellsIt.hasNext()) {
			Cell curCell = movedCellsIt.next();
			destinationLibrary.getCells().remove(curCell);
		}
		Iterator<Cell> initialCellsIt = initialCells.iterator();
		while(initialCellsIt.hasNext()) {
			Cell curCell = initialCellsIt.next();
			Library parent = initialPositions.get(curCell);
			parent.getCells().add(curCell);
			curCell.setParentLibrary(parent);
		}
		currentModel.getCurrentProject().inform();
	}

}

package gelf.model.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import gelf.model.elements.Cell;
import gelf.model.elements.Library;

public class PasteCommand implements Command {
	private ArrayList<Cell> pastedCells;
	private ArrayList<Cell> deletedCells;
	private ArrayList<Cell> copiedCells;
	private Library destinationLibrary;
	private HashMap<Cell, String> renamedCellsOldNames;
	
	/* If the list of copied cells won't be given as a parameter, then it will
	 * be taken from the project
	 */
	
	public PasteCommand(Library destinationLibrary) {
		this.destinationLibrary = destinationLibrary;
		Model currentModel = Model.getInstance();
        copiedCells = currentModel.getProject().getCopiedCells();
	}
	
	public PasteCommand(Library destinationLibrary, 
			ArrayList<Cell> copiedCells) {
		this.destinationLibrary = destinationLibrary;
		this.copiedCells = copiedCells;
	}

	public void execute() {
		ArrayList<Cell> cellsToComp = new ArrayList<Cell>();
		Iterator<Cell> copCellIt = copiedCells.iterator();
		ArrayList<Cell> destLibCells = destinationLibrary.getCells();
		Iterator<Cell> destLibCellsIt = destLibCells.iterator();
		
		while(copCellIt.hasNext()) {
			cellsToComp.add(copCellIt.next());
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
			if(exists = false) {
				destinationLibrary.getCells().add(curCell);
			}
		}
		Model currentModel = Model.getInstance();
		currentModel.getProject().notify();
		currentModel.getCommandHistory().addCommand(this);
	}

	@Override
	public void undo() {
		
	}

}

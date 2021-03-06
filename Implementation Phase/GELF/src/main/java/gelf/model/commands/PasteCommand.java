package gelf.model.commands;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import gelf.model.elements.Cell;
import gelf.model.elements.Element;
import gelf.model.elements.Library;
import gelf.model.project.Model;

/**
 * Pastes the copied elements to the selected library.
 * 
 * @author Kerem Kara
 */
public class PasteCommand implements Command {
	private ArrayList<Cell> pastedCells = new ArrayList<Cell>();
	private ArrayList<Cell> copiedCells = new ArrayList<Cell>();
	private Library destinationLibrary;
	private Model currentModel = Model.getInstance();

	/**
	 * Initializes the command. If the list of copied cells won't be given as a
	 * parameter, then it will be taken from the project.
	 * 
	 * @param destinationLibrary Selected library that the copied cells are going to
	 *                           be pasted to.
	 */
	public PasteCommand(Library destinationLibrary) {
		this.destinationLibrary = destinationLibrary;
		Model currentModel = Model.getInstance();
		copiedCells = new ArrayList<Cell>();
		HashSet<Element> copiedElements = currentModel.getCurrentProject().getCopiedElements();
		for (Element element : copiedElements) {
			if (element instanceof Cell) {
				copiedCells.add((Cell) element);
			}
		}
	}

	/**
	 * @param destinationLibrary Selected library that the copied cells are going to
	 *                           be pasted to.
	 * @param copiedCells        List of the copied cells.
	 */
	public PasteCommand(Library destinationLibrary, ArrayList<Cell> copiedCells) {
		this.destinationLibrary = destinationLibrary;
		this.copiedCells = copiedCells;
	}

	/**
	 * Executes the pasting command by also calling the NameConflictResolver.
	 */
	public void execute() {
		ArrayList<Cell> cellsToComp = new ArrayList<Cell>();
		Iterator<Cell> copCellIt = copiedCells.iterator();
		ArrayList<Cell> destLibCells = destinationLibrary.getCells();
		Iterator<Cell> destLibCellsIt = destLibCells.iterator();

		while (copCellIt.hasNext()) {
			cellsToComp.add(copCellIt.next());
		}
		while (destLibCellsIt.hasNext()) {
			cellsToComp.add(destLibCellsIt.next());
		}

		NameConflictResolver conflictResolver = new NameConflictResolver(cellsToComp);
		ArrayList<Cell> cells = conflictResolver.getCells();

		Iterator<Cell> cellsIt = cells.iterator();
		while (cellsIt.hasNext()) {
			boolean exists = false;
			Cell curCell = cellsIt.next();

			destLibCellsIt = destLibCells.iterator();
			while (destLibCellsIt.hasNext()) {
				Cell curDestCell = destLibCellsIt.next();
				if (curCell.getName().equals(curDestCell.getName())) {
					exists = true;
					break;
				}
			}
			if (exists == false) {
				Cell cloneCell = curCell.clone();
				destinationLibrary.getCells().add(cloneCell);
				cloneCell.setParentLibrary(destinationLibrary);
				pastedCells.add(cloneCell);
			}
		}
		currentModel.getCurrentProject().inform();
		currentModel.getCurrentCommandHistory().addCommand(this);
	}

	/**
	 * Undoes the pasting command by removing the pasted cells from the destination
	 * library.
	 */
	@Override
	public void undo() {
		Iterator<Cell> pastedCellsIt = pastedCells.iterator();
		while (pastedCellsIt.hasNext()) {
			destinationLibrary.getCells().remove(pastedCellsIt.next());
			currentModel.getCurrentProject().inform();
		}
	}

}

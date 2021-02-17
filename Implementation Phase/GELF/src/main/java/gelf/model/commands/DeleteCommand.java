package gelf.model.commands;

import gelf.model.elements.Cell;
import gelf.model.project.Model;

import java.util.ArrayList;
/**
 * Deletes cells
 * @author Xhulio Pernoca
 */
public class DeleteCommand implements Command{
    private ArrayList<Cell> deletedCells;
    private Model currentModel = Model.getInstance();

    /**
     * Instantiates the command
     * @param cells the cells to be deleted
     */
    public DeleteCommand(ArrayList<Cell> cells) {
        this.deletedCells = cells;
    }

    /**
     * Executes the command by removing the cells from their parent Libraries.
     * After having no reference, they'll fade and be deleted from the memory
     */
    public void execute() {
        for (Cell cell: deletedCells) {
            ArrayList<Cell> parentCellList = cell.getParentLibrary().getCells();
            parentCellList.remove(cell);
            cell.getParentLibrary().setCells(parentCellList);
        }
        currentModel.getCurrentCommandHistory().addCommand(this);
        currentModel.getCurrentProject().inform();
    }

    /**
     * Undoes the deletiong of a cell by readding the cells to the library
     */
    public void undo() {
    	for (Cell cell: deletedCells) {
            ArrayList<Cell> parentCellList = cell.getParentLibrary().getCells();
            parentCellList.add(cell);
            cell.getParentLibrary().setCells(parentCellList);
        }
        currentModel.getCurrentProject().inform();
    }
}

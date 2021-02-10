package gelf.model.commands;

import gelf.model.elements.Cell;
import gelf.model.project.Model;

import java.util.ArrayList;

public class DeleteCommand implements Command{
    private ArrayList<Cell> deletedCells;

    public DeleteCommand(ArrayList<Cell> cells) {
        this.deletedCells = cells;
    }

    public void execute() {
        for (Cell cell: deletedCells) {
            ArrayList<Cell> parentCellList = cell.getParentLibrary().getCells();
            parentCellList.remove(cell);
            cell.getParentLibrary().setCells(parentCellList);
            Model.getInstance().getCurrentCommandHistory().addCommand(this);
        }
    }

    public void undo() {

    }
}

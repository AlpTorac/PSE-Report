package gelf.model.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import gelf.model.elements.Cell;
import gelf.model.elements.CompareElementByName;
import gelf.view.composites.MergeDialog;

/**
 * A class that resolves naming conflicts between different Cells, so that
 * functionality is simplified in MergeCommand, MoveCommand, PasteCommand.
 * 
 * @author Xhulio Pernoca
 */
public class NameConflictResolver {
    private ArrayList<Cell> deletedCells = new ArrayList<Cell>();
    private ArrayList<Cell> cells = new ArrayList<Cell>();
    private HashMap<Cell, String> renamedCells = new HashMap<Cell, String>();

    /**
     * Instantiates the resolver and starts resolving right away so that the values
     * of it's attributes are valid
     * 
     * @param cells the cells to be resolved
     */
    public NameConflictResolver(ArrayList<Cell> cells) {
        if (cells.size() <= 1) {
            return;
        }
        boolean hasConflict = true;
        while (hasConflict) {
            hasConflict = false;
            Collections.sort(cells, new CompareElementByName());
            for (int i = 1; i < cells.size(); i++) {
                if (cells.get(i - 1).getName().equals(cells.get(i).getName())) {
                    hasConflict = true;
                    MergeDialog mergeDialog = new MergeDialog();
                    ConflictData conflictData = mergeDialog.open(cells.get(i - 1), cells.get(i));
                    switch (conflictData.getResolutionMethod()) {
                    case KEEPLEFT:
                        deletedCells.add(cells.get(i));
                        cells.remove(i);
                        break;
                    case KEEPRIGHT:
                        deletedCells.add(cells.get(i - 1));
                        cells.remove(i - 1);
                        break;
                    case RENAMELEFT:
                        Cell renamedLeftCell = cells.get(i - 1);
                        String newLeftName = conflictData.getName();
                        renamedCells.put(renamedLeftCell, renamedLeftCell.getName());
                        renamedLeftCell.setName(newLeftName);
                        break;
                    case RENAMERIGHT:
                        Cell renamedRightCell = cells.get(i);
                        String newRightName = conflictData.getName();
                        renamedCells.put(renamedRightCell, renamedRightCell.getName());
                        renamedRightCell.setName(newRightName);
                        break;
                    case CANCEL:
                        this.cells = new ArrayList<Cell>();
                        return;
                    }
                }
            }
        }
        this.cells = cells;
    }

    /**
     * Returns the deleted cells for the commands to keep track and use when undoing
     * commands
     * 
     * @return the deleted cells
     */
    @SuppressWarnings("unchecked")
    public ArrayList<Cell> getDeletedCells() {
        return (ArrayList<Cell>) deletedCells.clone();
    }

    /**
     * Returns the resolved cells so that the commands can resume functionality
     * 
     * @return the resolved cells
     */
    @SuppressWarnings("unchecked")
    public ArrayList<Cell> getCells() {
        return (ArrayList<Cell>) cells.clone();
    }

    /**
     * Returns the renamed cells and their old names so that they can be used for
     * undoing purposes
     * 
     * @return the renamed cells and their old names
     */
    @SuppressWarnings("unchecked")
    public HashMap<Cell, String> getRenamedCells() {
        return (HashMap<Cell, String>) renamedCells.clone();
    }
}
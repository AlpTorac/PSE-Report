package gelf.model.commands;

import gelf.view.composites.MergeDialog;
import gelf.model.parsers.LibertyParser;
import gelf.model.elements.Library;
import gelf.model.elements.Cell;
import gelf.model.elements.CompareElementByName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class NameConflictResolver {
    private ArrayList<Cell> deletedCells;
    private ArrayList<Cell> cells;
    private HashMap<Cell, String> renamedCells;

    public NameConflictResolver(ArrayList<Cell> cells) {
        if (cells.size() <= 1) {
            return;
        }
        boolean hasConflict = true;
        while (hasConflict) {
            hasConflict = false;
            Collections.sort(cells, new CompareElementByName());
            for (int i = 1; i < cells.size(); i++) {
                if (cells.get(i-1).getName().equals(cells.get(i).getName())) {
                    hasConflict = true;
                    MergeDialog mergeDialog = new MergeDialog(); 
                    ConflictData conflictData = mergeDialog.open(cells.get(i-1), cells.get(i));
                    switch (conflictData.getResolutionMethod()){
                        case KEEPLEFT:
                        deletedCells.add(cells.get(i));
                        cells.remove(i);
                        break;
                        case KEEPRIGHT:
                        deletedCells.add(cells.get(i-1));
                        cells.remove(i-1);
                        break;
                        case RENAMELEFT:
                        Cell renamedLeftCell = cells.get(i-1);
                        String newLeftName = conflictData.getName();
                        renamedCells.put(renamedLeftCell, newLeftName);
                        renamedLeftCell.setName(newLeftName);
                        break;
                        case RENAMERIGHT:
                        Cell renamedRightCell = cells.get(i);
                        String newRightName = conflictData.getName();
                        renamedCells.put(renamedRightCell, newRightName);
                        renamedRightCell.setName(newRightName);
                        break;
                        case CANCEL:
                        this.cells = new ArrayList<Cell>();
                        return;
                        break;
                    }
                }
            }
        }
        this.cells = cells;
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Cell> getDeletedCells() {
        return (ArrayList<Cell>) deletedCells.clone();
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Cell> getCells() {
        return (ArrayList<Cell>) cells.clone();
    }

    @SuppressWarnings("unchecked")
    public HashMap<Cell, String> getRenamedCells() {
        return (HashMap<Cell, String>) renamedCells.clone();
    }
}
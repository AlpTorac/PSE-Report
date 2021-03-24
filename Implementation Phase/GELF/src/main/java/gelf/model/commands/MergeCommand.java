package gelf.model.commands;

import java.util.ArrayList;

import gelf.model.elements.Cell;
import gelf.model.elements.Library;
import gelf.model.project.Model;

/**
 * Handles the merging of several libraries
 * 
 * @author Xhulio Pernoca
 */
public class MergeCommand implements Command {
    private ArrayList<Library> mergedLibraries;
    private Library productLibrary;
    private String name;
    private Model currentModel = Model.getInstance();

    /**
     * Initialises the command
     * 
     * @param name            new Library name
     * @param mergedLibraries the array of the libraries to be merged
     */
    public MergeCommand(String name, ArrayList<Library> mergedLibraries) {
        this.mergedLibraries = mergedLibraries;
        this.name = name;
    }

    /**
     * Executes the merging command by also calling the name conflict resolver. So
     * that everytime the command is redone, so is the conflict resolver
     */
    public void execute() {
        ArrayList<Cell> cells = new ArrayList<Cell>();
        for (Library library : mergedLibraries) {
            for (Cell cell : library.getCells()) {
                cells.add(cell);
            }
        }
        for (int i = 0; i < cells.size(); i++) {
            cells.set(i, cells.get(i).clone());
        }
        NameConflictResolver conflictResolver = new NameConflictResolver(cells);
        cells = conflictResolver.getCells();
        if (cells.isEmpty()) {
            return;
        }
        Cell firstCell = cells.get(0);
        float[] index1 = firstCell.getIndex1();
        float[] index2 = firstCell.getIndex2();
        for (int i = 1; i < cells.size(); i++) {
            Cell currentCell = cells.get(i);
            if (!currentCell.getIndex1().equals(index1) || !currentCell.getIndex2().equals(index2)) {
                currentCell.interpolate(index1, index2);
            }
        }
        productLibrary = new Library(name, index1, index2, null, cells);
        for (Cell cell : cells) {
            cell.setParentLibrary(productLibrary);
        }
        productLibrary.setUnits(mergedLibraries.get(0).getUnits());
        ArrayList<Library> libraries = currentModel.getCurrentProject().getLibraries();
        libraries.add(productLibrary);
        currentModel.getCurrentProject().setLibraries(libraries);
        currentModel.getCurrentCommandHistory().addCommand(this);
        currentModel.getCurrentProject().inform();
    }

    /**
     * Undoes the command by simply removing the product library
     */
    public void undo() {
        ArrayList<Library> libraries = currentModel.getCurrentProject().getLibraries();
        libraries.remove(productLibrary);
        currentModel.getCurrentProject().setLibraries(libraries);
        currentModel.getCurrentProject().inform();
    }
}

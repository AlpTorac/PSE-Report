package gelf.model.commands;

import java.util.ArrayList;

import gelf.model.elements.Cell;
import gelf.model.elements.Library;
import gelf.model.project.Model;

/**
 * A command that handles the merging of several libraries
 * 
 */
public class MergeCommand implements Command {
    private ArrayList<Library> mergedLibraries;
    private Library productLibrary;
    private String name;
    private Model currentModel = Model.getInstance();

    public MergeCommand(String name, ArrayList<Library> mergedLibraries) {
        this.mergedLibraries = mergedLibraries;
        this.name = name;
    }

    public void execute() {
        ArrayList<Cell> cells = new ArrayList<Cell>();
        for (Library library : mergedLibraries) {
            for (Cell cell : library.getCells()) {
                cells.add(cell);
            }
        }
        NameConflictResolver conflictResolver = new NameConflictResolver(cells);
        cells = conflictResolver.getCells();
        if (cells.isEmpty()) {
            return;
        }
        for (int i = 0; i < cells.size(); i++) {
        	cells.set(i, (Cell) cells.get(i).clone());
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
        ArrayList<Library> libraries = currentModel.getCurrentProject().getLibraries();
        libraries.add(productLibrary);
        currentModel.getCurrentProject().setLibraries(libraries);
        currentModel.getCurrentCommandHistory().addCommand(this);
        currentModel.getCurrentProject().inform();
    }

    public void undo() {
    	currentModel.getCurrentProject().getLibraries().remove(productLibrary);
        currentModel.getCurrentProject().inform();
    }
}

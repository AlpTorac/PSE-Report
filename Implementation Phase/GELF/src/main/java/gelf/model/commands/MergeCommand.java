package gelf.model.commands;

import gelf.model.parsers.LibertyParser;
import gelf.model.elements.Library;
import gelf.model.elements.Cell;
import gelf.model.commands.NameConflictResolver;
import gelf.model.project.Model;

import java.util.ArrayList;


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
            cells.addAll(library.getCells());
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
            if (!currentCell.getIndex1().equals(index1) || !currentCell.getIndex1().equals(index2)) {
                currentCell.interpolate(index1, index2);
            }
        }
        productLibrary = new Library(name, index1, index2, null, cells);
        currentModel.getCurrentProject().getLibraries().add(productLibrary);
        currentModel.getCurrentCommandHistory().addCommand(this);
        currentModel.getCurrentProject().inform();
    }

    public void undo() {
    	currentModel.getCurrentProject().getLibraries().remove(productLibrary);
        currentModel.getCurrentProject().inform();
    }
}

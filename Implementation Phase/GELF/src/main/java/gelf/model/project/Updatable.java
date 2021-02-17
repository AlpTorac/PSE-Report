package gelf.model.project;

/**
 * An interface for all updatable view components that change once something
 * changes in the project
 * @author Xhulio Pernoca
 */
public interface Updatable {

    /**
     * Updated the content of the component
     */
    public void update();
}
package gelf.model.commands;

/**
 * Holds data about a naming conflict resolution
 * @author Xhulio Pernoca
 */
public class ConflictData {
    private ResolutionMethod resolutionMethod;
    private String name;

    /**
     * Initialises the conflict data
     * @param resolutionMethod the chosen resolution method
     * @param name the name of the renaming action, if it happens to be the chosen 
     * resolution method
     */
    public ConflictData(ResolutionMethod resolutionMethod, String name) {
        this.resolutionMethod = resolutionMethod;
        this.name = name;
    }

    /**
     * Returns the resolution method
     * @return the resolution method
     */
    public ResolutionMethod getResolutionMethod() {
        return resolutionMethod;
    }

    /**
     * returns the given name string
     * @return the given name string
     */
    public String getName() {
        return name;
    }

}

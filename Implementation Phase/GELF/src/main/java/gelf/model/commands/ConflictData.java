package gelf.model.commands;

public class ConflictData {
    private ResolutionMethod resolutionMethod;
    private String name;

    public ConflictData(ResolutionMethod resolutionMethod, String name) {
        this.resolutionMethod = resolutionMethod;
        this.name = name;
    }

    public ResolutionMethod getResolutionMethod() {
        return resolutionMethod;
    }

    public String getName() {
        return name;
    }

}

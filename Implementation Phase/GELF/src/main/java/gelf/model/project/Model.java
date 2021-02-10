package gelf.model.project;

import gelf.model.commands.CommandHistory;

public class Model {
    private static Model instance = new Model();
    private Project currentProject;
    private CommandHistory currentCommandHistory;

    private Model() {
        currentProject = new Project();
        currentCommandHistory = new CommandHistory();
    }

    public static Model getInstance() {
        return instance;
    }

    public Project getCurrentProject() {
        return currentProject;
    }

    public CommandHistory getCurrentCommandHistory() {
        return currentCommandHistory;
    }

    public void inform() {
        currentProject.inform();
    }
}

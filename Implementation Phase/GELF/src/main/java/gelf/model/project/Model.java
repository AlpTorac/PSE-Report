package gelf.model.project;

import java.text.DecimalFormat;

import gelf.model.commands.CommandHistory;

public class Model {
    private static Model instance = new Model();
    private Project currentProject;
    private CommandHistory currentCommandHistory;
    private static DecimalFormat format = new DecimalFormat("#.#######");
    private static DecimalFormat scFormat = new DecimalFormat("0.###E00");

    private Model() {
        currentProject = new Project();
        currentCommandHistory = new CommandHistory();
    }
    
    public static String formatFloat(float number) {
    	if (number < 0.0001) {
            return scFormat.format(number).toLowerCase();
    	} else {
    		return format.format(number);
    	}
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

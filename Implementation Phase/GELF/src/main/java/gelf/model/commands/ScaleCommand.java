package gelf.model.commands;

import gelf.model.elements.attributes.Attribute;
import gelf.model.project.Model;

public class ScaleCommand implements Command {
    private Attribute attribute;
    private float scale;
    private Model currentModel = Model.getInstance(); 

    public ScaleCommand(Attribute attribute, float scale) {
        this.attribute = attribute;
        this.scale = scale;
    }

    public void execute() {
        attribute.scale(scale);
        currentModel.getCurrentCommandHistory().addCommand(this);
		currentModel.getCurrentProject().inform();
    }

    public void undo() {
    	attribute.scale(1/scale);
		currentModel.getCurrentProject().inform();
    }
}

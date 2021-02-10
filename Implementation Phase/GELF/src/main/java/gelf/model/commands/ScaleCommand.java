package gelf.model.commands;

import gelf.model.elements.attributes.Attribute;
import gelf.model.project.Model;

public class ScaleCommand implements Command {
    Attribute attribute;
    float scale;

    public ScaleCommand(Attribute attribute, float scale) {
        this.attribute = attribute;
        this.scale = scale;
    }

    public void execute() {
        attribute.scale(scale);
        Model.getInstance().getCurrentCommandHistory().addCommand(this);
    }

    public void undo() {

    }
}

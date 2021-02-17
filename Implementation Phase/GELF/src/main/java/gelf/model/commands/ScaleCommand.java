package gelf.model.commands;

import gelf.model.elements.attributes.Attribute;
import gelf.model.project.Model;

/**
 * Scales an attribute to a given scale
 * @author Xhulio Pernoca
 */
public class ScaleCommand implements Command {
    private Attribute attribute;
    private float scale;
    private Model currentModel = Model.getInstance(); 

    /**
     * Instantiates a command
     * @param attribute the attribute to be scaled
     * @param scale the scaling value
     */
    public ScaleCommand(Attribute attribute, float scale) {
        this.attribute = attribute;
        this.scale = scale;
    }

    /**
     * Executes the scaling
     */
    public void execute() {
        attribute.scale(scale);
        currentModel.getCurrentCommandHistory().addCommand(this);
		currentModel.getCurrentProject().inform();
    }

    /**
     * Undoes the scaling 
     */
    public void undo() {
    	attribute.scale(1 / scale);
		currentModel.getCurrentProject().inform();
    }
}

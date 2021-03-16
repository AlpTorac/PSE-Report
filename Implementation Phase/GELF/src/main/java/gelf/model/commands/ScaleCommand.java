package gelf.model.commands;

import gelf.model.elements.attributes.Attribute;
import gelf.model.project.Model;
import gelf.view.composites.Visualizer;

/**
 * Scales an attribute to a given scale
 * @author Xhulio Pernoca, Kerem Kara
 */
public class ScaleCommand implements Command {
    private float scaleValue;
    private Model currentModel = Model.getInstance(); 
    private Visualizer vis;
    /**
     * Instantiates a command
     * @param attribute the attribute to be scaled
     * @param scale the scaling value
     */
    public ScaleCommand(float scaleValue, Visualizer vis) {
        this.scaleValue = scaleValue;
        this.vis = vis;
    }

    /**
     * Executes the scaling
     */
    public void execute() {
    	vis.setScaleValue(scaleValue);
    	vis.update();
        currentModel.getCurrentCommandHistory().addCommand(this);
		currentModel.getCurrentProject().inform();
    }

    /**
     * Undoes the scaling 
     */
    public void undo() {
    	scaleValue = 1 / scaleValue;
    	vis.setScaleValue(scaleValue);
    	vis.update();
		currentModel.getCurrentProject().inform();
    }
}

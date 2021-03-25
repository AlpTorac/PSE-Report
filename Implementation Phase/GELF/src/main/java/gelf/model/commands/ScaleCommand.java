package gelf.model.commands;

import gelf.model.elements.Cell;
import gelf.model.elements.Element;
import gelf.model.elements.InputPin;
import gelf.model.elements.Library;
import gelf.model.elements.OutputPin;
import gelf.model.elements.attributes.InputPower;
import gelf.model.elements.attributes.OutputPower;
import gelf.model.elements.attributes.Timing;
import gelf.model.project.Model;
import gelf.model.project.Project;

/**
 * Scales an attribute to a given scale
 * @author Xhulio Pernoca, Kerem Kara
 */
public class ScaleCommand implements Command {
    private float scaleValue;
    private Model currentModel = Model.getInstance(); 
    private InputPower inPow;
    private Element element; 
    private Element oldEl;
    private OutputPower outPow;
    private Timing tim;
    private String attr;
    /**
     * Instantiates a command
     * @param attribute the attribute to be scaled
     * @param scale the scaling value
     */
    public ScaleCommand(float scaleValue, Element element, String attr, 
    		InputPower inPow, OutputPower outPow, Timing tim) {
        this.scaleValue = scaleValue;
        this.element = element;
        this.attr = attr;
        this.inPow = inPow;
        this.outPow = outPow;
        this.tim = tim;
        
    }

    /**
     * Executes the scaling
     */
    public void execute() {
    	if (element.getClass() == Library.class) {
    		Library lib = (Library) element;
    		oldEl = lib.clone();
    		if (attr.equals("Timing")) lib.scaleTiming(scaleValue);
    		else if (attr.equals("Default Leakage")) lib.scaleDefaultLeakage(scaleValue);
    		else if (attr.equals("Leakage")) lib.scaleLeakages(scaleValue); 
    		else if (attr.equals("Output Power")) lib.scaleOutputPower(scaleValue);
    		else if (attr.equals("Input Power")) lib.scaleInputPower(scaleValue);
    	}
    	else if (element.getClass() == Cell.class) {
    		Cell cell = (Cell) element;
    		oldEl = cell.clone();
    		if (attr.equals("Timing")) cell.scaleTiming(scaleValue);
    		else if (attr.equals("Output Power")) cell.scaleOutputPower(scaleValue);
    		else if (attr.equals("Input Power")) cell.scaleInputPower(scaleValue);
    		else if (attr.equals("Leakage")) cell.getLeakages().scale(scaleValue);
    	}
    	if (element.getClass() == InputPin.class) {
    		inPow.scale(scaleValue);
    	}
    	if (element.getClass() == OutputPin.class) {
    		if (attr.equals("Timing")) tim.scale(scaleValue);
    		else if (attr.equals("Output Power")) outPow.scale(scaleValue);
    	}
        currentModel.getCurrentCommandHistory().addCommand(this);
		currentModel.getCurrentProject().inform();
    }

    /**
     * Undoes the scaling 
     */
    public void undo() {
    	if (scaleValue == 0) {
    		Project.replaceElementData(element, oldEl);
    	}
    	else {
    		scaleValue = 1 / scaleValue;
    		execute();
    	}
		currentModel.getCurrentProject().inform();
    }
}

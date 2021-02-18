package gelf.view.composites;

import gelf.model.elements.*;
import gelf.model.project.Project;

import java.awt.*;
import java.util.ArrayList;

import gelf.view.components.Panel;

/**
 * ElementManipulator
 */
public abstract class ElementManipulator extends Panel {
    gelf.model.elements.Element element;
    Project project;
    ArrayList<Element> elements;

    public ElementManipulator(gelf.model.elements.Element e, Project p, int width, int height) {
        super(width, height);
        this.element = e;
        this.project = p;
        this.setBackground(ColorTheme.section);
    }
    
    public ElementManipulator(ArrayList<Element> elements, Project p, int width, int height) {
    	super(width, height);
    	this.elements = elements;
    	this.project = p;
    }

    public gelf.model.elements.Element getElement() {
        return this.element;
    }

    public void setElement(gelf.model.elements.Element e) {
        this.element = e;
    }
}

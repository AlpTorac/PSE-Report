package gelf.view.composites;

import gelf.model.elements.Element;
import gelf.model.project.Project;

import java.awt.*;

import gelf.view.components.Panel;
/**
 * Visualizer
 */
public class Visualizer extends ElementManipulator {

    public Visualizer(gelf.model.elements.Element e, Project p, int width, int height) {
        super(e, p, width, height);
        this.setBackground(Color.green);
    }
}
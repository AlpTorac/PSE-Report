package gelf.view.composites;

import gelf.model.elements.Element;
import java.awt.*;

import gelf.view.components.Panel;
/**
 * Visualizer
 */
public class Visualizer extends ElementManipulator {

    public Visualizer(gelf.model.elements.Element e, int width, int height) {
        super(e, width, height);
        this.setBackground(Color.green);
    }
}
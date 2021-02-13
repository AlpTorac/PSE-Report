package gelf.view.composites;

import javax.lang.model.element.Element;
import java.awt.*;

import gelf.view.components.Panel;

/**
 * ElementManipulator
 */
public abstract class ElementManipulator extends Panel {
    gelf.model.elements.Element element;

    public ElementManipulator(gelf.model.elements.Element e, int width, int height) {
        super(width, height);
        this.element = e;
        this.setBackground(Color.red);
    }

    public gelf.model.elements.Element getElement() {
        return this.element;
    }
}
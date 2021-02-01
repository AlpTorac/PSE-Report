package gelf.view.components;

import java.awt.*;
import java.awt.event.*;

/**
 * AutoResizing
 */
public interface AutoResizing extends ComponentListener {
    //sets resizer for given component
    public void setResizer(Component c, Resizer r);
}
package gelf.view.composites;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gelf.model.elements.Element;
import gelf.view.components.Button;
import gelf.view.components.Label;
import gelf.view.components.MenuBar;
import gelf.view.components.Panel;

/**
 * SubWindow
 */
public class SubWindow extends Panel {
    Panel bar;
    Button close;
    Label path;
    private Color cBackground = new Color(0.23f, 0.23f, 0.23f);
    private Color cBorder = new Color(0.15f, 0.15f, 0.15f);
    SubWindow _this = this;

    public SubWindow(ElementManipulator em, SubWindowArea parent, int width, int height) {
        super(width, height);
        // style
        this.setLayout(new BorderLayout());
        this.setBackground(cBackground);
        Border margin = BorderFactory.createLineBorder(cBorder, 2);
        this.setBorder(margin);
        // menu
        this.bar = new Panel(this.getWidth(), 30);
        this.bar.setLayout(new BoxLayout(this.bar, BoxLayout.LINE_AXIS));
        this.bar.setVisible(true);
        // close button
        this.close = new Button("Close");
        this.close.setVisible(true);
        this.close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.removeSubWindow(_this);
            }
        });
        //path/name label
        this.path = new Label(em.getElement().toString());
        this.path.setVisible(true);

        this.bar.add(this.close);
        this.bar.add(this.path);
        this.bar.add(Box.createHorizontalGlue());

        this.add(this.bar, BorderLayout.PAGE_START);
        this.add(em, BorderLayout.CENTER);
    }
}
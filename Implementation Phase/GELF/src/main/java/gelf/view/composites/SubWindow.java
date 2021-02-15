package gelf.view.composites;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.border.Border;
import javax.swing.plaf.DimensionUIResource;

import org.w3c.dom.Text;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import gelf.controller.listeners.SaveAsListener;
import gelf.controller.listeners.SaveListener;
import gelf.model.elements.Element;
import gelf.model.project.Project;
import gelf.model.project.Updatable;
import gelf.view.components.Button;
import gelf.view.components.DropdownSelector;
import gelf.view.components.Label;
import gelf.view.components.MenuBar;
import gelf.view.components.Panel;

/**
 * SubWindow
 */
public class SubWindow extends Panel {
    Panel bar;
    Button close;
    DropdownSelector dropdown;
    Label path;
    private Color cBackground = new Color(0.23f, 0.23f, 0.23f);
    private Color cBorder = new Color(0.15f, 0.15f, 0.15f);
    SubWindow _this = this;
    //element manipulators
    public enum ManipulatorType {
        VISUALIZER("Visualizer"),
        TEXT_EDITOR("Text Editor");
        private String str;
        private ManipulatorType(String s){
            this.str = s;
        }

        @Override
        public String toString() {
            return this.str;
        }
    }
    private Element e;
    private ElementManipulator activeManipulator;
    private Visualizer eVisualizer;
    private TextEditor eTextEditor;

    public SubWindow(Element e, Project p, SubWindowArea parent, int width, int height) {
        super(width, height);
        this.e = e;
        //element manipulators
        this.eVisualizer = new Visualizer(e,this, p, 200, 100);
        this.eVisualizer.setVisible(true);
        this.eTextEditor = new TextEditor(e, p, 200, 100);
        this.eTextEditor.setVisible(true);
        this.activeManipulator = eVisualizer;
        // style
        this.setLayout(new BorderLayout());
        this.setBackground(cBackground);
        Border margin = BorderFactory.createLineBorder(cBorder, 2);
        this.setBorder(margin);
        this.setPreferredSize(new Dimension(300, 200));
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
        //dropdown element manipulator type
        ManipulatorType[] options = {ManipulatorType.VISUALIZER, ManipulatorType.TEXT_EDITOR};
        this.dropdown  = new DropdownSelector(options);
        this.dropdown.setPreferredSize(new Dimension(10, 10));
        this.dropdown.setVisible(true);
        this.dropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DropdownSelector src = (DropdownSelector)e.getSource();
                ManipulatorType manip = (ManipulatorType)src.getSelectedItem();
                _this.setManipulatorType(manip);
            }
        });
        //path/name label
        this.path = new Label(e.toString());
        this.path.setVisible(true);

        this.bar.add(this.close);
        this.bar.add(this.dropdown);
        this.bar.add(this.path);
        this.bar.add(Box.createHorizontalGlue());

        this.add(this.bar, BorderLayout.PAGE_START);
        this.add(this.eVisualizer, BorderLayout.CENTER);
    }

    public void setElement(Element e) {
    	this.e = e;
    	this.path.setText(e.getName());
        this.eVisualizer.setElement(e);
        this.eTextEditor.setElement(e);
    }
    
    public Element getElement() {
    	return e;
    }

    public void setManipulatorType(ManipulatorType m) {
        this.remove(this.activeManipulator);
        switch(m) {
            case VISUALIZER:
                this.add(this.eVisualizer, BorderLayout.CENTER);
                this.activeManipulator = this.eVisualizer;
            break;
            case TEXT_EDITOR:
                this.add(this.eTextEditor, BorderLayout.CENTER);
                this.activeManipulator = this.eTextEditor;
            break;
            default:
                System.out.println("ERROR: Unimplemented Element Manipulator");
            break;
        }
        this.revalidate();
        this.repaint();
    }
}

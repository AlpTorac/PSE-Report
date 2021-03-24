package gelf.view.composites;

import java.awt.Color;
import java.awt.Component;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import gelf.model.elements.Cell;
import gelf.model.elements.InputPin;
import gelf.model.elements.Library;
import gelf.model.elements.OutputPin;

public class HierarchyRenderer extends DefaultTreeCellRenderer {
    private static final String SPAN_FORMAT = "<span style='color:%s;'><b>%s</b></span>";
    private ImageIcon libraryIcon;
    private ImageIcon cellIcon;
    private ImageIcon outPinIcon;
    private ImageIcon inPinIcon;

    public HierarchyRenderer() {
        try{
            libraryIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage("src/main/resources/LibraryIcon.png"));
            cellIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage("src/main/resources/CellIcon.png"));
            outPinIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage("src/main/resources/OutPinIcon.png"));
            inPinIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage("src/main/resources/inPinIcon.png"));
        } catch (Exception e) {
            System.out.println("Icon not loaded "+ e.getClass());
        }
    }
  
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        Object userObject = node.getUserObject();
        String text;
        if (userObject instanceof Library) {
            Library lib = (Library) userObject;
            text = String.format(SPAN_FORMAT, "#D3D3D3", lib.toString());
            this.setIcon(libraryIcon);
        } else if (userObject instanceof Cell) {
            Cell cell = (Cell) userObject;
            text = String.format(SPAN_FORMAT, "#D3D3D3", cell.toString());
            this.setIcon(cellIcon);
        } else if (userObject instanceof InputPin) {
            InputPin pin = (InputPin) userObject;
            text = String.format(SPAN_FORMAT, "#D3D3D3", pin.toString());
            this.setIcon(inPinIcon);
        } else if (userObject instanceof OutputPin) {
            OutputPin pin = (OutputPin) userObject;
            text = String.format(SPAN_FORMAT, "#D3D3D3", pin.toString());
            this.setIcon(outPinIcon);
        }else {
            text = String.format(SPAN_FORMAT, "yellow", userObject);
        }
        this.setText("<html>" + text + "</html>");
        this.setBorder(new EmptyBorder(2, 2, 2, 2));
        return this;
    }

    @Override
    public Color getBackground() {
        return null;
    }

    @Override
    public Color getBackgroundSelectionColor() {
        return ColorTheme.selected;
    }

    @Override
    public Color getBackgroundNonSelectionColor() {
        return null;
    }
  }
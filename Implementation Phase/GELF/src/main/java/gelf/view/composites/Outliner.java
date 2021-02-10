package gelf.view.composites;

import gelf.model.elements.Cell;
import gelf.model.elements.InputPin;
import gelf.model.elements.Library;
import gelf.model.elements.OutputPin;
import gelf.model.project.Project;
import gelf.model.project.Updatable;
import gelf.view.components.Label;
import gelf.view.components.Menu;
import gelf.view.components.MenuBar;
import gelf.view.components.Panel;
import gelf.view.components.Tree;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.plaf.basic.BasicBorders;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import java.util.ArrayList;

/**
 * Outliner
 */
public class Outliner extends Panel implements Updatable, TreeSelectionListener {
    Project project;
    MenuBar menuBar;
    JTree tree;
    JScrollPane treePane;

    // colors
    private Color cBackground = new Color(0.23f, 0.23f, 0.23f);
    private Color cBorder = new Color(0.15f, 0.15f, 0.15f);

    private Color cTree = new Color(0.2f, 0.2f, 0.2f);
    private Color CNode = new Color(0.3f, 0.3f, 0.3f);
    private Color cText = new Color(0.8f, 0.8f, 0.8f);

    public Outliner(int width, int height, Project project) {
        super(width, height);
        this.project = project;
        // style
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.setBackground(cBackground);
        Border margin = new EmptyBorder(5, 5, 5, 5);
        margin = BorderFactory.createLineBorder(cBorder, 5);
        this.setBorder(margin);

        // menu
        Menu menu = new Menu("View");
        Menu menu2 = new Menu("Sort");
        this.menuBar = new MenuBar();
        this.menuBar.setBackground(Color.red);
        this.menuBar.setPreferredSize(new Dimension(this.getWidth(), 30));
        this.menuBar.setVisible(true);
        this.menuBar.add(menu);
        this.menuBar.add(menu2);
        this.add(this.menuBar);

        // tree
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
        DefaultTreeModel treeModel = new DefaultTreeModel(root);
        this.tree = new JTree(treeModel);
        this.tree.setEditable(true);
        this.tree.setBackground(cTree);
        this.tree.setShowsRootHandles(true);
        this.tree.expandRow(0);
        // this.tree.setRootVisible(false);

        // scroll pane
        this.treePane = new JScrollPane(tree);
        this.treePane.setPreferredSize(new Dimension(this.getWidth(), this.getHeight() - this.menuBar.getHeight()));
        this.treePane.setVisible(true);
        this.treePane.setBorder(null);
        this.add(treePane);

        this.add(Box.createVerticalGlue());

        // subscribe to project
        project.addUpdatable(this);
        this.update();
    }

    @Override
    public void update() {
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) this.tree.getModel().getRoot();
        DefaultTreeModel treeModel = (DefaultTreeModel) this.tree.getModel();

        // generate library level
        ArrayList<Library> libraries = project.getLibraries();
        for (Library lib : libraries) {
            DefaultMutableTreeNode libNode = new DefaultMutableTreeNode(lib.getName());
            treeModel.insertNodeInto(libNode, root, root.getChildCount());

            // generate cell levels
            ArrayList<Cell> cells = lib.getCells();
            for (Cell cell : cells) {
                DefaultMutableTreeNode cellNode = new DefaultMutableTreeNode(cell.getName());
                treeModel.insertNodeInto(cellNode, libNode, libNode.getChildCount());
                // generate in/out pin levels
                ArrayList<InputPin> pinsIn = cell.getInPins();
                ArrayList<OutputPin> pinsOut = cell.getOutPins();
                // input pins
                for (InputPin pinIn : pinsIn) {
                    DefaultMutableTreeNode pinInNode = new DefaultMutableTreeNode(pinIn.getName() + "(In)");
                    treeModel.insertNodeInto(pinInNode, cellNode, cellNode.getChildCount());
                }
                // output pins
                for (OutputPin pinOut : pinsOut) {
                    DefaultMutableTreeNode pinOutNode = new DefaultMutableTreeNode(pinOut.getName() + "(Out)");
                    treeModel.insertNodeInto(pinOutNode, cellNode, cellNode.getChildCount());
                }
            }
        }
    }

    //TreeSelectionListener method
    @Override
    public void valueChanged(TreeSelectionEvent e) {
        
    }
}
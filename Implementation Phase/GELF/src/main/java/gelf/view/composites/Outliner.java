package gelf.view.composites;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.*;
import javax.swing.tree.*;

import gelf.controller.EventManager;
import gelf.model.elements.Cell;
import gelf.model.elements.Element;
import gelf.model.elements.InputPin;
import gelf.model.elements.Library;
import gelf.model.elements.OutputPin;
import gelf.model.project.Project;
import gelf.model.project.Updatable;
import gelf.view.components.Menu;
import gelf.view.components.MenuBar;
import gelf.view.components.MenuItem;
import gelf.view.components.Panel;
import gelf.view.components.TreeMouseAdapter;

/**
 * Outliner
 */
public class Outliner extends Panel implements Updatable, TreeSelectionListener {
    public Project project;
    public MenuBar menuBar;
    public JTree tree;
    public JScrollPane treePane;
    public JTextField searchBox;
    public Menu menuView;
        public MenuItem itemOpen;
        public MenuItem itemCopy;
        public MenuItem itemPaste;
        public MenuItem itemDelete;
        public MenuItem itemRemove;
        public MenuItem itemCompare;
        public MenuItem itemProperties;
        public EventManager em;
    // colors
    private Color cBackground = ColorTheme.section;
    private Color cBorder = ColorTheme.frame;

    private Color cTree = new Color(0.2f, 0.2f, 0.2f);
    private Color CNode = new Color(0.3f, 0.3f, 0.3f);
    private Color cText = new Color(0.8f, 0.8f, 0.8f);

    public Outliner(int width, int height, Project project) {
        super(width, height);
        this.project = project;
        // style
        this.setLayout(new BorderLayout());
        this.setBackground(cBackground);
        Border margin = new EmptyBorder(5, 5, 5, 5);
        margin = BorderFactory.createLineBorder(cBorder, 5);
        this.setBorder(margin);

        // menu
        this.searchBox = new JTextField();
        this.menuBar = new MenuBar();
        this.menuBar.setBackground(cBackground);
        this.menuBar.setPreferredSize(new Dimension(this.getWidth(), 30));
        this.menuBar.setVisible(true);

        this.menuView = new Menu("View");
        this.itemOpen = new MenuItem("Open");
        this.itemCopy = new MenuItem("Copy");
        this.itemPaste = new MenuItem("Paste");
        this.itemDelete = new MenuItem("Delete");
        this.itemRemove = new MenuItem("Remove");
        this.itemCompare = new MenuItem("Compare");
        this.itemProperties = new MenuItem("Properties");
        this.menuView.add(this.itemOpen);
        this.menuView.add(this.itemCopy);
        this.menuView.add(this.itemPaste);
        this.menuView.add(this.itemDelete);
        this.menuView.add(this.itemRemove);
        this.menuView.add(this.itemCompare);
        this.menuView.add(this.itemProperties);
        this.menuBar.add(menuView);
        this.menuBar.add(searchBox);
        this.add(this.menuBar, BorderLayout.PAGE_START);

        // tree
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
        DefaultTreeModel treeModel = new DefaultTreeModel(root);
        this.tree = new JTree(treeModel);
        //this.tree.setEditable(true);
        this.tree.setBackground(cTree);
        this.tree.setShowsRootHandles(true);
        this.tree.expandRow(0);
        this.tree.addTreeSelectionListener(this);   //respond to selection
        // this.tree.setRootVisible(false);

        // scroll pane
        this.treePane = new JScrollPane(this.tree);
        this.treePane.setPreferredSize(new Dimension(this.getWidth(), this.getHeight() - this.menuBar.getHeight()));
        this.treePane.setVisible(true);
        this.treePane.setBorder(null);
        this.add(treePane, BorderLayout.CENTER);

        // subscribe to project
        this.project.addUpdatable(this);
        this.update();

    }

    @Override
    public void update() {
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) this.tree.getModel().getRoot();
        root.removeAllChildren();
        DefaultTreeModel treeModel = (DefaultTreeModel) this.tree.getModel();
        treeModel.nodeStructureChanged(root);

        // generate library level
        ArrayList<Library> libraries = this.project.getLibraries();
        for (Library lib : libraries) {
            DefaultMutableTreeNode libNode = new DefaultMutableTreeNode(lib);
            treeModel.insertNodeInto(libNode, root, root.getChildCount());

            // generate cell levels
            ArrayList<Cell> cells = lib.getCells();
            for (Cell cell : cells) {
                DefaultMutableTreeNode cellNode = new DefaultMutableTreeNode(cell);
                treeModel.insertNodeInto(cellNode, libNode, libNode.getChildCount());
                // generate in/out pin levels
                ArrayList<InputPin> pinsIn = cell.getInPins();
                ArrayList<OutputPin> pinsOut = cell.getOutPins();
                // input pins
                for (InputPin pinIn : pinsIn) {
                    DefaultMutableTreeNode pinInNode = new DefaultMutableTreeNode(pinIn);
                    treeModel.insertNodeInto(pinInNode, cellNode, cellNode.getChildCount());
                }
                // output pins
                for (OutputPin pinOut : pinsOut) {
                    DefaultMutableTreeNode pinOutNode = new DefaultMutableTreeNode(pinOut);
                    treeModel.insertNodeInto(pinOutNode, cellNode, cellNode.getChildCount());
                }
            }
        }
        this.revalidate();
        this.repaint();
    }
    //get list of all selected elements
    public ArrayList<Element> getSelectedElements() {
        ArrayList<Element> result = new ArrayList<Element>();
        TreePath[] paths = this.tree.getSelectionPaths();
        for(TreePath path : paths) {
            if(Element.class.isAssignableFrom(((DefaultMutableTreeNode)path.getLastPathComponent()).getUserObject().getClass())) {
                result.add((Element)((DefaultMutableTreeNode)path.getLastPathComponent()).getUserObject());
            }
        }
        return result;
    }

    public void setTreeMouseListener(EventManager em) {
		tree.addMouseListener(new TreeMouseAdapter(em));
    }

    //TreeSelectionListener method
    @Override
    public void valueChanged(TreeSelectionEvent e) {
        //TODO
    }

}

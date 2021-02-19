package gelf.view.components;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.EventListener;
import java.util.HashMap;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import gelf.controller.Event;
import gelf.controller.EventManager;
import gelf.model.elements.Cell;
import gelf.model.elements.Element;
import gelf.model.elements.Library;

/**
 * Class providing right clicking functionality on the JTree component
 * @author Xhulio Pernoca
 */
public class TreeMouseAdapter extends MouseAdapter{
    HashMap<Event, EventListener> listeners;

    /**
     * Constructor for the Adapter
     * @param em the EventManager instance
     */
    public TreeMouseAdapter(EventManager em) {
        this.listeners = em.getListeners();
    }

    /**
     * Event once a tree node is right clicked
     * @param e the mouse event
     */
    private void treeRightClickEvent(MouseEvent e) {
        // gets the path where the right click event has been called
        JTree tree = (JTree) e.getSource();
        int xCoordinate = e.getX();
        int yCoordinate = e.getY();
        TreePath path = tree.getPathForLocation(xCoordinate, yCoordinate);
        if (path == null)
          return;
    
        // gets the node that has been right clicked
        DefaultMutableTreeNode rightClickedNode = (DefaultMutableTreeNode) path.getLastPathComponent();
    
        //gets the selected paths and checks whether what was right clicked is selected
        TreePath[] selectionPaths = tree.getSelectionPaths();
        boolean isSelected = false;
        if (selectionPaths != null) {
          for (TreePath selectionPath : selectionPaths) {
            if (selectionPath.equals(path)) {
              isSelected = true;
            }
          }
        }
        //if it isn't selected, selects only it
        if (!isSelected) {
            tree.setSelectionPath(path);
        }
        // if root is selected, simply returns
        if (!(rightClickedNode.getUserObject() instanceof Element)) {
            return;
        }
        // creates the popup with the desired elements linked to their listeners
        JPopupMenu popup = new JPopupMenu();
        final JMenuItem open = new JMenuItem("Open");
        open.addActionListener((ActionListener) listeners.get(Event.OPEN));
        popup.add(open);
        final JMenuItem compare = new JMenuItem("Compare");
        compare.addActionListener((ActionListener) listeners.get(Event.COMPARE));
        popup.add(compare);
        final JMenuItem rename = new JMenuItem("Rename");
        rename.addActionListener((ActionListener) listeners.get(Event.RENAME));
        popup.add(rename);
        if (rightClickedNode.getUserObject() instanceof Library) {
            final JMenuItem merge = new JMenuItem("Merge");
            merge.addActionListener((ActionListener) listeners.get(Event.MERGE));
            popup.add(merge);
            final JMenuItem save = new JMenuItem("Save");
            save.addActionListener((ActionListener) listeners.get(Event.SAVE));
            popup.add(save);
            final JMenuItem saveAs = new JMenuItem("Save As");
            saveAs.addActionListener((ActionListener) listeners.get(Event.SAVEAS));
            popup.add(saveAs);
            final JMenuItem remove = new JMenuItem("Remove");
            remove.addActionListener((ActionListener) listeners.get(Event.REMOVE));
            popup.add(remove);
            final JMenuItem paste = new JMenuItem("Paste");
            paste.addActionListener((ActionListener) listeners.get(Event.PASTE));
            popup.add(paste);
        } else if (rightClickedNode.getUserObject() instanceof Cell) {
            final JMenuItem delete = new JMenuItem("Delete");
            delete.addActionListener((ActionListener) listeners.get(Event.DELETE));
            popup.add(delete);
            final JMenuItem copy = new JMenuItem("Copy");
            copy.addActionListener((ActionListener) listeners.get(Event.COPY));
            popup.add(copy);
        }
        popup.show(tree, xCoordinate, yCoordinate);
      }
    
      /**
       * Invokes the method treeRightClickEvent when invoked by a right click
       */
      public void mousePressed(MouseEvent e) {
        if (e.isPopupTrigger())
          treeRightClickEvent(e);
      }
    
      /**
       * Checked for cross-platform functionality, same functionality as mousePressed().
       */
      public void mouseReleased(MouseEvent e) {
        if (e.isPopupTrigger())
          treeRightClickEvent(e);
      }
}

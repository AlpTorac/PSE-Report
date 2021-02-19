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
import gelf.controller.listeners.CompareListener;
import gelf.controller.listeners.CopyListener;
import gelf.controller.listeners.DeleteCellListener;
import gelf.controller.listeners.MergeListener;
import gelf.controller.listeners.OpenElementListener;
import gelf.controller.listeners.PasteListener;
import gelf.controller.listeners.RemoveListener;
import gelf.controller.listeners.RenameListener;
import gelf.controller.listeners.SaveAsListener;
import gelf.controller.listeners.SaveListener;
import gelf.model.elements.Cell;
import gelf.model.elements.Library;
import gelf.view.composites.Outliner;
import gelf.view.composites.SubWindowArea;

public class TreeMouseAdapter extends MouseAdapter{
    HashMap<Event, EventListener> listeners;

    public TreeMouseAdapter(EventManager em) {
        this.listeners = em.getListeners();
    }

    private void myPopupEvent(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        JTree tree = (JTree) e.getSource();
        TreePath path = tree.getPathForLocation(x, y);
        if (path == null)
          return;
    
        DefaultMutableTreeNode rightClickedNode = (DefaultMutableTreeNode) path
            .getLastPathComponent();
    
        TreePath[] selectionPaths = tree.getSelectionPaths();
    
        boolean isSelected = false;
        if (selectionPaths != null) {
          for (TreePath selectionPath : selectionPaths) {
            if (selectionPath.equals(path)) {
              isSelected = true;
            }
          }
        }
        if (!isSelected) {
            tree.setSelectionPath(path);
        }
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
        popup.show(tree, x, y);
      }
    
      public void mousePressed(MouseEvent e) {
        if (e.isPopupTrigger())
          myPopupEvent(e);
      }
    
      public void mouseReleased(MouseEvent e) {
        if (e.isPopupTrigger())
          myPopupEvent(e);
      }
}

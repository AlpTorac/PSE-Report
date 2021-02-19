package gelf.controller;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultTreeModel;

import gelf.controller.listeners.*;
import gelf.model.project.Model;
import gelf.view.composites.MainWindow;;

/**
 * Stores and initializes the listeners.
 * @author Ege Uzhan
 */
public class EventManager {
	
	private MainWindow view;
	private HashMap<Event, EventListener> listeners;
	
	/**
	 * Initializes the manager
	 * @param view GUI view
	 * @param model Model class
	 */
	public EventManager(MainWindow view) {
		
		this.view = view;
		listeners = new HashMap<Event, EventListener>();
		initListeners();
	}
	
	/**
	 * Returns a map of listeners.
	 * @return HashMap of events mapped to listeners.
	 */
	public HashMap<Event, EventListener> getListeners(){
		return listeners;
	}
	
	/**
	 * Initializes the listeners.
	 */
	public void initListeners() {
		listeners.put(Event.LOAD, new LoadListener());
		listeners.put(Event.OPEN, new OpenElementListener(view.outliner, view.subWindowArea));
		listeners.put(Event.DELETE, new DeleteCellListener(view.outliner));
		listeners.put(Event.REMOVE, new RemoveListener(view.outliner));
		listeners.put(Event.COPY, new CopyListener(view.outliner));
		listeners.put(Event.PASTE, new PasteListener(view.outliner));
		listeners.put(Event.MOVE, new MoveListener(view.outliner));
		listeners.put(Event.MERGE, new MergeListener(view.outliner));
		listeners.put(Event.SEARCH, new SearchListener(view.outliner));
		listeners.put(Event.RENAME, new RenameListener(view.outliner, view.subWindowArea));
		listeners.put(Event.SAVE, new SaveListener(view.outliner));
		listeners.put(Event.SAVEAS, new SaveAsListener(view.outliner));
		listeners.put(Event.SAVEALL, new SaveAllListener());
		listeners.put(Event.LOADPROJECT, new LoadProjectListener());
		listeners.put(Event.UNDO, new UndoListener());
		listeners.put(Event.REDO, new RedoListener());
		listeners.put(Event.COMPARE, new CompareListener(view.outliner, view.subWindowArea));
		
		view.itemOpen.addActionListener((ActionListener) listeners.get(Event.LOAD));
		view.outliner.tree.addMouseListener((MouseListener) listeners.get(Event.OPEN));
		view.outliner.itemOpen.addActionListener((ActionListener) listeners.get(Event.OPEN));
		view.outliner.itemCopy.addActionListener((ActionListener) listeners.get(Event.COPY));
		view.outliner.itemPaste.addActionListener((ActionListener) listeners.get(Event.PASTE));
		view.outliner.itemDelete.addActionListener((ActionListener) listeners.get(Event.DELETE));
		view.outliner.itemRemove.addActionListener((ActionListener) listeners.get(Event.REMOVE));
		view.outliner.itemCompare.addActionListener((ActionListener) listeners.get(Event.COMPARE));
		view.outliner.searchBox.addKeyListener((KeyListener) listeners.get(Event.SEARCH));
		view.itemUndo.addActionListener((ActionListener) listeners.get(Event.UNDO));
		view.itemRedo.addActionListener((ActionListener) listeners.get(Event.REDO));
		view.itemMergeSelected.addActionListener((ActionListener) listeners.get(Event.MERGE));
		view.itemSave.addActionListener((ActionListener) listeners.get(Event.SAVE));
		view.itemSaveAs.addActionListener((ActionListener) listeners.get(Event.SAVEAS));
		view.itemSaveAll.addActionListener((ActionListener) listeners.get(Event.SAVEALL));
	}
}

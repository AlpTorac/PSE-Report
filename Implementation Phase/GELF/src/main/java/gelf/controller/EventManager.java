package gelf.controller;

import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultTreeModel;

import gelf.controller.listeners.*;
import gelf.model.project.Model;
import gelf.view.composites.MainWindow;;

/*
 * Stores and initializes the listeners.
 */
public class EventManager {
	
	private MainWindow view;
	private HashMap<Event, EventListener> listeners;
	
	/*
	 * Constructor
	 * @param view GUI view
	 * @param model Model class
	 */
	public EventManager(MainWindow view) {
		
		this.view = view;
		listeners = new HashMap<Event, EventListener>();
		initListeners();
	}
	
	/*
	 * Returns a map of listeners.
	 */
	public HashMap<Event, EventListener> getListeners(){
		return listeners;
	}
	
	/*
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
		listeners.put(Event.RENAME, new RenameListener(view.outliner));
		listeners.put(Event.SAVE, new SaveListener());
		listeners.put(Event.SAVEAS, new SaveAsListener());
		listeners.put(Event.SAVEALL, new SaveAllListener());
		listeners.put(Event.LOADPROJECT, new LoadProjectListener());
		listeners.put(Event.UNDO, new UndoListener());
		listeners.put(Event.REDO, new RedoListener());
		
		view.itemOpen.addActionListener((ActionListener) listeners.get(Event.LOAD));
		view.outliner.tree.addMouseListener((MouseListener) listeners.get(Event.OPEN));
		//
		//view.outliner
		//view.outliner
		//view.outliner
		//view.outliner
		((DefaultTreeModel) view.outliner.tree.getModel()).addTreeModelListener((TreeModelListener) listeners.get(Event.RENAME));
		view.itemUndo.addActionListener((ActionListener) listeners.get(Event.UNDO));
		view.itemRedo.addActionListener((ActionListener) listeners.get(Event.REDO));
		view.itemMergeSelected.addActionListener((ActionListener) listeners.get(Event.MERGE));
		view.itemSave.addActionListener((ActionListener) listeners.get(Event.SAVE));
		view.itemSaveAs.addActionListener((ActionListener) listeners.get(Event.SAVEAS));
		view.itemSaveAll.addActionListener((ActionListener) listeners.get(Event.SAVEALL));
		
		
		
		
	}
	
	
	
	
	
	
	
	

}

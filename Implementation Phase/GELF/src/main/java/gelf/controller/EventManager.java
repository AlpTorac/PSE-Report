package gelf.controller;

import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

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
		listeners.put(Event.OPEN, new OpenElementListener(view.getOutliner(), view.getSubWindowArea()));
		listeners.put(Event.DELETE, new DeleteCellListener(view.getOutliner()));
		listeners.put(Event.REMOVE, new RemoveListener(view.getOutliner()));
		listeners.put(Event.COPY, new CopyListener(view.getOutliner()));
		listeners.put(Event.PASTE, new PasteListener(view.getOutliner()));
		listeners.put(Event.MOVE, new MoveListener(view.getOutliner()));
		listeners.put(Event.MERGE, new MergeListener(view.getOutliner()));
		listeners.put(Event.SEARCH, new SearchListener(view.getOutliner()));
		listeners.put(Event.RENAME, new RenameListener(view.getOutliner()));
		listeners.put(Event.SAVE, new SaveListener());
		listeners.put(Event.SAVEAS, new SaveAsListener());
		listeners.put(Event.LOADPROJECT, new LoadProjectListener());
		listeners.put(Event.UNDO, new UndoListener());
		listeners.put(Event.REDO, new RedoListener());
	}
	
	
	
	
	
	
	
	

}

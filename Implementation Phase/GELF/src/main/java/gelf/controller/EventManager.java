package gelf.controller;

import gelf.model.Model;
import gelf.view.Main;

import java.util.EventListener;
import java.util.Map;;

/*
 * Stores and initializes the listeners.
 */
public class EventManager {
	
	private MainWindow view;
	private Model model;
	private Map<Event, EventListener> listeners;
	
	/*
	 * Constructor
	 * @param view GUI view
	 * @param model Model class
	 */
	public EventManager(MainWindow view, Model model) {
		
		this.view = view;
		this.model = model;
		
		
	}
	
	/*
	 * Returns a map of listeners.
	 */
	public Map<Event, EventListener> getListeners(){
		return listeners;
	}
	
	/*
	 * Subscribes a listener.
	 * @param listener
	 */
	public void subscribeListener(EventListener listener) {
		
	}
	
	/*
	 * Removes a listener
	 */
	public void removeListener(EventListener listener) {
		
		
	}
	
	
	
	
	
	
	

}

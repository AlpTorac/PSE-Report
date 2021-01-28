package gelf.view.diagrams.components;

import java.awt.Component;

public interface Hoverable {
	public default boolean isBeingHovered() {
		return false;
	}
	
	public default void hoverAction() {
		
	}
	
	public default void refreshHoverLabelPosition() {
		
	}
	
	public default void showHoverLabel() {
		
	}
	
	public default void hideHoverLabel() {
		
	}
	
	public default void addHoverListener(Component component) {
		
	}
	
	public void addHoverListeners(Component[] components);
}

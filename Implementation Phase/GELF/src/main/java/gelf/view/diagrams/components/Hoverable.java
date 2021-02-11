package gelf.view.diagrams.components;

import java.awt.Component;
import java.awt.Container;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Timer;

public interface Hoverable {
	public default void hoverAction() {

	}
	
	public default void repositionHoverLabel(double x, double y) {
		HoverLabel l = HoverLabel.getHoverLabel();
		l.setXPos(x);
		l.setYPos(y);
	}

	public default void showHoverLabel() {
		HoverLabel.getHoverLabel().show();
	}

	public default void hideHoverLabel() {
		HoverLabel.getHoverLabel().hide();
	}

	public default void addHoverListener(Component component, Container container) {
		
		MouseAdapter listener = new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				HoverLabel.getHoverLabel().attachToContainer(container);
				showHoverLabel();
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				hoverAction();
				Point cursorOnComponent = container.getMousePosition();
				repositionHoverLabel(cursorOnComponent.getX(), cursorOnComponent.getY());
			}

			@Override
			public void mouseExited(MouseEvent e) {
				hideHoverLabel();
				HoverLabel.getHoverLabel().removeFromContainer();
			}
		};
		
		component.addMouseListener(listener);
		component.addMouseMotionListener(listener);
	}

	public default void addHoverListeners(Container containerOfComponents, Component[] components) {
		for (Component c : components) {
			this.addHoverListener(c, containerOfComponents);
		}
	}
}

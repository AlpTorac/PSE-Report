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

import gelf.view.diagrams.IDiagram;

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

	public default void addHoverListener(Component component, IDiagram diagram) {
		
		MouseAdapter listener = new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				HoverLabel.getHoverLabel().attachToDiagram(diagram);
				showHoverLabel();
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				hoverAction();
				Point cursorOnComponent = diagram.getContainingElement().getMousePosition();
				repositionHoverLabel(cursorOnComponent.getX(), cursorOnComponent.getY());
			}

			@Override
			public void mouseExited(MouseEvent e) {
				hideHoverLabel();
				HoverLabel.getHoverLabel().removeFromDiagram();
			}
		};
		
		component.addMouseListener(listener);
		component.addMouseMotionListener(listener);
	}

	public default void addHoverListeners(IDiagram diagram, Component[] components) {
		for (Component c : components) {
			this.addHoverListener(c, diagram);
		}
	}
}

package gelf.view.diagrams.components;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import gelf.view.diagrams.IDiagram;

public interface Hoverable {
	public default void hoverAction(DiagramValueDisplayComponent dvdc) {
		HoverLabel.getHoverLabel().setCaption(dvdc.toString());
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

	public default void addHoverListener(DiagramValueDisplayComponent dvdc, Component component, IDiagram diagram) {
		
		MouseAdapter listener = new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				HoverLabel.getHoverLabel().attachToDiagram(diagram);
				showHoverLabel();
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				Point cursorOnComponent = diagram.getContainingElement().getMousePosition();
				if (cursorOnComponent != null) {
					hoverAction(dvdc);
					repositionHoverLabel(cursorOnComponent.getX(), cursorOnComponent.getY());
				}
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

	public default void addHoverListeners(DiagramValueDisplayComponent dvdc, Component[] components, IDiagram diagram) {
		for (Component c : components) {
			this.addHoverListener(dvdc, c, diagram);
		}
	}
}

package gelf.view.diagrams.components;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import gelf.view.diagrams.IDiagram;

/**
 * An interface implemented by the {@link DiagramValueDisplayComponent} sub-classes that
 * utilize {@link HoverLabel} to increase the visibility of the values and indices
 * they represent by providing a summary of this information.
 * @author Alp Torac Genc
 */
public interface Hoverable {
	/**
	 * The action to be performed, when the visuals of the component
	 * are being hovered.
	 * @param dvdc the component implementing this interface
	 */
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
	/**
	 * Creates and adds listeners to the component, which listen for hover events.
	 * @param dvdc the component implementing this interface
	 * @param component {@link DiagramValueDisplayComponent#visualElement visualElement} of dvdc
	 * @param diagram {@link DiagramValueDisplayComponent#diagram diagram} of dvdc
	 */
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
				HoverLabel.getHoverLabel().setCaption("");
			}
		};
		component.addMouseListener(listener);
		component.addMouseMotionListener(listener);
	}
	/**
	 * {@link #addHoverListener} for dvdcs that have multiple such components.
	 * @param dvdc the component implementing this interface
	 * @param components parts of dvdc that serve the same purpose as
	 * {@link DiagramValueDisplayComponent#visualElement visualElement}
	 * @param diagram {@link DiagramValueDisplayComponent#diagram diagram} of dvdc
	 */
	public default void addHoverListeners(DiagramValueDisplayComponent dvdc, Component[] components, IDiagram diagram) {
		for (Component c : components) {
			this.addHoverListener(dvdc, c, diagram);
		}
	}
}

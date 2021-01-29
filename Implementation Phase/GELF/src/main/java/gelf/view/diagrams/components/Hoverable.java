package gelf.view.diagrams.components;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public interface Hoverable {
	public default void hoverAction() {

	}

	public default void refreshHoverLabelPosition(double x, double y) {
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

	public default void addHoverListener(Component component) {
		MouseListener listener = new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent me) {
				showHoverLabel();
			}

			@Override
			public void mouseMoved(MouseEvent me) {
				hoverAction();
				Point p = me.getPoint();
				refreshHoverLabelPosition(p.getX(), p.getY());
			}

			@Override
			public void mouseExited(MouseEvent me) {
				hideHoverLabel();
			}
		};

		component.addMouseListener(listener);
	}

	public default void addHoverListeners(Component[] components) {
		for (Component c : components) {
			this.addHoverListener(c);
		}
	}
}

package gelf.view.diagrams;

import gelf.view.diagrams.components.DiagramComponent;

public interface TestCase {
	
	public static final long FAST_SHOW_DURATION = 1000;
	public static final long SHOW_DURATION = 1000;
	public static final long LONG_SHOW_DURATION = 1000;
	
	public static final double TOLERANCE = 1E-5;
	
	public default void show(TestFrame frame, long ms) {
		frame.setVisible(true);
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public default void show(TestFrame frame, DiagramComponent dc, long ms) {
		frame.setVisible(true);
		try {
			Thread.sleep(ms);
			dc.hide();
			Thread.sleep(ms);
			dc.show();
			Thread.sleep(ms / 2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		frame.dispose();
	}
	
	public default boolean compareFloatingPoint(double val1, double val2) {
		if (Math.abs(val1 - val2) <= TOLERANCE) {
			return true;
		}
		return false;
	}
}

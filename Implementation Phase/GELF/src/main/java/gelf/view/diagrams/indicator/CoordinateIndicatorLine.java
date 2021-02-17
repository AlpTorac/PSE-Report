package gelf.view.diagrams.indicator;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import gelf.view.diagrams.SettingsProvider;
import gelf.view.diagrams.components.DiagramComponent;
import gelf.view.diagrams.components.DiagramLine;
import gelf.view.diagrams.components.PositionInFrame;

public class CoordinateIndicatorLine extends DiagramLine {
	protected CoordinateIndicatorLine(PositionInFrame start, PositionInFrame end, Color color, int thickness) {
		super(start, end, color, thickness, SettingsProvider.getInstance().getDiagramViewHelperDisplayLayer());
	}
	
	@Override
	public DiagramComponent clone() {
		return new CoordinateIndicatorLine(this.getStartInFrame().clone(), this.getEndInFrame().clone(), this.getColor(), this.getThickness());
	}
	
	@Override
	protected void initVisualElement() {
		this.visualElement = new CoordinateLineVisual(this);
	}
	
	protected class CoordinateLineVisual extends LineVisual {

		/**
		 * Generated serial version ID.
		 */
		private static final long serialVersionUID = 9102607072967640614L;

		protected CoordinateLineVisual(DiagramLine line) {
			super(line);
		}
		
		@Override
		protected void setStroke(Graphics2D graphs) {
			graphs.setStroke(new BasicStroke(this.line.getThickness(),
                    BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_MITER,
                    10.0f, new float[] {10.0f}, 0.0f));
		}
	}
}

package gelf.view.diagrams.indicator;

import java.awt.Color;

import gelf.view.diagrams.SettingsProvider;
import gelf.view.diagrams.components.DiagramComponent;
import gelf.view.diagrams.components.DiagramLine;
import gelf.view.diagrams.components.PositionInFrame;

public class ValueLine extends DiagramLine {
	private float value;
	
	protected ValueLine(PositionInFrame start, PositionInFrame end, float value, Color color, int thickness) {
		super(start, end, color, thickness, SettingsProvider.getInstance().getDiagramViewHelperDisplayLayer());
	}
	
	@Override
	public DiagramComponent clone() {
		return new ValueLine(this.getStartInFrame().clone(), this.getEndInFrame().clone(), this.value, this.getColor(), this.getThickness());
	}
	
	public float getValue() {
		return this.value;
	}

	@Override
	protected void initVisualElement() {
		this.visualElement = new ValueLineVisual(this);
	}
	
	protected class ValueLineVisual extends LineVisual {

		/**
		 * Generated serial version ID.
		 */
		private static final long serialVersionUID = 9102607072967640614L;

		protected ValueLineVisual(DiagramLine line) {
			super(line);
		}
	}
}

package gelf.view.diagrams.components;

/**
 * The class that represents an axis with a solid {@link #axisLine} in a {@link gelf.view.diagrams.type.Diagram Diagram}.
 * @author Alp Torac Genc
 */
public class SolidAxis extends DiagramAxis {

	protected SolidAxis(SolidLine axisLine, float min, float max, int steps) {
		super(axisLine, min, max, steps);
	}
	
	protected SolidAxis(SolidLine axisLine, float min, float max, int steps, String[] stepDisplays) {
		super(axisLine, min, max, steps, stepDisplays);
	}

	@Override
	public SolidAxis clone() {
		SolidAxis axis = new SolidAxis((SolidLine) this.axisLine.clone(), this.getMin(), this.getMax(), this.getSteps(), this.getStepDisplays());
		axis.setShowValuesUnderAxis(this.areValuesUnderAxis());
		return axis;
	}
}

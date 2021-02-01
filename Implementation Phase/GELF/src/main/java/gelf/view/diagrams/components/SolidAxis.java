package gelf.view.diagrams.components;

public class SolidAxis extends DiagramAxis {

	protected SolidAxis(SolidLine axisLine, float min, float max, int steps) {
		super(axisLine, min, max, steps);
		// TODO Auto-generated constructor stub
	}

	@Override
	public SolidAxis clone() {
		return new SolidAxis((SolidLine) this.axisLine.clone(), this.getMin(), this.getMax(), this.getSteps());
	}
}

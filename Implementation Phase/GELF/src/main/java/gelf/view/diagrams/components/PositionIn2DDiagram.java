package gelf.view.diagrams.components;

public class PositionIn2DDiagram extends PositionInDiagram {
	public PositionIn2DDiagram(DiagramAxis xAxis, Number xCoordinate, DiagramAxis yAxis, Number yCoordinate) {
		super(new DiagramAxis[] {xAxis, yAxis},
				new Number[] {xCoordinate, yCoordinate});
	}
	public void setXCoordinate(Number xCoordinate) {
		
	}
	public Number getXCoordinate() {
		return 0;
	}
	public void setYCoordinate(Number yCoordinate) {
		
	}
	public Number getYCoordinate() {
		return 0;
	}
}

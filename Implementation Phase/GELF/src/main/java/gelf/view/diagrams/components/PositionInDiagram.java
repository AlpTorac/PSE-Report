package gelf.view.diagrams.components;

public abstract class PositionInDiagram {
	private DiagramAxis[] axes;
	private Number[] positionsInAxes;
	
	public PositionInDiagram(DiagramAxis[] axes, Number[] coordinatesInAxes) {
		
	}
	public Number axisCoordinateToFrameCoordinate(int index) {
		return 0;
	}
	public PositionInFrame toPositionInFrame() {
		return null;
	}
	protected void setAxisCoordinate(int index, Number position) {
		
	}
	protected void setAxisCoordinates(Number[] coordinates) {
		
	}
	protected Number getAxisPos(int index) {
		return 0;
	}
}

package gelf.view.diagrams.components;

public class PositionInFrame {
	private double xPos;
	private double yPos;

	public PositionInFrame(double xPos, double yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	public PositionInFrame(PositionInFrame referencePosition, double xPosDifference, double yPosDifference) {
		this.xPos = referencePosition.getXPos() + xPosDifference;
		this.yPos = referencePosition.getYPos() + yPosDifference;
	}

	public double getXPos() {
		return xPos;
	}

	public void setXPos(double xPos) {
		this.xPos = xPos;
	}

	public double getYPos() {
		return yPos;
	}

	public void setYPos(double yPos) {
		this.yPos = yPos;
	}
	
	@Override
	public PositionInFrame clone() {
		return new PositionInFrame(this.getXPos(), this.getYPos());
	}
}

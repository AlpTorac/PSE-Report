package gelf.view.diagrams.components;

public class PositionInFrame {
	private Number xPos;
	private Number yPos;
	
	public PositionInFrame(Number xPos, Number yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
	}

	public Number getXPos() {
		return xPos;
	}

	public void setXPos(Number xPos) {
		this.xPos = xPos;
	}

	public Number getYPos() {
		return yPos;
	}

	public void setYPos(Number yPos) {
		this.yPos = yPos;
	}
}

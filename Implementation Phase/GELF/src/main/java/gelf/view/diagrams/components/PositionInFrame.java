package gelf.view.diagrams.components;

import gelf.view.diagrams.SettingsProvider;

/**
 * The class that represents a point (x, y) in a {@link java.awt.Container Container}.
 * @author Alp Torac Genc
 *
 */
public class PositionInFrame {
	private double xPos;
	private double yPos;

	protected PositionInFrame(double xPos, double yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
	}
	/**
	 * Constructs an instance by adding the vector specified in x and y coordinates to reference point's coordinates.
	 */
	protected PositionInFrame(PositionInFrame referencePosition, double xPosDifference, double yPosDifference) {
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
	
	@Override
	public boolean equals(Object pos) {
		if (pos == null || !(pos instanceof PositionInFrame)) {
			return false;
		}
		
		PositionInFrame otherPos = (PositionInFrame) pos;
		
		if (Math.abs(this.getXPos() - otherPos.getXPos()) <= SettingsProvider.getTolerance() &&
				Math.abs(this.getYPos() - otherPos.getYPos()) <= SettingsProvider.getTolerance()) {
			return true;
		} else {
			return false;
		}
	}
}

package main.java.gelf.model.elements.attributes;

public class Timing extends OutAttribute{
	private TimingSense timSense;
	private TimingType timType;
	private TimingGroup timGroup;
	
	public Timing(TimingSense timSense, TimingType timType, 
			TimingGroup timGroup, float[][] values) {
		setTimingSense(timSense);
		setTimType(timType);
		setTimGroup(timGroup);
		this.values = values;
	}

	public TimingSense getTimSense() {
		return timSense;
	}

	public void setTimingSense(TimingSense timSense) {
		this.timSense = timSense;
	}

	public TimingType getTimType() {
		return timType;
	}

	public void setTimType(TimingType timType) {
		this.timType = timType;
	}

	public TimingGroup getTimGroup() {
		return timGroup;
	}

	public void setTimGroup(TimingGroup timGroup) {
		this.timGroup = timGroup;
	}

	@Override
	public OutAttribute createComparedAttribute(OutAttribute attribute) {
		float[][] compValues = new float[index1.length][index2.length];
		if (this.index1.length == attribute.index1.length &&
			this.index2.length == attribute.index2.length) {
			for (int i = 0; i < index1.length; i++) {
				for (int j = 0; j < index2.length; j++) {
					compValues[i][j] = this.values[i][j] - attribute.values[i][j];
				}
			}
		}
		OutAttribute compTiming = new Timing(timSense, timType, timGroup, compValues);
		return compTiming;
	}
	
	

}

package main.java.gelf.model.elements.attributes;

public class TimingKey {
	// this class is to use one key for the timingStat map instead of nesting maps
	private TimingSense timSense;
	private TimingGroup timGroup;
	private TimingType timType;
	
	public TimingKey(TimingSense timSense, TimingGroup timGroup, TimingType timType) {
		this.timSense = timSense;
		this.timGroup = timGroup;
		this.timType = timType;
	}
	
	public TimingSense getTimSense() {
		return timSense;
	}
	
	public void setTimSense(TimingSense timSense) {
		this.timSense = timSense;
	}
	
	public TimingGroup getTimGroup() {
		return timGroup;
	}
	
	public void setTimGroup(TimingGroup timGroup) {
		this.timGroup = timGroup;
	}
	
	public TimingType getTimType() {
		return timType;
	}
	
	public void setTimType(TimingType timType) {
		this.timType = timType;
	}
	
}

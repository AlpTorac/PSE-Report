package gelf.model.elements.attributes;

/**
 * Used as a key for the timing statistic maps.
 * 
 * @author Kerem Kara
 */
public class TimingKey {
	// this class is to use one key for the timingStat map instead of nesting maps
	private TimingSense timSense;
	private TimingGroup timGroup;
	private TimingType timType;

	public TimingKey(TimingSense timSense, TimingType timType, TimingGroup timGroup) {
		this.timSense = timSense;
		this.timType = timType;
		this.timGroup = timGroup;
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

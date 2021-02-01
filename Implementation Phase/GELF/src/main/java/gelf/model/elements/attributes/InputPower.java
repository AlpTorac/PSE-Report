package gelf.model.elements.attributes;

public class InputPower extends InAttribute {
	private PowerGroup powGroup;
	
	public InputPower(PowerGroup powGroup, float[] values) {
		this.powGroup = powGroup;
		this.values = values;
	}

	public PowerGroup getPowGroup() {
		return powGroup;
	}

	public void setPowGroup(PowerGroup powGroup) {
		this.powGroup = powGroup;
	}
}

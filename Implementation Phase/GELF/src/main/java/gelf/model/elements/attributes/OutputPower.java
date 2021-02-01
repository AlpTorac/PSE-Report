package gelf.model.elements.attributes;

public class OutputPower extends OutAttribute{
	private PowerGroup powGroup;
	
	public OutputPower(PowerGroup powGroup, float[][] values) {
		this.powGroup = powGroup;
		this.values = values;
	}

	public PowerGroup getPowGroup() {
		return powGroup;
	}

	public void setPowGroup(PowerGroup powGroup) {
		this.powGroup = powGroup;
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
		OutAttribute compOutPow = new OutputPower(powGroup, compValues);
		return compOutPow;
	}
}


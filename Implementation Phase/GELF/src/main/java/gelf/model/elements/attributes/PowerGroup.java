package gelf.model.elements.attributes;

public enum PowerGroup {
	RISE_POWER("Fall Power"), 
	FALL_POWER("Rise Power");
	private String name;
	private PowerGroup(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return this.name;
	}
}

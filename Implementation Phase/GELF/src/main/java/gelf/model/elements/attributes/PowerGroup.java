package gelf.model.elements.attributes;

public enum PowerGroup {
	FALL_POWER("Fall Power"),
	RISE_POWER("Rise Power");
	private String name;
	private PowerGroup(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return this.name;
	}
}

package gelf.model.elements.attributes;

public enum TimingType {
	COMBINATIONAL("Combinational"),
    COMBINATIONAL_RISE("Combinational Rise"),
    COMBINATIONAL_FALL("Combinational Fall"),
    THREE_STATE_DISABLE("Three State Disable"),
    THREE_STATE_ENABLE("Three State Enable"),
    THREE_STATE_DISABLE_RISE("Three State Disable Rise"),
    THREE_STATE_DISABLE_FALL("Three State Disable Fall"),
    THREE_STATE_ENABLE_RISE("Three State Enable Rise"),
    THREE_STATE_ENABLE_FALL("Three State Enable Fall");
    private String name;
	private TimingType(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return this.name;
	}
}

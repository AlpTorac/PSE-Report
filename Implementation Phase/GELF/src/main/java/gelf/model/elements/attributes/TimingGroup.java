package gelf.model.elements.attributes;

public enum TimingGroup {
	CELL_RISE("Cell Rise"),
    CELL_FALL("Cell Fall"),
    FALL_TRANSITION("Fall Transition"),
    RISE_TRANSITION("Rise Transition");
    private String name;
	private TimingGroup(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return this.name;
	}
}

package gelf.model.elements.attributes;

public enum TimingSense {
	POSITIVE_UNATE("Positive Unate"),
    NEGATIVE_UNATE("Negative Unate"),
    NON_UNATE("Non Unate");
    private String name;
	private TimingSense(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return this.name;
	}
}

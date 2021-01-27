package main.java.gelf.model.elements;

public abstract class Element {
	protected boolean filtered;
	protected boolean searched;
	protected String name;
	
	public Element() {}

	public boolean getFiltered() {
		return filtered;
	}

	public void setFiltered(boolean filtered) {
		this.filtered = filtered;
	}

	public boolean getSearched() {
		return searched;
	}

	public void setSearched(boolean searched) {
		this.searched = searched;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public abstract void calculate();
	
	public int compareTo(Element element) {
		return this.name.compareTo(element.name);
	}
}
